package com.magnus.infrastructure.codegenerator;

import static com.magnus.infrastructure.codegenerator.CommonOps.sp;

public class GeneratorConfig {

   public static String projectName = "practice-project";
    public static   String basePackagePath = "com" + sp + "magnus" + CommonOps.sp + projectName.replace("-", "");

    public static  String dBUrl = "jdbc:mysql://localhost:3306/gstest";
    public static   String dBUserName = "root";
    public static  String dBPassWord = "admin";

}
