该包下面的模板配合mybatis-plus-generator使用

### 注意

1. 尚未与mybatis通用配置文件兼容，但已开启enableBaseResultMap标签完成自动映射
2. typeHandler在ResultMap中完成标识 在对应的Json字段中添加 如 typeHandler="com.aliyun.edu.xxx.JsonTypeHandler"