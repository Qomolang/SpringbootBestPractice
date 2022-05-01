//package com.magnus;
//
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.FileType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.google.common.collect.Maps;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.text.CaseUtils;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
///**
// * 对应mybatis-plus版本 3.3.2
// * @author 84028
// */
//public class OldModelCodeGenerator {
//
//    /**
//     * 项目名 用来组装路径
//     */
//    private static final String PROJECT_NAME = "practice-project";
//    /**
//     * 项目统一前缀
//     */
//    private static final String PARENT_PACKAGE_NAME = "com.magnus";
//
//
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    public static void main(String[] args) throws Exception {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        String projectName = PROJECT_NAME;
//        String parentPackageName = PARENT_PACKAGE_NAME;
//
//        String url = "jdbc:mysql://localhost:3306/gstest";
//        String username = "root";
//        String password = "admin";
//
//        AbstractTemplateEngine renderEngine = new FreemarkerTemplateEngine();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/" + projectName + "-infrastructure/src/main/java");
//        gc.setAuthor("edu-mybatis-plus-auto-generator");
//        gc.setOpen(false);
//        //实体属性 Swagger2 注解 暂不需要
//        //gc.setSwagger2(true);
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = getDataSourceConfig(url, username, password);
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = getPackageConfig(parentPackageName);
//        mpg.setPackageInfo(pc);
//
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<>(16);
//                map.put("moduleName", CaseUtils.toCamelCase(pc.getModuleName(), true));
//                map.put("parentPackageName", parentPackageName);
//                this.setMap(map);
//            }
//        };
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        String basePath = parentPackageName.replaceAll("\\.", "/");
//        // 自定义配置会被优先输出
//        extracted(projectName, projectPath, pc, focList, basePath);
//        cfg.setFileCreate(new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                new File(filePath).getParentFile().mkdirs();
//                //key ftl模板中使用的变量 value 用来确定生成类的路径
//                configBuilder.getPackageInfo().put("Service", parentPackageName + ".domain." + pc.getModuleName() + ".repository");
//                configBuilder.getPackageInfo().put("Domain", parentPackageName + ".domain." + pc.getModuleName() + ".model");
//                configBuilder.getPackageInfo().put("Entity", parentPackageName + ".infrastructure." + "dao." + pc.getModuleName() + ".model");
//                configBuilder.getPackageInfo().put("ServiceImpl", parentPackageName + ".domain." + pc.getModuleName());
//                configBuilder.getPackageInfo().put("Mapper", parentPackageName + ".infrastructure." + "dao." + pc.getModuleName() + ".mapper");
//
//                //converter比较特殊 此处影响的是其他类import它的路径 生成类的路径在独立方法中
//                configBuilder.getPackageInfo().put("Converter", parentPackageName + ".domain." + pc.getModuleName()+ ".converter");
//                return true;
//            }
//        });
//
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        templateConfig.setXml(null);
//        templateConfig.disable(TemplateType.SERVICE, TemplateType.CONTROLLER, TemplateType.ENTITY, TemplateType.MAPPER);
//        mpg.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        // 生成字段常量
//        strategy.setEntityColumnConstant(true);
//        strategy.setEntityTableFieldAnnotationEnable(true);
//        // 公共父类
//        // 写于父类中的公共字段
////        strategy.setSuperEntityClass(BaseEntity.class);
//
//        // DO NOT REMOVE IT！！！
//        System.out.println(strategy.getSuperEntityColumns());
//
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(renderEngine);
//
//        generateTableClz(mpg);
//
//        generateConverter(projectPath, projectName, basePath, pc.getModuleName(), parentPackageName, renderEngine);
//    }
//
//    private static void extracted(String projectName,
//                                  String projectPath,
//                                  PackageConfig pc,
//                                  List<FileOutConfig> focList,
//                                  String basePath) {
//        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-infrastructure/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        focList.add(new FileOutConfig("/templates/repository.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-domain/src/main/java/" + basePath + "/domain/" + pc.getModuleName()
//                        + "/" + pc.getService() + "/" + tableInfo.getEntityName() + "Repository.java";
//            }
//        });
//        focList.add(new FileOutConfig("/templates/domainEntity.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-domain/src/main/java/" + basePath + "/domain/" + pc.getModuleName()
//                        + "/model/" + tableInfo.getEntityName() + ".java";
//            }
//        });
//        focList.add(new FileOutConfig("/templates/repositoryImpl.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-domain/src/main/java/" + basePath + "/domain/" + pc.getModuleName() + "/"
//                        + tableInfo.getEntityName() + "RepositoryImpl.java";
//            }
//        });
//        focList.add(new FileOutConfig("/templates/do.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-infrastructure/src/main/java/" + basePath + "/infrastructure/" + "/dao/" + pc.getModuleName()
//                        + "/model/" + tableInfo.getEntityName() + "DO.java";
//            }
//        });
//        focList.add(new FileOutConfig("/templates/mapper.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + projectName + "-infrastructure/src/main/java/" + basePath + "/infrastructure/" + "/dao/" + pc.getModuleName()
//                        + "/mapper/" + tableInfo.getMapperName() + ".java";
//            }
//        });
//    }
//
//    private static DataSourceConfig getDataSourceConfig(String url, String username, String password) {
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl(url);
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername(username);
//        dsc.setPassword(password);
//        return dsc;
//    }
//
//    private static PackageConfig getPackageConfig(String parentPackageName) {
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("请输入模块名"));
//        pc.setParent(parentPackageName);
//        pc.setEntity("model");
//        pc.setMapper("mapper");
//        pc.setController("controller");
//        pc.setService("repository");
//        pc.setServiceImpl("repository");
//        return pc;
//    }
//
//    private static void generateTableClz(AutoGenerator mpg) {
//        mpg.execute();
//    }
//
//    private static void generateConverter(String projectPath, String projectName, String basePath, String moduleName, String parentPackageName, AbstractTemplateEngine renderEngine) throws Exception {
//        String scannerRemind = "是否覆盖:";
//        String judge = "y";
//        // scan package
//        String modelPath = projectPath + "/" + projectName + "-infrastructure/src/main/java/" + basePath + "/infrastructure/" + "dao/" + moduleName + "/model/";
//        List<String> modelNames = Files.list(Paths.get(modelPath))
//                .map(path -> path.toFile().getName().substring(0, path.toFile().getName().indexOf("DO.java")))
//                .collect(Collectors.toList());
//
//        String cvPath = projectPath + "/" + projectName + "-domain/src/main/java/" + basePath + "/domain/" + moduleName + "/converter/";
//        String outputFile = cvPath + CaseUtils.toCamelCase(moduleName, true) + "Converter.java";
//
//        if (Files.exists(Paths.get(outputFile))) {
//            System.err.println(CaseUtils.toCamelCase(moduleName, true) + ".java 文件已存在，如需覆盖，请输入y");
//            if (!scanner(scannerRemind).trim().equalsIgnoreCase(judge)) {
//                return;
//            }
//        }
//        FileUtils.forceMkdir(new File(cvPath));
//
//        //ftl模板参数
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("package", parentPackageName + ".domain." + moduleName + ".converter");
//        params.put("domainPackage", parentPackageName + ".domain." + moduleName + ".model");
//        params.put("modelPackage", parentPackageName + ".infrastructure." + "dao." + moduleName + ".model");
//        params.put("modelNames", modelNames);
//        params.put("moduleName", CaseUtils.toCamelCase(moduleName, true));
//        params.put("date", new Date().toString());
//        renderEngine.init(null).writer(params, "/templates/converter.java.ftl", outputFile);
//    }
//
//
//}
