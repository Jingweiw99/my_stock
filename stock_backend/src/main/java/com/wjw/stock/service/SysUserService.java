package com.wjw.stock.service;

import com.wjw.stock.pojo.entity.SysUser;
import com.wjw.stock.pojo.vo.req.LoginReqVo;
import com.wjw.stock.pojo.vo.resp.LoginRespVo;
import com.wjw.stock.pojo.vo.resp.R;

import java.util.Map;

public interface SysUserService {
    SysUser getUserByUserName(String userName);
    R<LoginRespVo> login(LoginReqVo loginReqVo);
    R<Map> getCaptchaCode();
}
