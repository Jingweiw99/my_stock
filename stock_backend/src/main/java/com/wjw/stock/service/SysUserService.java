package com.wjw.stock.service;

import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.pojo.vo.req.LoginReqVo;
import com.wjw.stock.pojo.vo.resp.LoginRespVo;
import com.wjw.stock.pojo.vo.resp.R;

public interface SysUserService {
    SysUser getUserByUserName(String userName);
    R<LoginRespVo> login(LoginReqVo loginReqVo);
}
