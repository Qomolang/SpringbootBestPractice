# SpringbootBestPractice

## 通用功能
1. 集成mybatis-plus，自动填充CreateTime、UpdateTime，DO可Enum完成映射，DO的ext可自动完成解析
2. 集成mybatis-plus-generator，按照DDD生成结构
3. 集成retrofit，可http调用别人
4. 集成Lettuce，可直接使用redis
5. 提供Filter和Interceptor示例，以拦截请求
6. 提供ControllerExceptionHandler示例（使用Spring提供的RestControllerAdvice）和CommonExceptionHandler示例

## 业务模块
1. 短信发送模块
2. 导入导出模块

## 代码生成器使用指南
1. 生成的实体类需要手动替换其中的枚举值
2. 对应的枚举值需要在目标字段上加@EnumValue注解
3. 如果有Json类型的ext字段，则需要在DO类中的@TableName注解上加上(autoResultMap = true); 并且在ext字段上的注解@TableField加上(typeHandler =
   FastjsonTypeHandler.class)；如果有xml文件，需要加上typeHandler="xxxx.JsonTypeHandler"/>

## 非controller层validator使用指南

根pom引入的spring-boot-starter-validation已经包含全部的包
在要使用validator的模块引入hibernate-validator 以使用JSR303注解即可

1. 类上加@Validated，spring仅校验有该注解标识的类下面的方法
2. 方法参数上加@Valid，让hibernate-validator校验
3. 如果参数要保证不为null，方法参数上要加@NotNull

### 建表语句

-- gstest.employee definition

CREATE TABLE `employee` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`user_id` bigint NOT NULL,
`tenant_id` bigint NOT NULL,
`nick_name` varchar(100) NOT NULL,
`emp_code` varchar(512) NOT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
PRIMARY KEY (`id`),
UNIQUE KEY `employee_un` (`emp_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.org definition

CREATE TABLE `org` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`name` varchar(512) NOT NULL,
`full_name` varchar(100) DEFAULT NULL,
`corp_id` varchar(100) NOT NULL,
`super_admin_uid` bigint NOT NULL,
`ext` json DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.dept definition

CREATE TABLE `dept` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`name` varchar(512) NOT NULL,
`member_size` bigint DEFAULT NULL,
`org_id` varchar(100) NOT NULL,
`ext` json DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.`user` definition

CREATE TABLE `user` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`mobile` varchar(32) NOT NULL,
`tag` json DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `demo` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` datetime NOT NULL COMMENT '创建时间',
`gmt_modified` datetime NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
`enum_string` varchar(32) DEFAULT NULL,
`enum_int` smallint DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
`ext` json DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 运行
需要预先安装docker

sh bin/start.sh # 启动docker
sh bin/do_setup.sh # 初始化数据库, 需要等待mysql初始化完成，约10s
如果想清理数据文件, 执行下面命令即可

sh bin/cleanup.sh
在初始化数据库完成后，启动项目，可以按照2.1的方式训练数据，也可以参照api文档从数据库拉取数据来训练

# 构建镜像
mvn clean package docker:build
# 或者
# docker pull xxxx:latest

docker run -e PROFILES=demo -p 8000:8000 -d -v host-path-to-data-dir:/opt/data xxxx:latest
# 暴露本机 8000 端口，启动docker