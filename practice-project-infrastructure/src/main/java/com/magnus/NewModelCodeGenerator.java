package com.magnus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * mybatis plus FastAutoGenerator
 *
 * @author L.cm, lanjerry
 * @since 2021-07-22
 */
public final class NewModelCodeGenerator {

    /**
     * 斜杠
     */
    private static final String sp = File.separator;

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

    public static NewModelCodeGenerator create(String url, String username, String password) {
        return new NewModelCodeGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    public static NewModelCodeGenerator create(DataSourceConfig.Builder dataSourceConfigBuilder) {
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
        //unix下如:/home/gs/github/mybatis-practice-project
        String projectPath = System.getProperty("user.dir");
        //com/magnus
        //todo  只能是/，不能是\， 看下windows下怎么办
        String basePackagePath = "com" + sp + "magnus";
        String projectPackageName = "com.magnus";


        String domainModelName = projectName + "-domain";
        String infrastructureModelName = projectName + "-infrastructure";

        String tableName = scanner("表名");
        //表新生成的package名
        //todo 改为交互式输入
        String tableDirName = "test";

        String srcMainJavaPath = "src" + sp + "main" + sp + "java";
        //表在domain下的目录
        String domainModelRootPath = projectPath + sp + domainModelName + sp + srcMainJavaPath + sp + basePackagePath + sp + tableDirName;
        //表在infra下的目录
        String tableInfraModelPath = projectPath + sp + infrastructureModelName + srcMainJavaPath + basePackagePath + sp + tableDirName;

        //自己指定模板
        AbstractTemplateEngine renderEngine = new FreemarkerTemplateEngine();

        String tableNameInBigCamelCase = CaseUtils.toCamelCase(tableName, true, '_');
        //生成文件的路径，仅mapperXml文件的生成地址为默认地址
        //todo 判断操作系统是windows还是unix，windows上路径为\ unix上路径为\
        String doPath = projectPath + "\\" + projectName + "-infrastructure\\src\\main\\java\\" + basePackagePath + "\\infrastructure\\" + "dao\\" + tableName + "\\mapper\\" + (tableNameInBigCamelCase + "DO") + ".java";
        String mapperPath = projectPath + "\\" + projectName + "-infrastructure\\src\\main\\java\\" + basePackagePath + "\\infrastructure\\" + "dao\\" + tableName + "\\mapper\\" + (tableNameInBigCamelCase + "Mapper") + ".java";
        String domainEntityPath = projectPath + "\\" + projectName + "-infrastructure\\src\\main\\java\\" + basePackagePath + "\\infrastructure\\" + "dao\\" + tableName + "\\mapper\\" + (tableNameInBigCamelCase) + ".java";
        String repositoryPath = projectPath + "\\" + projectName + "-infrastructure\\src\\main\\java\\" + basePackagePath + "\\infrastructure\\" + "dao\\" + tableName + "\\mapper\\" + (tableNameInBigCamelCase + "Repository") + ".java";
        String repositoryImplPath = projectPath + "\\" + projectName + "-infrastructure\\src\\main\\java\\" + basePackagePath + "\\infrastructure\\" + "dao\\" + tableName + "\\mapper\\" + (tableNameInBigCamelCase + "RepositoryImpl") + ".java";

        NewModelCodeGenerator.create("jdbc:mysql://localhost:3306/gstest", "root", "admin")
                .globalConfig(builder -> {
                    builder.author("gs") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            //默认生成完毕后会打开outputDir对应文件夹，关闭
                            .disableOpenDir()
                            //使用localdatatime 如果想用date可以指定为ONLY_DATE
                            .dateType(DateType.TIME_PACK); // 默认的指定输出目录 如果packageConfig中没有指定某个生成类的目录，则采用此默认目录
                })
                //包配置（主要是路径信息） 除了mapperXml外全部自定义
                .packageConfig(builder -> {
                    builder.parent(projectPackageName) // 设置父包名
                            .pathInfo(ImmutableMap.of(
                                    OutputFile.mapperXml, tableInfraModelPath
                            ));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                    ; // 设置需要生成的表名
                })
                .injectionConfig(builder ->
                        builder
                                //
                                .beforeOutputFile((tableInfo, objectMap) -> {
                                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                                })
                                //自定义模板参数
                                .customMap(ImmutableMap.of(
                                        //todo 通用化
                                        "doPath", "com.magnus.domain",
                                        "mapperPath", "com.magnus.domain",
                                        "domainEntityPath", "com.magnus.domain",
                                        "repositoryPath", "com.magnus.domain",
                                        "repositoryImplPath", "com.magnus.domain"
                                ))
                                //自定义模板 key生成路径 value 模板名称
                                .customFile(ImmutableMap.of(
                                                doPath, "/templates" + "/do.java.ftl",
                                                mapperPath, "/templates" + "/mapper.java.ftl",
                                                domainEntityPath, "/templates" + "/domainEntity.java.ftl",
                                                repositoryPath, "/templates" + "/repository.java.ftl",
                                                repositoryImplPath, "/templates" + "/repositoryImpl.java.ftl"
                                        )
                                )
                )
                .templateConfig(builder -> {
                    builder
                            //此处配置无法改变生成类的类名，不建议使用，仅留一注释作为参考
                            //.mapper("/templates" + "/mapper.java")
                            //禁掉默认会生成的controller
                            .disable(TemplateType.CONTROLLER, TemplateType.CONTROLLER, TemplateType.ENTITY,
                                    TemplateType.MAPPER, TemplateType.SERVICE, TemplateType.SERVICEIMPL)
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine() {
                    //重写该方法，以自定义输出路径
                    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
                        String entityName = tableInfo.getEntityName();
                        String otherPath = getPathInfo(OutputFile.other);
                        customFile.forEach((key, value) -> {
                            System.out.println("otherPath:" + otherPath);
                            System.out.println("entityName:" + entityName);
                            System.out.println("key:" + key);
                            String fileName = key;
                            outputFile(new File(fileName), objectMap, value);
                        });
                    }
                })
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
