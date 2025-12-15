package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
