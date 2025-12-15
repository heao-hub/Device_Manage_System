package com.xiaozhang.devicemanagesystem.pojo.vo;

import java.io.Serializable;
import com.xiaozhang.devicemanagesystem.pojo.entity.BorrowDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDetailVO extends BorrowDetail implements Serializable {
    private String borrowCode;
    private String deviceCode;
    private String deviceName;
    private String deviceModel;

}
