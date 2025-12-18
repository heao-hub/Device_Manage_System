package com.xiaozhang.devicemanagesystem.server.controller;

import com.xiaozhang.devicemanagesystem.common.constant.JwtClaimsConstant;
import com.xiaozhang.devicemanagesystem.common.properties.JwtProperties;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.common.util.JwtUtil;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserLoginDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserPageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.User;
import com.xiaozhang.devicemanagesystem.pojo.vo.UserLoginVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.UserVO;
import com.xiaozhang.devicemanagesystem.server.mapper.UserMapper;
import com.xiaozhang.devicemanagesystem.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户管理controller
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO);

        User user = userService.userLogin(userLoginDTO);

        // 登录成功，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        claims.put(JwtClaimsConstant.USER_ROLE,user.getType());

        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .code(user.getCode())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @PostMapping
    public Result<String> addUser(@RequestBody UserDTO userDTO){
        log.info("添加用户，{}",userDTO);

        userService.addUser(userDTO);
        return Result.success();
    }

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(UserPageQueryDTO userPageQueryDTO){
        log.info("用户分页查询,{}",userPageQueryDTO);

        PageResult result = userService.pageQuery(userPageQueryDTO);
        return Result.success(result);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long id){
        log.info("删除用户{}",id);
        userService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<UserVO> getById(@PathVariable Long id){
        log.info("根据id查询用户信息,{}",id );
        UserVO userVO = userService.getById(id);
        return Result.success(userVO);
    }

    /**
     * 修改用户信息
     * @param userDTO
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody UserDTO userDTO){
        log.info("修改用户信息，{}",userDTO);
        userService.update(userDTO);
        return Result.success();
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(){
        return Result.success();
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/dept")
    public Result<PageResult> getDepts(){
        log.info("查询所有部门信息");
        PageResult result = userService.getDepts();
        return Result.success(result);
    }
}
