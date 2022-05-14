package com.magnus.infrastructure;

import java.io.File;

public class Test {

    private static final String sp = File.separator;

    public static void main(String[] args) {
//        String output = CaseUtils.toCamelCase("user_admin", true, '_');
//        System.out.println(output);

        System.out.println("com\\magus\\abc".replaceAll("\\\\", "."));

        System.out.println("com/magus/abc".replaceAll("\\/", "."));
        System.out.println(fileName2PackageName("com\\magus\\abc"));
    }

    public static String fileName2PackageName(String packagePath) {
        //linux mac
        String filePath;
        if (org.apache.commons.lang3.StringUtils.equals(sp, "/")) {
            filePath = packagePath.replaceAll("\\/", ".");
        }
        //windows
        filePath = packagePath.replaceAll("\\\\", ".");
        return filePath;
    }

}