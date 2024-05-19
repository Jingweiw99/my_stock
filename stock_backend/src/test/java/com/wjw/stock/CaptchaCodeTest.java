package com.wjw.stock;

import com.wjw.stock.vo.resp.R;
import com.wjw.stock.web.SysUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class CaptchaCodeTest {
    @Autowired
    private SysUserController sysUserController;

    /**
     * 测试是否生成验证码
     */
    @Test
    public void test1() {
        R<Map> captchaCode = sysUserController.getCaptchaCode();
        System.out.println(captchaCode);
    }

}
