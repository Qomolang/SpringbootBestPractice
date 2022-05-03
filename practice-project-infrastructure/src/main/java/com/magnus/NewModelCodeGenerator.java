package com.magnus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import org.apache.commons.text.CaseUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * mybatis plus FastAutoGenerator
 *
 * @author L.cm, lanjerry
 * @since 2021-07-22
 */
public final class NewModelCodeGenerator {

    /**
     * 数据源配置 Builder
     */
    private final DataSourceConfig.Builder dataSourceConfigBuilder;

    /**
     * 全局配置 Builder
     */
    private final GlobalConfig.Builder globalConfigBuilder;

    /**
     * 包配置 Builder
     */
    private final PackageConfig.Builder packageConfigBuilder;

    /**
     * 策略配置 Builder
     */
    private final StrategyConfig.Builder strategyConfigBuilder;

    /**
     * 注入配置 Builder
     */
    private final InjectionConfig.Builder injectionConfigBuilder;

    /**
     * 模板配置 Builder
     */
    private final TemplateConfig.Builder templateConfigBuilder;

    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    private NewModelCodeGenerator(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new GlobalConfig.Builder();
        this.packageConfigBuilder = new PackageConfig.Builder();
        this.strategyConfigBuilder = new StrategyConfig.Builder();
        this.injectionConfigBuilder = new InjectionConfig.Builder();
        this.templateConfigBuilder = new TemplateConfig.Builder();
    }

    public static NewModelCodeGenerator create(@NotNull String url, String username, String password) {
        return new NewModelCodeGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    public static NewModelCodeGenerator create(@NotNull DataSourceConfig.Builder dataSourceConfigBuilder) {
        return new NewModelCodeGenerator(dataSourceConfigBuilder);
    }

    /**
     * 读取控制台输入内容
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * 控制台输入内容读取并打印提示信息
     *
     * @param message 提示信息
     * @return
     */
    public String scannerNext(String message) {
        System.out.println(message);
        String nextLine = scanner.nextLine();
        if (StringUtils.isBlank(nextLine)) {
            // 如果输入空行继续等待
            return scanner.next();
        }
        return nextLine;
    }

    /**
     * 全局配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public NewModelCodeGenerator globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    public NewModelCodeGenerator globalConfig(BiConsumer<Function<String, String>, GlobalConfig.Builder> biConsumer) {
        biConsumer.accept(message -> scannerNext(message), this.globalConfigBuilder);
        return this;
    }

    /**
     * 包配置
     *
     * @param consumer 自定义包配置
     * @return
     */
    public NewModelCodeGenerator packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    public NewModelCodeGenerator packageConfig(BiConsumer<Function<String, String>, PackageConfig.Builder> biConsumer) {
        biConsumer.accept(message -> scannerNext(message), this.packageConfigBuilder);
        return this;
    }

    /**
     * 策略配置
     *
     * @param consumer 自定义策略配置
     * @return
     */
    public NewModelCodeGenerator strategyConfig(Consumer<StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    public NewModelCodeGenerator strategyConfig(BiConsumer<Function<String, String>, StrategyConfig.Builder> biConsumer) {
        biConsumer.accept(message -> scannerNext(message), this.strategyConfigBuilder);
        return this;
    }

    /**
     * 注入配置
     *
     * @param consumer 自定义注入配置
     * @return
     */
    public NewModelCodeGenerator injectionConfig(Consumer<InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    public NewModelCodeGenerator injectionConfig(BiConsumer<Function<String, String>, InjectionConfig.Builder> biConsumer) {
        biConsumer.accept(message -> scannerNext(message), this.injectionConfigBuilder);
        return this;
    }

    /**
     * 模板配置
     *
     * @param consumer 自定义模板配置
     * @return
     */
    public NewModelCodeGenerator templateConfig(Consumer<TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);
        return this;
    }

    public NewModelCodeGenerator templateConfig(BiConsumer<Function<String, String>, TemplateConfig.Builder> biConsumer) {
        biConsumer.accept(message -> scannerNext(message), this.templateConfigBuilder);
        return this;
    }

    /**
     * 模板引擎配置
     *
     * @param templateEngine 模板引擎
     * @return
     */
    public NewModelCodeGenerator templateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    public void execute() {
        new AutoGenerator(this.dataSourceConfigBuilder.build())
                // 全局配置
                .global(this.globalConfigBuilder.build())
                // 包配置
                .packageInfo(this.packageConfigBuilder.build())
                // 策略配置
                .strategy(this.strategyConfigBuilder.build())
                // 注入配置
                .injection(this.injectionConfigBuilder.build())
                // 模板配置
                .template(this.templateConfigBuilder.build())
                // 执行
                .execute(this.templateEngine);
    }

    public static void main(String[] args) throws Exception {
        String projectName = "practice-project";
        String projectPath = System.getProperty("user.dir");

        String tableName = scanner("表名");
        String moduleName = "test";
        String domainModelRootPath = projectPath + "/" + projectName + "-domain" + "/src/main/java/" + moduleName;
        String infrastructureModelRootPath = projectPath + "/" + projectName + "-infrastructure" + "/src/main/java/" + moduleName;
        //todo 判断操作系统是windows还是unix，windows上路径为\ unix上路径为\
//        String templatesPath = projectPath + "/" + projectName + "-infrastructure" + "/src/main/java/" + "resources/templates";
        String templatesPath = projectPath + "\\" + projectName + "-infrastructure" + "\\src\\main\\java\\" + "resources\\templates";

        String parentPackageName = "com.magnus";

        //自己指定模板
        AbstractTemplateEngine renderEngine = new FreemarkerTemplateEngine();
        String basePath = parentPackageName.replaceAll("\\.", "/");
        String modelPath = projectPath + "/" + projectName + "-infrastructure/src/main/java/" + basePath + "/infrastructure/" + "dao/" + moduleName + "/model/";

        List<String> modelNames = Files.list(Paths.get(modelPath))
                .map(path -> path.toFile().getName().substring(0, path.toFile().getName().indexOf("DO.java")))
                .collect(Collectors.toList());
        //ftl模板参数
        Map<String, Object> params = Maps.newHashMap();
        params.put("package", parentPackageName + ".domain." + moduleName + ".converter");
        params.put("domainPackage", parentPackageName + ".domain." + moduleName + ".model");
        params.put("modelPackage", parentPackageName + ".infrastructure." + "dao." + moduleName + ".model");
        params.put("modelNames", modelNames);
        params.put("moduleName", CaseUtils.toCamelCase(moduleName, true));
        params.put("date", new Date().toString());
        String cvPath = projectPath + "/" + projectName + "-domain/src/main/java/" + basePath + "/domain/" + moduleName + "/converter/";
        String outputFile = cvPath + CaseUtils.toCamelCase(moduleName, true) + "Converter.java";

        renderEngine.init(null).writer(params, "/templates/converter.java.ftl", outputFile);

        String mapperPath = projectPath + "/" + projectName + "-infrastructure/src/main/java/" + basePath + "/infrastructure/" + "/dao/" + tableName
                        + "/mapper/" + tableName + ".java";

        NewModelCodeGenerator.create("jdbc:mysql://localhost:3306/gstest", "root", "admin")
                .globalConfig(builder -> {
                    builder.author("gs") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(domainModelRootPath)
                            //默认生成完毕后会打开outputDir对应文件夹，关闭
                            .disableOpenDir()
                            //使用localdatatime 如果想用date可以指定为ONLY_DATE
                            .dateType(DateType.TIME_PACK); // 默认的指定输出目录 如果packageConfig中没有指定某个生成类的目录，则采用此默认目录
                })
                //包配置（主要是路径信息）
                .packageConfig(builder -> {
                    builder.parent(parentPackageName) // 设置父包名
                            .pathInfo(ImmutableMap.of(
                                    OutputFile.mapperXml, infrastructureModelRootPath,
                                    OutputFile.entity, infrastructureModelRootPath,
                                    OutputFile.service, domainModelRootPath,
                                    OutputFile.serviceImpl, domainModelRootPath,
                                    OutputFile.mapper, infrastructureModelRootPath
                            ));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                    ; // 设置需要生成的表名
                })
                .injectionConfig(builder -> builder
                        //
                        .beforeOutputFile((tableInfo, objectMap) -> {
                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                        })
                        //自定义模板参数
                        .customMap(Collections.singletonMap("test", "baomidou"))
                        //自定义模板
//                        .customFile(Collections.singletonMap(mapperPath, "/templates" + "/mapper.java.ftl"))
                        )
                .templateConfig(builder -> {
                    builder
                            //此处配置无法改变生成类的类名，不建议使用，仅留一注释作为参考
                            .mapper("/templates" + "/mapper.java")
                            //禁掉默认会生成的controller
                            .disable(TemplateType.CONTROLLER)
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
