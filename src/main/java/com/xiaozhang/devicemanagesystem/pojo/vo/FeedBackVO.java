package com.xiaozhang.devicemanagesystem.pojo.vo;

import com.xiaozhang.devicemanagesystem.pojo.entity.FeedbackOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class FeedBackVO extends FeedbackOrder implements Serializable {
    private String deviceCode;
    private String deviceName;
    private String deviceModel;
    private String userCode;
    private String userName;
    private String adminCode;
    private String adminName;

}
