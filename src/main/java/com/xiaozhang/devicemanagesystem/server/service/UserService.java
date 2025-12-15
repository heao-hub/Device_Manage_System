package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserLoginDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserPageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.User;
import com.xiaozhang.devicemanagesystem.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User userLogin(UserLoginDTO userLoginDTO);

    /**
     * 新增用户
     * @param userDTO
     */
    void addUser(UserDTO userDTO);

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 删除用户
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserVO getById(Long id);

    /**
     * 修改用户信息
     * @param userDTO
     */
    void update(UserDTO userDTO);

    /**
     * 查询所有单位信息
     * @return
     */
    PageResult getDepts();
}
