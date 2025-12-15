package com.xiaozhang.devicemanagesystem.server.mapper;

import com.github.pagehelper.Page;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserPageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.Dept;
import com.xiaozhang.devicemanagesystem.pojo.entity.User;
import com.xiaozhang.devicemanagesystem.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {


    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user (code,username,password,type,dept_id,create_time,update_time)" +
            "values (#{code} ,#{username} ,#{password} ,#{type} ,#{deptId} ,#{createTime} ,#{updateTime} )")
    void insert(User user);

    /**
     * 根据用户名和id查询用户
     * @param userPageQueryDTO
     * @return
     */
    Page<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 删除用户
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    void delete(Long id);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Select("select * from v_user where id = #{id}")
    UserVO getById(Long id);

    /**
     * 修改用户信息
     * @param user
     */
    void update(User user);

    /**
     * 查询所有单位信息
     * @return
     */
    @Select("select * from dept")
    List<Dept> getDepts();
}
