package com.xiaozhang.devicemanagesystem.server.mapper;

import com.github.pagehelper.Page;
import com.xiaozhang.devicemanagesystem.pojo.dto.DevicePageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.Device;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeviceMapper {
    /**
     * 插入设备
     * @param device
     */
    @Insert("insert into device (code,name,model,status,insert_order_id,update_time)" +
            "values (#{code} ,#{name} ,#{model},#{status} ,#{insertOrderId} ,#{updateTime}  )")
    void insert(Device device);

    /**
     * 分页查询设备
     * @param devicePageQueryDTO
     * @return
     */
    Page<DeviceVO> page(DevicePageQueryDTO devicePageQueryDTO);

    /**
     * 根据id查询设备信息
     * @param id
     * @return
     */
    @Select("select * from v_device where id = #{id}")
    DeviceVO getById(Long id);

    /**
     * 修改设备信息
     * @param device
     */
    void update(Device device);
}
