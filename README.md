# SpringbootBestPractice

## 代码生成器使用指南

1. 生成的实体类需要手动替换其中的枚举值
2. 对应的枚举值需要在目标字段上加@EnumValue注解
3. 如果有Json类型的ext字段，则需要在DO类中的@TableName注解上加上(autoResultMap = true); 并且在ext字段上的注解@TableField加上(typeHandler =
   FastjsonTypeHandler.class)

## 非controller层validator使用指南

根pom引入的spring-boot-starter-validation已经包含全部的包
在要使用validator的模块引入hibernate-validator 以使用JSR303注解即可

1. 类上加@Validated，spring仅校验有该注解标识的类下面的方法
2. 方法参数上加@Valid，让hibernate-validator校验
3. 如果参数要保证不为null，方法参数上要加@NotNull
