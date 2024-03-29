package com.magnus.infrastructure.codegenerator;

import static com.magnus.infrastructure.codegenerator.CommonOps.sp;

public class GeneratorConfig {

    public static String projectName = "practice-project";

    //即每个模块下，src/main/java后，第一层展开目录前这一段的path
    //部分应用不会加上projectName
    //部分应用会将projectName替代为一个更简短的名字
    public static String basePackagePath = "com" + sp + "magnus" + sp + projectName.replace("-", "");
    //public static String basePackagePath = "com" + sp + "magnus";
    //public static String basePackagePath = "com" + sp + "magnus" + sp + "im";

    public static String dBUrl = "jdbc:mysql://localhost:3306/gstest";
    public static String dBUserName = "root";
    public static String dBPassWord = "admin";

}
