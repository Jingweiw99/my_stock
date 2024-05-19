package com.wjw.stock.vo.req;

import lombok.Data;

@Data
public class LoginReqVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 保存redis随机码的key，也就是sessionId (前端display: none，但是数据还是通过表单传递过来)
     */
    private String sessionId;
}
