package com.wjw.stock.service.impl;

import com.wjw.stock.mapper.SysUserMapper;
import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByUserName(String userName) {
        return sysUserMapper.getUserByUserName(userName);
    }
}
