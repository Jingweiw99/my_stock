package com.wjw.stock;

import com.wjw.stock.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SnowFlakeTest {
    @Autowired
    private IdWorker idWorker;

    @Test
    public void testSnowFlake() {
        long l = idWorker.nextId();
        System.out.println(l);
        long l2 = idWorker.nextId();
        System.out.println(l2);
        long l3 = idWorker.nextId();
        System.out.println(l3);
        long l4 = idWorker.nextId();
        System.out.println(l4);
        long l5 = idWorker.nextId();
        System.out.println(l5);
    }
}
