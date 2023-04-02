# SpringbootBestPractice

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
`gmt_create` timestamp NOT NULL COMMENT '创建时间',
`gmt_modified` timestamp NOT NULL COMMENT '修改时间',
`create_by` bigint NOT NULL COMMENT '创建人',
`modified_by` bigint NOT NULL COMMENT '修改人',
`user_id` bigint NOT NULL,
`tenant_id` bigint NOT NULL,
`nick_name` varchar(100) NOT NULL,
`emp_code` varchar(512) NOT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`),
UNIQUE KEY `employee_un` (`emp_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.org definition

CREATE TABLE `org` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` timestamp NOT NULL,
`gmt_modified` timestamp NOT NULL,
`create_by` bigint NOT NULL,
`modified_by` bigint NOT NULL,
`name` varchar(512) NOT NULL,
`full_name` varchar(100) DEFAULT NULL,
`corp_id` varchar(100) NOT NULL,
`super_admin_uid` bigint NOT NULL,
`ext` json DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.dept definition

CREATE TABLE `dept` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` timestamp NOT NULL,
`gmt_modified` timestamp NOT NULL,
`create_by` bigint NOT NULL,
`modified_by` bigint NOT NULL,
`name` varchar(512) NOT NULL,
`member_size` bigint DEFAULT NULL,
`org_id` varchar(100) NOT NULL,
`ext` json DEFAULT NULL,
`is_deleted` bigint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gstest.`user` definition

CREATE TABLE `user` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` timestamp NOT NULL,
`gmt_modified` timestamp NOT NULL,
`create_by` bigint NOT NULL,
`modified_by` bigint NOT NULL,
`name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
`mobile` varchar(32) NOT NULL,
`tag` json DEFAULT NULL,
`is_deleted` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `demo` (
`id` bigint NOT NULL AUTO_INCREMENT,
`gmt_create` timestamp NOT NULL,
`gmt_modified` timestamp NOT NULL,
`create_by` bigint NOT NULL,
`modified_by` bigint NOT NULL,
`name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
`ext` json DEFAULT NULL,
`enum` json DEFAULT NULL,
`enum_int` json DEFAULT NULL,
`is_deleted` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;