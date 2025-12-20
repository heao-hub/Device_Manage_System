create database device_manage_system;

use device_manage_system;

-- 1. 单位表
CREATE TABLE dept (
                        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '单位主键ID',
                        code VARCHAR(50) NOT NULL COMMENT '单位唯一编号',
                        name VARCHAR(100) NOT NULL COMMENT '单位名称',
                        create_time DATETIME COMMENT '创建时间',
                        update_time DATETIME COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单位信息表';

drop table dept;
-- 2. 用户表
CREATE TABLE user (
                             id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户主键ID',
                             code VARCHAR(15) NOT NULL COMMENT '用户唯一编号',
                             username VARCHAR(50) NOT NULL COMMENT '用户名',
                             password VARCHAR(100) DEFAULT '123456' COMMENT '用户密码',
                             type INT  COMMENT '用户类型（如管理员/普通用户）',
                             dept_id INT(11)  COMMENT '关联单位表ID',
                             create_time DATETIME COMMENT '创建时间',
                             update_time DATETIME COMMENT '最后操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

drop table user;

-- 3. 入库单表
CREATE TABLE insert_order (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '入库单主键ID',
                                      code VARCHAR(16) NOT NULL COMMENT '入库单唯一编号',
                                      device_name VARCHAR(50) NOT NULL COMMENT '设备名称',
                                      device_model VARCHAR(50)  COMMENT '设备型号',
                                      count INT  COMMENT '入库数量',
                                      price DECIMAL(10,2)  COMMENT '设备单价',
                                      manufacturer VARCHAR(50)  COMMENT '生产厂商',
                                      dept_id BIGINT NOT NULL COMMENT '购入单位ID',
                                      create_time DATETIME COMMENT '入库时间',
                                      admin_id BIGINT NOT NULL COMMENT '入库处理人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备入库信息表';

-- 4. 仪器设备表
CREATE TABLE device (
                             id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '设备主键ID',
                             code VARCHAR(15) NOT NULL COMMENT '设备唯一编号',
                             name VARCHAR(50) NOT NULL COMMENT '设备名称',
                             model VARCHAR(50)  COMMENT '设备型号',
                             status INT NOT NULL COMMENT '设备状态,1表示正常可用 2表示借出 3表示维修 4表示报废',
                             insert_order_id BIGINT NOT NULL COMMENT '关联入库单编号',
                             update_time DATETIME  COMMENT '最后操作时间'

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仪器设备信息表';

-- 5. 报废单表
CREATE TABLE scrap_order (
                               id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '报废单主键ID',
                               code VARCHAR(16) NOT NULL COMMENT '报废单唯一编号',
                               device_id BIGINT NOT NULL COMMENT '关联设备表编号',
                               reason VARCHAR(200) COMMENT '报废原因',
                               admin_id BIGINT NOT NULL COMMENT '关联用户表ID（批准人）',
                               create_time DATETIME  COMMENT '报废时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备报废信息表';
drop table scrap_order;

-- 6. 借条表（借用申请单）
CREATE TABLE borrow_order (
                                id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '借条主键ID',
                                code VARCHAR(16) NOT NULL COMMENT '借条唯一编号',
                                create_time DATETIME NOT NULL  COMMENT '申请时间',
                                description VARCHAR(200) COMMENT '借用说明',
                                user_id BIGINT NOT NULL COMMENT '关联用户表ID（借用人）',
                                status INT NOT NULL COMMENT '申请状态,1表示待审批，2表示审批通过，3表示审批不通过',
                                admin_id BIGINT DEFAULT NULL COMMENT '关联用户表ID（批准人，可空）',
                                handle_time DATETIME DEFAULT NULL COMMENT '审批时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备借用申请单表';
drop table borrow_order;

-- 7. 借条详细信息表
CREATE TABLE borrow_detail (
                                 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '借条详情主键ID',
                                 borrow_id BIGINT NOT NULL COMMENT '关联借条表单号',
                                 device_id BIGINT NOT NULL COMMENT '关联设备表编号',
                                 borrow_time DATETIME  COMMENT '实际借用时间',
                                 user_id BIGINT NOT NULL COMMENT '关联用户表ID（借用人）',
                                 return_time DATETIME DEFAULT NULL COMMENT '归还时间（可空）',
                                 return_status INT  COMMENT '归还状态,1表示未归还，2表示已归还'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='借条详细信息表';
drop table borrow_detail;

-- 8. 设备反馈表
CREATE TABLE feedback_order (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '反馈表主键ID',
                                      code VARCHAR(16) NOT NULL COMMENT '反馈表唯一编号',
                                      device_id BIGINT NOT NULL COMMENT '关联设备表编号',
                                      device_problem VARCHAR(200) NOT NULL COMMENT '设备问题描述',
                                      user_id BIGINT NOT NULL COMMENT '关联用户表ID（反馈人）',
                                      status INT NOT NULL COMMENT '处理状态,1表示待处理，2表示已处理',
                                      admin_id BIGINT DEFAULT NULL COMMENT '关联用户表ID（处理人，可空）',
                                      create_time DATETIME NOT NULL COMMENT '反馈时间',
                                      handle_time DATETIME DEFAULT NULL COMMENT '处理时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备问题反馈表';
drop table feedback_order;
-- 9. 日志表
CREATE TABLE operation_log (
                                 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '日志主键ID',
                                 device_id BIGINT NOT NULL COMMENT '关联设备表编号',
                                 operation_type VARCHAR(50) NOT NULL COMMENT '操作类型（如入库/借用/报废/反馈）',
                                 operator_id BIGINT NOT NULL COMMENT '关联用户表ID（操作人）',
                                 operation_time DATETIME COMMENT '操作时间',
                                 operation_desc VARCHAR(200) DEFAULT NULL COMMENT '操作描述（可选）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备操作日志表';

create view v_user as
    select user.*,dept.name as dept_name
    from dept ,user
    where user.dept_id = dept.id;

drop view v_user;

create view v_device as
    select d.id,d.code,d.name as device_name,d.model as device_model,d.status,i.price,i.manufacturer,
           dept.name as dept_name,d.insert_order_id,i.create_time,d.update_time
    from device d,insert_order i ,dept
    where d.insert_order_id = i.id and i.dept_id = dept.id;

drop view v_device;

create view v_borrow as
    select bd.id,bo.id as borrow_id,bo.code as borrow_code,bo.status,
           v_user.username as user_name,v_user.dept_name,v_user.id as user_id,v_user.code as user_code,
           bd.device_id,d.code as device_code,d.name as device_name,d.model as device_model,
           bd.return_status,bd.borrow_time,bd.return_time,user.username as admin_name,user.code as admin_code
    from borrow_order bo left outer join user on bo.admin_id = user.id,
         borrow_detail bd,v_user,device d
    where bo.id = bd.borrow_id and bo.user_id = v_user.id and bd.device_id = d.id ;



drop view v_borrow;

create view v_scrap as
    select so.id,so.code,d.id as device_id,d.code as device_code,d.name as device_name,d.model as device_model,
           so.reason,so.create_time,user.id as admin_id,user.code as admin_code,user.username as admin_name
    from scrap_order so ,device d ,user
    where so.device_id = d.id and so.admin_id = user.id;

drop view v_scrap;

create view v_feedback as
    select fo.id,fo.code ,d.id as device_id,d.code as device_code,d.name as device_name,d.model,fo.device_problem,
           u.id as user_id,u.code as user_code,u.username as user_name,
           u1.id as admin_id,u1.code as admin_code,u1.username as admin_name,
           fo.status,fo.create_time ,fo.handle_time
    from feedback_order fo left outer join user u1 on fo.admin_id = u1.id,device d ,user u
    where fo.device_id = d.id and fo.user_id = u.id ;

drop view v_feedback;

create view v_user_borrow as
    select bo.*, u2.username as user_name,u2.code as user_code,u1.username as admin_name,u1.code as admin_code
    from borrow_order bo left outer join user u1 on bo.admin_id = u1.id,user u2
    where bo.user_id = u2.id;

drop view v_user_borrow;

create view v_insert as
    select insert_order.*,dept.name as dept_name,user.code as admin_code,user.username as admin_name
    from insert_order left outer join user on insert_order.admin_id = user.id,dept
    where insert_order.dept_id = dept.id;


insert into dept (code,name,create_time,update_time) values
                                                         ('D001','计算机学院',NOW(),NOW()),
                                                         ('D002','材料学院',NOW(),NOW()),
                                                         ('D003','土建学院',NOW(),NOW()),
                                                         ('D004','机电学院',NOW(),NOW()),
                                                         ('D005','化生学院',NOW(),NOW()),
                                                         ('D006','信息学院',NOW(),NOW()),
                                                         ('D007','材料学院',NOW(),NOW()),
                                                         ('D008','资环学院',NOW(),NOW()),
                                                         ('D009','汽车学院',NOW(),NOW()),
                                                         ('D010','体育学院',NOW(),NOW()),
                                                         ('D011','智能实验中心',NOW(),NOW()),
                                                         ('D012','化学实验中心',NOW(),NOW()),
                                                         ('D013','物理实验中心',NOW(),NOW());

create table number(
    user_num int default 0 comment '用户顺序号',
    insert_num int default 0 comment '入库单顺序号',
    device_num int default 0 comment '设备顺序号',
    borrow_num int default 0 comment '借条顺序号',
    scrap_num int default 0 comment '报废单顺序号',

);
insert into number values (1,0,0,0,0,0);
insert into user (code, username, type, dept_id, create_time, update_time)
values ("U20251216000000","curry",1,1,now(),now());
