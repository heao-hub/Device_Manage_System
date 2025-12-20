package com.xiaozhang.devicemanagesystem.pojo.vo;

import com.xiaozhang.devicemanagesystem.pojo.entity.InsertOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertOrderVO extends InsertOrder implements Serializable {
    private  String adminCode;
    private String adminName;
    private String deptName;
}
