package com.xiaozhang.devicemanagesystem;

import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DeviceManageSystemApplicationTests {

    @Autowired
    private DeviceMapper deviceMapper;
    @Test
    void contextLoads() {

        List<Integer> counts = new ArrayList<>();

        counts = deviceMapper.getCountByStatus();
        System.out.println(counts);
    }

}
