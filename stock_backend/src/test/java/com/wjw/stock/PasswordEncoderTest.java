package com.wjw.stock;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String pwd = "123456";
        String encode = passwordEncoder.encode(pwd);
        // $2a$10$/QU2z9ZQ.NZ/iKbFoBpiA.0/O/7YupNou2u7WR1MmBT3/y47e1U9e
        System.out.println(encode);
    }

    @Test
    public void testMatches() {
        String pwd = "123456";
        boolean matches = passwordEncoder.matches(pwd, "$2a$10$/QU2z9ZQ.NZ/iKbFoBpiA.0/O/7YupNou2u7WR1MmBT3/y47e1U9e");
        System.out.println(matches);
    }
}
