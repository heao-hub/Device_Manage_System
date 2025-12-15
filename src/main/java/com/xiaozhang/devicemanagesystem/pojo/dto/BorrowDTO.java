package com.xiaozhang.devicemanagesystem.pojo.dto;

import com.xiaozhang.devicemanagesystem.pojo.entity.Device;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BorrowDTO implements Serializable {

    private Long userId;
    private int status;
    private String description;
    private List<DeviceDTO> devices = new ArrayList<>();
}
