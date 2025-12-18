package com.xiaozhang.devicemanagesystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertOrderReportVO implements Serializable {
    private String dateList;
    private String newInsertOrderList;
    private String totalInsertOrderList;
}
