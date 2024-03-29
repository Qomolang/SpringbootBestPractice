package com.magnus.infrastructure.codegenerator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

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
     * 生成dao层及domain层自定义文件
     */
    public static List<CustomFile> buildDaoAndDomainCustomFile(String infraModelRootPath,
                                                               String domainModelRootPath,
                                                               String doDirRelativeModelPath,
                                                               String mapperXmlDirRelativeModelPath,
                                                               String mapperDirRelativeModelPath,
                                                               String domainEntityDirRelativeModelPath,
                                                               String repositoryImplDirRelativeModelPath,
                                                               String converterDirRelativeModelPath,
                                                               String repositoryDirRelativeModelPath,
                                                               String fileBaseName
    ) {
        List<CustomFile> output = new ArrayList<>();

        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + doDirRelativeModelPath + sp + fileBaseName + "DO.java")
                .templatePath("/templates" + "/do.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperDirRelativeModelPath + sp + fileBaseName + "Mapper.java")
                .templatePath("/templates" + "/mapper.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperXmlDirRelativeModelPath + sp + fileBaseName + "Mapper.xml")
                .templatePath("/templates" + "/mapper.xml.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + domainEntityDirRelativeModelPath + sp + fileBaseName + ".java")
                .templatePath("/templates" + "/domainEntity.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + repositoryDirRelativeModelPath + sp + fileBaseName + "Repository.java")
                .templatePath("/templates" + "/repository.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + repositoryImplDirRelativeModelPath + sp + fileBaseName + "RepositoryImpl.java")
                .templatePath("/templates" + "/repositoryImpl.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + converterDirRelativeModelPath + sp + fileBaseName + "Converter.java")
                .templatePath("/templates" + "/converter.java.ftl")
                .build());

        return output;
    }

    /**
     * 生成dao层及domain层自定义文件
     */
    public static List<CustomFile> buildTkDaoAndDomainCustomFile(String infraModelRootPath,
                                                               String domainModelRootPath,
                                                               String doDirRelativeModelPath,
                                                               String mapperXmlDirRelativeModelPath,
                                                               String mapperDirRelativeModelPath,
                                                               String domainEntityDirRelativeModelPath,
                                                               String repositoryImplDirRelativeModelPath,
                                                               String converterDirRelativeModelPath,
                                                               String repositoryDirRelativeModelPath,
                                                               String fileBaseName
    ) {
        List<CustomFile> output = new ArrayList<>();

        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + doDirRelativeModelPath + sp + fileBaseName + "DO.java")
                .templatePath("/templates/tk" + "/do.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperDirRelativeModelPath + sp + fileBaseName + "Mapper.java")
                .templatePath("/templates/tk" + "/mapper.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperXmlDirRelativeModelPath + sp + fileBaseName + "Mapper.xml")
                .templatePath("/templates/tk" + "/mapper.xml.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + domainEntityDirRelativeModelPath + sp + fileBaseName + ".java")
                .templatePath("/templates" + "/domainEntity.java.ftl")
                .build());
        //tk不提供Repository层
        //output.add(new CustomFile.Builder()
        //        .filePath(domainModelRootPath + sp + repositoryDirRelativeModelPath + sp + fileBaseName + "Repository.java")
        //        .templatePath("/templates" + "/repository.java.ftl")
        //        .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + repositoryImplDirRelativeModelPath + sp + fileBaseName + "RepositoryImpl.java")
                .templatePath("/templates/tk" + "/repositoryImpl.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + converterDirRelativeModelPath + sp + fileBaseName + "Converter.java")
                .templatePath("/templates" + "/converter.java.ftl")
                .build());

        return output;
    }

    /**
     * 生成dao层及domain层自定义文件
     */
    public static List<CustomFile> buildVanillaDaoAndDomainCustomFile(String infraModelRootPath,
                                                                 String domainModelRootPath,
                                                                 String doDirRelativeModelPath,
                                                                 String mapperXmlDirRelativeModelPath,
                                                                 String mapperDirRelativeModelPath,
                                                                 String domainEntityDirRelativeModelPath,
                                                                 String repositoryImplDirRelativeModelPath,
                                                                 String converterDirRelativeModelPath,
                                                                 String repositoryDirRelativeModelPath,
                                                                 String fileBaseName
    ) {
        List<CustomFile> output = new ArrayList<>();

        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + doDirRelativeModelPath + sp + fileBaseName + "DO.java")
                .templatePath("/templates/vanilla" + "/do.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperDirRelativeModelPath + sp + fileBaseName + "Mapper.java")
                .templatePath("/templates/vanilla" + "/mapper.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(infraModelRootPath + sp + mapperXmlDirRelativeModelPath + sp + fileBaseName + "Mapper.xml")
                .templatePath("/templates/vanilla" + "/mapper.xml.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + domainEntityDirRelativeModelPath + sp + fileBaseName + ".java")
                .templatePath("/templates" + "/domainEntity.java.ftl")
                .build());
        //tk不提供Repository层
        //output.add(new CustomFile.Builder()
        //        .filePath(domainModelRootPath + sp + repositoryDirRelativeModelPath + sp + fileBaseName + "Repository.java")
        //        .templatePath("/templates" + "/repository.java.ftl")
        //        .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + repositoryImplDirRelativeModelPath + sp + fileBaseName + "RepositoryImpl.java")
                .templatePath("/templates/vanilla" + "/repositoryImpl.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(domainModelRootPath + sp + converterDirRelativeModelPath + sp + fileBaseName + "Converter.java")
                .templatePath("/templates" + "/converter.java.ftl")
                .build());

        return output;
    }

    /**
     * 生成service层及controller层自定义文件
     */
    public static List<CustomFile> buildServiceAndControllerCustomFile(String serviceModelRootPath,
                                                                       String serviceDirRelativeModelPath,
                                                                       String serviceConverterDirRelativeModelPath,
                                                                       String starterModelRootPath,
                                                                       String starterDirRelativeModelPath,
                                                                       String apiModelRootPath,
                                                                       String apiDirRelativeModelPath,
                                                                       String fileBaseName
    ) {
        List<CustomFile> output = new ArrayList<>();

        String yes = "y";
        if (!scanner("是否生成service层(默认跳过，确认请输入y)").trim().equalsIgnoreCase(yes)) {
            return output;
        }

        if (scanner("是否按照读写分离生成service层(默认跳过，确认请输入y)").trim().equalsIgnoreCase(yes)) {
            output.add(new CustomFile.Builder()
                    .filePath(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "ReadService.java")
                    .templatePath("/templates" + "/readService.java.ftl")
                    .build());
            output.add(new CustomFile.Builder()
                    .filePath(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "WriteService.java")
                    .templatePath("/templates" + "/writeService.java.ftl")
                    .build());
            output.add(new CustomFile.Builder()
                    .filePath(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "BizService.java")
                    .templatePath("/templates" + "/bizService.java.ftl")
                    .build());
        } else {
            output.add(new CustomFile.Builder()
                    .filePath(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + fileBaseName + "Service.java")
                    .templatePath("/templates" + "/service.java.ftl")
                    .build());
        }

        if (!scanner("是否生成controller层(默认跳过，确认请输入y)").trim().equalsIgnoreCase(yes)) {
            return output;
        }
        output.add(new CustomFile.Builder()
                .filePath(starterModelRootPath + sp + starterDirRelativeModelPath + sp + fileBaseName + "Controller.java")
                .templatePath("/templates" + "/controller.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(apiModelRootPath + sp + apiDirRelativeModelPath + sp + fileBaseName + "ExampleRequest.java")
                .templatePath("/templates" + "/request.java.ftl")
                .build());
        output.add(new CustomFile.Builder()
                .filePath(serviceModelRootPath + sp + serviceConverterDirRelativeModelPath + sp + fileBaseName + "ServiceConverter.java")
                .templatePath("/templates" + "/serviceConverter.java.ftl")
                .build());

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
        String ipt = scanner.nextLine();
        if (StringUtils.isNotBlank(ipt)) {
            return ipt;
        } else {
            return "";
        }

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
