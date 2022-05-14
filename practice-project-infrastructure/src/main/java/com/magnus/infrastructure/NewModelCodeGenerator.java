package com.magnus.infrastructure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
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

    private static final String srcMainJavaPath = "src" + sp + "main" + sp + "java";


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

        //表新生成的package名
        String tableName = scanner("表名");

        String domainModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "domain";
        String infraModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "infrastructure";

        //表在domain下的目录
        String domainModelRootPath = projectPath + sp + domainModelName;
        //表在infra下的目录
        String infraModelRootPath = projectPath + sp + infrastructureModelName;

        String tableNameInBigCamelCase = CaseUtils.toCamelCase(tableName, true, '_');
        //生成文件的路径，仅mapperXml文件的生成地址为默认地址
        //todo 判断操作系统是windows还是unix，windows上路径为\ unix上路径为\
        String doModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String mapperModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String mapperXmlModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String domainEntityModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String repositoryModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String repositoryImplModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";
        String converterImplModelRelativePath = infraModelRelativePath + sp + "dao" + sp + tableName + sp + "mapper";

        NewModelCodeGenerator.create("jdbc:mysql://localhost:3306/gstest", "root", "admin")
                .globalConfig(builder -> builder
                        .author("gs")
                        // 覆盖已生成文件
                        .fileOverride()
                        //默认生成完毕后会打开outputDir对应文件夹，关闭
                        .disableOpenDir()
                        //使用localdatatime 如果想用date可以指定为ONLY_DATE
                        .dateType(DateType.TIME_PACK) // 默认的指定输出目录 如果packageConfig中没有指定某个生成类的目录，则采用此默认目录
                )
                .strategyConfig(builder -> builder
                        //设置需要生成的表名
                        .addInclude(tableName)
                        //打开模板中的entityLombokModel标签
                        .entityBuilder()
                        //打开模板中的entityLombokModel
                        .enableLombok()
                        //打开模板中的entityColumnConstant标签
                        .enableColumnConstant()
                        .idType(IdType.AUTO)
                )
                .injectionConfig(builder -> builder
                        //
                        .beforeOutputFile((tableInfo, objectMap) -> {
                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                        })
                        //自定义模板参数
                        .customMap(ImmutableMap.<String, Object>builder()
                                //key ftl模板中参数名 value ftl模板参数值
                                //todo 通用化
                                .put("doPackagePath", getPackageName(doModelRelativePath))
                                .put("mapperPackagePath", getPackageName(mapperModelRelativePath))
                                .put("domainEntityPackagePath", getPackageName(domainEntityModelRelativePath))
                                .put("repositoryPackagePath", getPackageName(repositoryModelRelativePath))
                                .put("repositoryImplPackagePath", getPackageName(repositoryImplModelRelativePath))
                                .put("converterPackagePath", getPackageName(converterImplModelRelativePath))
                                .build())
                        //自定义模板 key生成文件绝对路径 value 模板名称
                        .customFile(ImmutableMap.<String, String>builder()
                                .put(infraModelRootPath + sp + doModelRelativePath + sp + tableNameInBigCamelCase + "DO.java", "/templates" + "/do.java.ftl")
                                .put(infraModelRootPath + sp + mapperModelRelativePath + sp + tableNameInBigCamelCase + "Mapper.java", "/templates" + "/mapper.java.ftl")
                                .put(infraModelRootPath + sp + mapperXmlModelRelativePath + sp + tableNameInBigCamelCase + "Mapper.xml", "/templates" + "/mapper.xml.ftl")
                                .put(infraModelRootPath + sp + domainEntityModelRelativePath + sp + tableNameInBigCamelCase + ".java", "/templates" + "/domainEntity.java.ftl")
                                .put(infraModelRootPath + sp + repositoryModelRelativePath + sp + tableNameInBigCamelCase + "Repository.java", "/templates" + "/repository.java.ftl")
                                .put(infraModelRootPath + sp + repositoryImplModelRelativePath + sp + tableNameInBigCamelCase + "RepositoryImpl.java", "/templates" + "/repositoryImpl.java.ftl")
                                .put(infraModelRootPath + sp + converterImplModelRelativePath + sp + tableNameInBigCamelCase + "Converter.java", "/templates" + "/converter.java.ftl")
                                .build())
                )
                .templateConfig(builder -> builder
                        //此处配置无法改变生成类的类名，不建议使用，仅留一注释作为参考
                        //.mapper("/templates" + "/mapper.java")
                        //禁掉默认会生成的controller
                        .disable(TemplateType.CONTROLLER, TemplateType.CONTROLLER, TemplateType.ENTITY,
                                TemplateType.MAPPER, TemplateType.SERVICE, TemplateType.SERVICEIMPL)

                )
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


    /**
     * src.main.java -> /src/main/java
     *
     * @param packagePath
     * @return
     */
    private static String packagePath2FilePath(String packagePath) {
        //linux mac
        String filePath;
        if (StringUtils.equals(sp, "/")) {
            filePath = packagePath.replaceAll("\\.", "/");
        }
        //windows
        filePath = packagePath.replaceAll("\\.", "\\\\");
        return filePath;
    }

    /**
     * /src/main/java/com/magnus -> com.magnus
     *
     * @param packagePath
     * @return
     */
    private static String getPackageName(String packagePath) {
        //去除前缀 src/main/java/
        packagePath = StringUtils.replace(packagePath, srcMainJavaPath + sp, "");
        //linux mac
        String filePath;
        if (StringUtils.equals(sp, "/")) {
            filePath = packagePath.replaceAll("\\/", ".");
        }
        //windows
        filePath = packagePath.replaceAll("\\\\", ".");
        return filePath;
    }


}
