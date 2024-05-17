package com.wjw.stock.service.impl;

import com.wjw.stock.mapper.SysUserMapper;
import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.pojo.vo.req.LoginReqVo;
import com.wjw.stock.pojo.vo.resp.LoginRespVo;
import com.wjw.stock.pojo.vo.resp.R;
import com.wjw.stock.pojo.vo.resp.ResponseCode;
import com.wjw.stock.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser getUserByUserName(String userName) {
        return sysUserMapper.getUserByUserName(userName);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        if (vo == null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
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
}
