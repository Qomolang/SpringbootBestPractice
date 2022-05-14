package com.magnus.infrastructure;

import org.apache.commons.text.CaseUtils;

public class Test {
    public static void main(String[] args) {
        String output = CaseUtils.toCamelCase("user_admin", true, '_');
        System.out.println(output);
    }
}