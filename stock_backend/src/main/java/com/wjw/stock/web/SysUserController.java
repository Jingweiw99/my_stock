package com.wjw.stock.web;

import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.vo.req.LoginReqVo;
import com.wjw.stock.vo.resp.LoginRespVo;
import com.wjw.stock.vo.resp.R;
import com.wjw.stock.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    @GetMapping("/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName) {
        return sysUserService.getUserByUserName(userName);
    }

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return sysUserService.login(vo);
    }

    /**
     * 生成验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode() {
        return sysUserService.getCaptchaCode();
    }
}
