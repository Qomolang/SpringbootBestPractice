# SpringbootBestPractice

## 代码生成器使用指南

1. 生成的实体类需要手动替换其中的枚举值
2. 对应的枚举值需要在目标字段上加@EnumValue注解
3. 如果有Json类型的ext字段，则需要在DO类中的@TableName注解上加上(autoResultMap = true); 并且在ext字段上的注解@TableField加上(typeHandler =
   FastjsonTypeHandler.class)
