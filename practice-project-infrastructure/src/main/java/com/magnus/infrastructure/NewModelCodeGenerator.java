package com.magnus.infrastructure;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

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

    private static final Gson gson = new Gson();

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

    /**
     * 读取控制台输入内容
     */
    private final Scanner scanner = new Scanner(System.in);

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
        String dBUrl = "jdbc:mysql://localhost:3306/gstest";
        String dBUserName = "root";
        String dBPassWord = "admin";

        //表新生成的package名
        String tableName = scanner("表名");
        String dirName = scanner("目录名");

        //unix下如:/home/gs/github/mybatis-practice-project
        String projectPath = System.getProperty("user.dir");

        String projectName = "practice-project";

        String infrastructureModelName = projectName + "-infrastructure";
        String domainModelName = projectName + "-domain";
        String serviceModelName = projectName + "-service";
        String starterModelName = projectName + "-starter";

        //com/magnus
        String basePackagePath = "com" + sp + "magnus";

        String infraModelRootPath = projectPath + sp + infrastructureModelName;
        String domainModelRootPath = projectPath + sp + domainModelName;
        String serviceModelRootPath = projectPath + sp + serviceModelName;
        String starterModelRootPath = projectPath + sp + starterModelName;

        // src/main/java/com/projectName/infrastructure
        String infraModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "infrastructure";
        String domainModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "domain";
        String serviceModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "service";
        String starterModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "controller";

        String tableNameInBigCamelCase = CaseUtils.toCamelCase(tableName, true, '_');

        //生成文件的路径
        String doDirRelativeModelPath = infraModelRelativePath + sp + "dao" + sp + dirName + sp + "model";
        String mapperDirRelativeModelPath = infraModelRelativePath + sp + "dao" + sp + dirName + sp + "mapper";
        String mapperXmlDirRelativeModelPath = "src" + sp + "main" + sp + "resources" + sp + "mapper" + sp + dirName;
        String domainEntityDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "model";
        String repositoryDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "repository";
        String repositoryImplDirRelativeModelPath = domainModelRelativePath + sp + dirName;
        String converterImplDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "converter";
        String serviceDirRelativeModelPath = serviceModelRelativePath + sp + dirName;
        String starterDirRelativeModelPath = starterModelRelativePath + sp + "api";

        //决定是否生成service层及controller层
        Map<String, String> customFileMap = buildCustomFile(serviceModelRootPath,
                serviceDirRelativeModelPath,
                starterModelRootPath,
                starterDirRelativeModelPath,
                tableNameInBigCamelCase);

        NewModelCodeGenerator.create(dBUrl, dBUserName, dBPassWord)
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
                        //打开模板中的 entityLombokModel 标签
                        .entityBuilder()
                        //！！！不要开启enableRemoveIsPrefix 与目前预处理部分冲突
                        //打开 entityLombokModel 标签
                        .enableLombok()
                        //打开 entityColumnConstant 标签
                        .enableColumnConstant()
                        //打开 convert 标签
                        .enableTableFieldAnnotation()
                        //给表字段添加填充
                        .addTableFills(new Property("createTime", FieldFill.INSERT))
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        .idType(IdType.AUTO)
                        .controllerBuilder()
                        //打开 restControllerStyle 标签
                        .enableRestStyle()
                )
                .injectionConfig(builder -> builder
                        //预处理
                        .beforeOutputFile((tableInfo, objectMap) ->
                                        //字段名 is_deleted 替换为 deleteTag
                                {
                                    for (TableField field : tableInfo.getFields()) {
                                        boolean booleanType = field.getColumnType() == DbColumnType.BOOLEAN || field.getColumnType() == DbColumnType.BASE_BOOLEAN;
                                        boolean startWithIs = field.getColumnName().startsWith("is");
                                        if (booleanType && startWithIs) {
                                            //逻辑删除标记单独处理
                                            if (StringUtils.equals(field.getPropertyName(), "isDeleted")) {
                                                field.setPropertyName("deleteTag", field.getColumnType());
                                                continue;
                                            }

                                            String propertyName = field.getPropertyName();
                                            propertyName = StringUtils.truncate(propertyName, 2, propertyName.length());
                                            propertyName = StringUtils.uncapitalize(propertyName);
                                            propertyName = propertyName + "Tag";

                                            field.setPropertyName(propertyName, field.getColumnType());
                                        }
                                    }
                                }
                        )
                        //自定义模板参数
                        .customMap(ImmutableMap.<String, Object>builder()
                                //key ftl模板中参数名 value ftl模板参数值
                                .put("doPackagePath", getPackageName(doDirRelativeModelPath))
                                .put("mapperPackagePath", getPackageName(mapperDirRelativeModelPath))
                                .put("domainEntityPackagePath", getPackageName(domainEntityDirRelativeModelPath))
                                .put("repositoryPackagePath", getPackageName(repositoryDirRelativeModelPath))
                                .put("repositoryImplPackagePath", getPackageName(repositoryImplDirRelativeModelPath))
                                .put("converterPackagePath", getPackageName(converterImplDirRelativeModelPath))
                                .put("servicePackagePath", getPackageName(serviceDirRelativeModelPath))
                                .put("starterPackagePath", getPackageName(starterDirRelativeModelPath))
                                .build())
                        //自定义模板 key:生成文件绝对路径 value:模板名称
                        .customFile(ImmutableMap.<String, String>builder()
                                .put(infraModelRootPath + sp + doDirRelativeModelPath + sp + tableNameInBigCamelCase + "DO.java", "/templates" + "/do.java.ftl")
                                .put(infraModelRootPath + sp + mapperDirRelativeModelPath + sp + tableNameInBigCamelCase + "Mapper.java", "/templates" + "/mapper.java.ftl")
                                .put(infraModelRootPath + sp + mapperXmlDirRelativeModelPath + sp + tableNameInBigCamelCase + "Mapper.xml", "/templates" + "/mapper.xml.ftl")
                                .put(domainModelRootPath + sp + domainEntityDirRelativeModelPath + sp + tableNameInBigCamelCase + ".java", "/templates" + "/domainEntity.java.ftl")
                                .put(domainModelRootPath + sp + repositoryDirRelativeModelPath + sp + tableNameInBigCamelCase + "Repository.java", "/templates" + "/repository.java.ftl")
                                .put(domainModelRootPath + sp + repositoryImplDirRelativeModelPath + sp + tableNameInBigCamelCase + "RepositoryImpl.java", "/templates" + "/repositoryImpl.java.ftl")
                                .put(domainModelRootPath + sp + converterImplDirRelativeModelPath + sp + tableNameInBigCamelCase + "Converter.java", "/templates" + "/converter.java.ftl")
                                //可生成optional的的文件
                                .putAll(customFileMap)
                                .build())
                )
                .templateConfig(builder -> builder
                        //禁掉默认会生成的controller
                        .disable(TemplateType.CONTROLLER, TemplateType.CONTROLLER, TemplateType.ENTITY,
                                TemplateType.MAPPER, TemplateType.SERVICE, TemplateType.SERVICEIMPL)
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine() {
                    //重写该方法，以自定义输出路径
                    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
                        customFile.forEach((key, value) -> outputFile(new File(key), objectMap, value));
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

    /**
     * 选择是否生成的自定义文件
     */
    private static Map<String, String> buildCustomFile(String serviceModelRootPath,
                                                       String serviceDirRelativeModelPath,
                                                       String starterModelRootPath,
                                                       String starterDirRelativeModelPath,
                                                       String tableNameInBigCamelCase
    ) {
        Map<String, String> output = new HashMap<>();

        String judge = "y";
        if (!scanner("是否生成service层(确认请输入y)").trim().equalsIgnoreCase(judge)) {
            return output;
        }
        output.put(serviceModelRootPath + sp + serviceDirRelativeModelPath + sp + tableNameInBigCamelCase + "BizService.java", "/templates" + "/service.java.ftl");
        if (!scanner("是否生成controller层(确认请输入y)").trim().equalsIgnoreCase(judge)) {
            return output;
        }
        output.put(starterModelRootPath + sp + starterDirRelativeModelPath + sp + tableNameInBigCamelCase + "Controller.java", "/templates" + "/controller.java.ftl");
        return output;
    }

}
