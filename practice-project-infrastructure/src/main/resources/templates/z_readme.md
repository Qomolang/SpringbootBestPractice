目录下的模板配合mybatis-plus-generator使用

### 注意

1. 和mybatis通用配置文件不兼容
2. typeHandler在ResultMap中完成标识 在对应的Json字段中添加 如 typeHandler="com.aliyun.xxx.yyy.JsonTypeHandler"