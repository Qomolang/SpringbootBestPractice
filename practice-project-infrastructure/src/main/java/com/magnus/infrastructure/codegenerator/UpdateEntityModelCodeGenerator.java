package com.magnus.infrastructure.codegenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.magnus.infrastructure.codegenerator.CommonOps.*;
import static com.magnus.infrastructure.codegenerator.CommonOps.sp;

/**
 * mybatis plus FastAutoGenerator
 *
 * @author L.cm, lanjerry
 * @since 2021-07-22
 */
public final class UpdateEntityModelCodeGenerator {

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

    private UpdateEntityModelCodeGenerator(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new GlobalConfig.Builder();
        this.packageConfigBuilder = new PackageConfig.Builder();
        this.strategyConfigBuilder = new StrategyConfig.Builder();
        this.injectionConfigBuilder = new InjectionConfig.Builder();
        this.templateConfigBuilder = new TemplateConfig.Builder();
    }

    public static UpdateEntityModelCodeGenerator create(String url, String username, String password) {
        return new UpdateEntityModelCodeGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    /**
     * 数据源配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public UpdateEntityModelCodeGenerator dataSourceConfig(Consumer<DataSourceConfig.Builder> consumer) {
        consumer.accept(this.dataSourceConfigBuilder);
        return this;
    }


    /**
     * 全局配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public UpdateEntityModelCodeGenerator globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    /**
     * 包配置
     *
     * @param consumer 自定义包配置
     * @return
     */
    public UpdateEntityModelCodeGenerator packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    /**
     * 策略配置
     *
     * @param consumer 自定义策略配置
     * @return
     */
    public UpdateEntityModelCodeGenerator strategyConfig(Consumer<StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    /**
     * 注入配置
     *
     * @param consumer 自定义注入配置
     * @return
     */
    public UpdateEntityModelCodeGenerator injectionConfig(Consumer<InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    /**
     * 模板配置
     *
     * @param consumer 自定义模板配置
     * @return
     */
    public UpdateEntityModelCodeGenerator templateConfig(Consumer<TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);
        return this;
    }

    /**
     * 模板引擎配置
     *
     * @param templateEngine 模板引擎
     * @return
     */
    public UpdateEntityModelCodeGenerator templateEngine(AbstractTemplateEngine templateEngine) {
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

    public static void main(String[] args) {
        String projectName = GeneratorConfig.projectName;
        String basePackagePath = GeneratorConfig.basePackagePath;

        String doCreateTime = DBTimeEnum.CreateTime.getCode();
        String doUpdateTime = DBTimeEnum.UpdateTIme.getCode();

        //表新生成的package名
        String tableName = scanner("表名");
        String tablePrefix = scanner("去除表前缀（如忽略请直接回车）");
        String dirName = scanner("目录名");

        //unix下如:/home/gs/github/mybatis-practice-project
        String projectPath = System.getProperty("user.dir");

        String infrastructureModelName = projectName + "-infrastructure";
        String domainModelName = projectName + "-domain";
        String serviceModelName = projectName + "-service";
        String starterModelName = projectName + "-starter";
        String apiModelName = projectName + "-api";

        String infraModelRootPath = projectPath + sp + infrastructureModelName;
        String domainModelRootPath = projectPath + sp + domainModelName;
        String serviceModelRootPath = projectPath + sp + serviceModelName;
        String starterModelRootPath = projectPath + sp + starterModelName;
        String apiModelRootPath = projectPath + sp + apiModelName;

        // src/main/java/com/projectName/infrastructure
        String infraModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "infrastructure";
        String domainModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "domain";
        String serviceModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "service";
        String starterModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "controller";
        String apiModelRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "api";

        String tableTempName = tableName.replace(tablePrefix, "");
        String fileBaseName = CaseUtils.toCamelCase(tableTempName, true, '_');

        //生成文件的路径
        String doDirRelativeModelPath = infraModelRelativePath + sp + "dao" + sp + dirName + sp + "model";
        String mapperDirRelativeModelPath = infraModelRelativePath + sp + "dao" + sp + dirName + sp + "mapper";
        String mapperXmlDirRelativeModelPath = "src" + sp + "main" + sp + "resources" + sp + "mapper" + sp + dirName;
        String domainEntityDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "model";
        String repositoryDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "repository";
        String repositoryImplDirRelativeModelPath = domainModelRelativePath + sp + dirName;
        String converterDirRelativeModelPath = domainModelRelativePath + sp + dirName + sp + "converter";
        String serviceDirRelativeModelPath = serviceModelRelativePath + sp + dirName;
        String serviceConverterDirRelativeModelPath = serviceModelRelativePath + sp + dirName + sp + "converter";
        String starterDirRelativeModelPath = starterModelRelativePath + sp + "api";
        String requestDirRelativeModelPath = apiModelRelativePath + sp + "model" + sp + "request" + sp + dirName;

        UpdateEntityModelCodeGenerator.create(GeneratorConfig.dBUrl, GeneratorConfig.dBUserName, GeneratorConfig.dBPassWord)
                .dataSourceConfig(builder ->
                        //默认情况下，mybatis-plus的生成器会将数据库DOUBLE类型会被转化为Object类型，转为java Double类型
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.DOUBLE) {
                                return DbColumnType.DOUBLE;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        }))
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
                        .addTablePrefix(tablePrefix)
                        //打开模板中的 entityLombokModel 标签
                        .entityBuilder()
                        //！！！不要开启enableRemoveIsPrefix 与目前预处理部分冲突
                        //打开 entityLombokModel 标签
                        .enableLombok()
                        //打开 entityColumnConstant 标签
                        //.enableColumnConstant()
                        //打开 convert 标签
                        .enableTableFieldAnnotation()
                        //给表字段添加填充
                        .addTableFills(new Property(doCreateTime, FieldFill.INSERT))
                        .addTableFills(new Property(doUpdateTime, FieldFill.INSERT_UPDATE))
                        .idType(IdType.AUTO)
                        .controllerBuilder()
                        //打开 restControllerStyle 标签
                        .enableRestStyle()
                        .mapperBuilder()
                        //开启该标签后，在xml文件中生成字段映射
                        .enableBaseResultMap()
                        //开启该标签后，在xml文件中生成通用查询结果列
                        .enableBaseColumnList()
                )
                .injectionConfig(builder -> builder
                        //预处理
                        .beforeOutputFile((tableInfo, objectMap) ->
                                        //字段名 is_deleted 替换为 deleteTag
                                {
                                    for (TableField field : tableInfo.getFields()) {

                                        //逻辑删除标记单独处理
                                        if (StringUtils.equals(field.getPropertyName(), "isDeleted")) {
                                            field.setPropertyName("deleteTag", field.getColumnType());
                                            continue;
                                        }

                                        boolean booleanType = field.getColumnType() == DbColumnType.BOOLEAN || field.getColumnType() == DbColumnType.BASE_BOOLEAN;
                                        boolean startWithIs = field.getPropertyName().startsWith("is");

                                        if (booleanType && startWithIs) {
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
                                .put("converterPackagePath", getPackageName(converterDirRelativeModelPath))
                                .put("servicePackagePath", getPackageName(serviceDirRelativeModelPath))
                                .put("serviceConverterPackagePath", getPackageName(serviceConverterDirRelativeModelPath))
                                .put("starterPackagePath", getPackageName(starterDirRelativeModelPath))
                                .put("requestPackagePath", getPackageName(requestDirRelativeModelPath))
                                .build())
                        //自定义模板 key:生成文件绝对路径 value:模板名称
                        .customFile(Lists.newArrayList(new CustomFile.Builder()
                                .filePath(infraModelRootPath + sp + doDirRelativeModelPath + sp + fileBaseName + "DO.java")
                                .templatePath("/templates" + "/do.java.ftl")
                                .build(), new CustomFile.Builder()
                                .filePath(domainModelRootPath + sp + domainEntityDirRelativeModelPath + sp + fileBaseName + ".java")
                                .templatePath("/templates" + "/domainEntity.java.ftl")
                                .build())
                        ))
                .templateConfig(builder -> builder.disable()
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine() {
                    //重写该方法，以自定义输出路径
                    @Override
                    protected void outputCustomFile(List<CustomFile> customFileList, TableInfo tableInfo, Map<String, Object> objectMap) {
                        customFileList.forEach(customFile -> outputFile(new File(customFile.getFilePath()), objectMap, customFile.getTemplatePath(), true));
                    }
                })
                .execute();
    }
}
