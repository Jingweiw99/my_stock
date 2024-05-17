package com.wjw.stock.web;

import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @GetMapping("/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName){
        return sysUserService.getUserByUserName(userName);
    }
}
