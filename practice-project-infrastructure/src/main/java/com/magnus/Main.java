package com.magnus;

import org.apache.commons.text.CaseUtils;

public class Main {
    public static void main(String[] args) {
        String output = CaseUtils.toCamelCase("user_admin", true,'_');
        System.out.println(output);
    }
}