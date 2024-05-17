package com.wjw.stock.service;

import com.wjw.stock.pojo.entity.SysUser;

public interface SysUserService {
    SysUser getUserByUserName(String userName);
}
