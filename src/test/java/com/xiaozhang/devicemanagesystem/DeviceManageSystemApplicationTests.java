package com.xiaozhang.devicemanagesystem;

import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DeviceManageSystemApplicationTests {

    @Autowired
    private DeviceMapper deviceMapper;
    @Test
    void contextLoads() {
        List<Integer> countByStatus = deviceMapper.getCountByStatus();
        System.out.println(countByStatus);
    }

}
