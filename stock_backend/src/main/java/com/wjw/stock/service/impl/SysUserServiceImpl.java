package com.wjw.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.wjw.stock.constant.StockConstant;
import com.wjw.stock.mapper.SysUserMapper;
import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.pojo.vo.req.LoginReqVo;
import com.wjw.stock.pojo.vo.resp.LoginRespVo;
import com.wjw.stock.pojo.vo.resp.R;
import com.wjw.stock.pojo.vo.resp.ResponseCode;
import com.wjw.stock.service.SysUserService;
import com.wjw.stock.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUser getUserByUserName(String userName) {
        return sysUserMapper.getUserByUserName(userName);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        // 1. 用户名密码空值过滤
        if (vo == null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        // 2. 验证码和sessionId过滤
        if (StringUtils.isBlank(vo.getCode()) || StringUtils.isBlank(vo.getSessionId())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        // 3. redis中获取后台保存的验证码
        String rCode = (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        // 4. 判断验证码是否为空
        if (StringUtils.isBlank(rCode) || rCode.equalsIgnoreCase(vo.getCode())) {
            // 验证码输入错误
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        //根据用户名查询用户信息
        SysUser user = this.sysUserMapper.getUserByUserName(vo.getUsername());
        //判断用户是否存在，存在则密码校验比对
        if (user == null || !passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS.getMessage());
        }
        //组装登录成功数据
        LoginRespVo respVo = new LoginRespVo();
        //属性名称与类型必须相同，否则属性值无法copy
        BeanUtils.copyProperties(user, respVo);
        return R.ok(respVo);
    }

    @Override
    public R<Map> getCaptchaCode() {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        lineCaptcha.setBackground(Color.lightGray);
        // 获取图片的验证码
        String code = lineCaptcha.getCode();
        // 转为字符串传给前端避免精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        String imageData = lineCaptcha.getImageBase64();
        // 设置redis 中value过期时间为1分钟 一般加上业务前缀，方便redis管理端查看
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX + sessionId, code, 1, TimeUnit.MINUTES);
        log.info("生成的sessionId: {},图片imageData: {}", sessionId, imageData);
        HashMap<String, String> resultVo = new HashMap<>();
        resultVo.put("sessionId", sessionId);
        resultVo.put("imageData", imageData);
        return R.ok(resultVo);
    }
}
