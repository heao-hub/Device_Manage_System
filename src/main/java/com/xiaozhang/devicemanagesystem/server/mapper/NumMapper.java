package com.xiaozhang.devicemanagesystem.server.mapper;

import com.xiaozhang.devicemanagesystem.pojo.entity.Num;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NumMapper {

    /**
     * 获取表中数据
     * @return
     */
    @Select("select * from number")
    Num getNum();

    /**
     * 修改顺序号信息
     * @param num
     */
    void update(Num num);
}
