package com.magnus.infrastructure.codegenerator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author gaosong
 */
public class CommonOps {

    /**
     * 斜杠
     */
    public static final String sp = File.separator;

    public static final String srcMainJavaPath = "src" + sp + "main" + sp + "java";

    /**
     * 读取控制台输入内容
     */
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * 决定是否生成service层及controller层
     */
    public static Map<String, String> buildCustomHierarchy(String serviceModelRootPath,
                                                            String serviceDirRelativeModelPath,
                                                            String serviceConverterDirRelativeModelPath,
                                                            String starterModelRootPath,
                                                            String starterDirRelativeModelPath,
                                                            String apiModelRootPath,
                                                            String apiDirRelativeModelPath,
                                                            String fileBaseName
    ) {
        Map<String, String> output = new HashMap<>();

        String judge = "y";
        if (!scanner("是否生成service层(确认请输入y)").trim().equalsIgnoreCase(judge)) {
            return output;
        }
        output.put(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "ReadService.java", "/templates" + "/readService.java.ftl");
        output.put(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "WriteService.java", "/templates" + "/writeService.java.ftl");
        output.put(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "BizService.java", "/templates" + "/bizService.java.ftl");

        if (!scanner("是否生成controller层(确认请输入y)").trim().equalsIgnoreCase(judge)) {
            return output;
        }
        output.put(starterModelRootPath + sp + starterDirRelativeModelPath + sp + fileBaseName + "Controller.java", "/templates" + "/controller.java.ftl");
        output.put(apiModelRootPath + sp + apiDirRelativeModelPath + sp + fileBaseName + "ExampleRequest.java", "/templates" + "/request.java.ftl");
        output.put(serviceModelRootPath + sp + serviceConverterDirRelativeModelPath + sp + fileBaseName + "ServiceConverter.java", "/templates" + "/serviceConverter.java.ftl");

        return output;
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {

        StringBuilder help = new StringBuilder();
        help.append("请输入 " + tip + ":");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * /src/main/java/com/magnus -> com.magnus
     *
     * @param packagePath
     * @return
     */
    public static String getPackageName(String packagePath) {
        //去除前缀 src/main/java/
        packagePath = StringUtils.replace(packagePath, srcMainJavaPath + sp, "");
        //linux mac
        String filePath;
        if (StringUtils.equals(sp, "/")) {
            filePath = packagePath.replaceAll("\\/", ".");
            return filePath;
        }
        //windows
        filePath = packagePath.replaceAll("\\\\", ".");
        return filePath;
    }

}
