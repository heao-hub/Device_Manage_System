package com.xiaozhang.devicemanagesystem.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaozhang.devicemanagesystem.common.constant.ExceptionConstant;
import com.xiaozhang.devicemanagesystem.common.exception.PasswordWrongException;
import com.xiaozhang.devicemanagesystem.common.exception.UserNotFoundException;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserLoginDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserPageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.Dept;
import com.xiaozhang.devicemanagesystem.pojo.entity.Num;
import com.xiaozhang.devicemanagesystem.pojo.entity.User;
import com.xiaozhang.devicemanagesystem.pojo.vo.UserVO;
import com.xiaozhang.devicemanagesystem.server.mapper.NumMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.UserMapper;
import com.xiaozhang.devicemanagesystem.server.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NumMapper numMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    public User userLogin(UserLoginDTO userLoginDTO) {

        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        // 根据username查询用户
        User user = userMapper.getUserByUsername(username);

        if(user == null){
            throw new UserNotFoundException(ExceptionConstant.USER_NOT_FOUND);
        }

        // 密码比对
        // 密码经过md5加密存储在数据库中
        String pass = DigestUtils.md5DigestAsHex(password.getBytes());

        if(!user.getPassword().equals(pass)){
            throw new PasswordWrongException(ExceptionConstant.PASSWORD_WRONG);
        }

        return user;
    }

    /**
     * 新增用户
     * @param userDTO
     */
    public void addUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);

        // 生成用户编号
        Num num = numMapper.getNum();
        int userNum = num.getUserNum();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code = String.format("U%s%06d", currentDate, userNum);
        user.setCode(code);

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);

        userMapper.insert(user);

        userNum++;
        num.setUserNum(userNum);
        numMapper.update(num);
    }

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());

        Page<UserVO> list= userMapper.pageQuery(userPageQueryDTO);
        int total = (int)list.getTotal();
        List<UserVO> records = list.getResult();
        return new PageResult(total,records);
    }

    /**
     * 删除用户
     * @param id
     */
    public void delete(Long id) {
        userMapper.delete(id);
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public UserVO getById(Long id) {
        UserVO userVO = userMapper.getById(id);

        return userVO;
    }

    /**
     * 修改用户信息
     * @param userDTO
     */
    public void update(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setUpdateTime(LocalDateTime.now());

        String password = user.getPassword();
        if(password != null){
            password = DigestUtils.md5DigestAsHex(password.getBytes());
        }
        user.setPassword(password);

        userMapper.update(user);
    }

    /**
     * 查询所有单位信息
     * @return
     */
    public PageResult getDepts() {
        List<Dept> depts = userMapper.getDepts();
        PageResult pageResult = new PageResult(depts.size(),depts);
        return pageResult;
    }
}
