package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Num {
    private int userNum;
    private int deviceNum;
    private int insertNum;
    private int borrowNum;
    private int scrapNum;
    private int feedbackNum;
}
