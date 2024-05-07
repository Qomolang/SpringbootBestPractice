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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.magnus.infrastructure.codegenerator.CommonOps.*;

/**
 * mybatis plus FastAutoGenerator
 *
 * @author L.cm, lanjerry
 * @since 2021-07-22
 */
public final class CreateModelCodeGeneratorWithVanillaMybatis {

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

    private CreateModelCodeGeneratorWithVanillaMybatis(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new GlobalConfig.Builder();
        this.packageConfigBuilder = new PackageConfig.Builder();
        this.strategyConfigBuilder = new StrategyConfig.Builder();
        this.injectionConfigBuilder = new InjectionConfig.Builder();
        this.templateConfigBuilder = new TemplateConfig.Builder();
    }

    public static CreateModelCodeGeneratorWithVanillaMybatis create(String url, String username, String password) {
        return new CreateModelCodeGeneratorWithVanillaMybatis(new DataSourceConfig.Builder(url, username, password));
    }

    /**
     * 全局配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public CreateModelCodeGeneratorWithVanillaMybatis globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        //配置项
        consumer.accept(this.globalConfigBuilder);

        //个人恒定偏好项
        globalConfigBuilder
                .author("gs")
                //默认生成完毕后会打开outputDir对应文件夹，关闭
                .disableOpenDir();

        return this;
    }

    /**
     * 包配置
     *
     * @param consumer 自定义包配置
     * @return
     */
    public CreateModelCodeGeneratorWithVanillaMybatis packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    /**
     * 策略配置
     *
     * @param consumer 自定义策略配置
     * @return
     */
    public CreateModelCodeGeneratorWithVanillaMybatis strategyConfig(Consumer<StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);

        strategyConfigBuilder.entityBuilder()
                //！！！不要开启enableRemoveIsPrefix 与目前预处理部分冲突
                //覆盖已生成文件
                //打开 entityLombokModel 标签
                .enableLombok()
                //打开 entityColumnConstant 标签
                //.enableColumnConstant()
                //打开 convert 标签
                .enableTableFieldAnnotation()
                .idType(IdType.AUTO)
                .controllerBuilder()
                //打开 restControllerStyle 标签
                .enableRestStyle()
                .mapperBuilder()
                //开启该标签后，在xml文件中生成字段映射
                .enableBaseResultMap()
                //开启该标签后，在xml文件中生成通用查询结果列
                .enableBaseColumnList();

        return this;
    }


    /**
     * 数据源配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public CreateModelCodeGeneratorWithVanillaMybatis dataSourceConfig(Consumer<DataSourceConfig.Builder> consumer) {
        consumer.accept(this.dataSourceConfigBuilder);
        return this;
    }


    /**
     * 注入配置
     */
    public CreateModelCodeGeneratorWithVanillaMybatis injectionConfig(Consumer<InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    /**
     * 模板配置
     */
    public CreateModelCodeGeneratorWithVanillaMybatis templateConfig(Consumer<TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);

        return this;
    }

    /**
     * 模板引擎配置
     */
    public CreateModelCodeGeneratorWithVanillaMybatis templateEngine(AbstractTemplateEngine templateEngine) {
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
        String dirName = scanner("目录名");
        String tablePrefix = scanner("去除表前缀（如忽略请直接回车）");

        //unix下如:/home/gs/github/mybatis-practice-project
        String projectPath = System.getProperty("user.dir");

        String infrastructureModuleName = projectName + "-infrastructure";
        String domainModuleName = projectName + "-domain";
        String serviceModuleName = projectName + "-service";
        String starterModuleName = projectName + "-starter";
        String apiModuleName = projectName + "-api";

        String infraModuleRootPath = projectPath + sp + infrastructureModuleName;
        String domainModuleRootPath = projectPath + sp + domainModuleName;
        String serviceModuleRootPath = projectPath + sp + serviceModuleName;
        String starterModuleRootPath = projectPath + sp + starterModuleName;
        String apiModuleRootPath = projectPath + sp + apiModuleName;

        // src/main/java/com/projectName/infrastructure
        // 各个模块的根目录，该目录即为该模块下第一层会展开目录的父目录
        String infraModuleRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "infrastructure";
        String domainModuleRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "domain";
        String serviceModuleRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "service";
        String starterModuleRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "controller";
        String apiModuleRelativePath = srcMainJavaPath + sp + basePackagePath + sp + "api";

        String tableTempName = tableName.replace(tablePrefix, "");
        String fileBaseName = CaseUtils.toCamelCase(tableTempName, true, '_');

        //生成文件的路径
        String doDirRelativeModulePath = infraModuleRelativePath + sp + "dao" + sp + dirName + sp + "model";
        String mapperDirRelativeModulePath = infraModuleRelativePath + sp + "dao" + sp + dirName + sp + "mapper";
        String mapperXmlDirRelativeModulePath = "src" + sp + "main" + sp + "resources" + sp + "mapper" + sp + dirName;
        String domainEntityDirRelativeModulePath = domainModuleRelativePath + sp + dirName + sp + "model";
        String repositoryDirRelativeModulePath = domainModuleRelativePath + sp + dirName + sp + "repository";
        String repositoryImplDirRelativeModulePath = domainModuleRelativePath + sp + dirName;
        String converterDirRelativeModulePath = domainModuleRelativePath + sp + dirName + sp + "converter";
        String serviceDirRelativeModulePath = serviceModuleRelativePath + sp + dirName;
        String serviceConverterDirRelativeModulePath = serviceModuleRelativePath + sp + dirName + sp + "converter";
        String starterDirRelativeModulePath = starterModuleRelativePath + sp + "api";
        String requestDirRelativeModulePath = apiModuleRelativePath + sp + "model" + sp + "request" + sp + dirName;

        //生成service层及controller层
        List<CustomFile> serviceAndControllerCustomFiles = CommonOps.buildServiceAndControllerCustomFile(serviceModuleRootPath,
                serviceDirRelativeModulePath,
                serviceConverterDirRelativeModulePath,
                starterModuleRootPath,
                starterDirRelativeModulePath,
                apiModuleRootPath,
                requestDirRelativeModulePath,
                fileBaseName);

        //生成dao层及domain层自定义文件
        List<CustomFile> customFiles = buildVanillaDaoAndDomainCustomFile(infraModuleRootPath,
                domainModuleRootPath,
                doDirRelativeModulePath,
                mapperXmlDirRelativeModulePath,
                mapperDirRelativeModulePath,
                domainEntityDirRelativeModulePath,
                repositoryImplDirRelativeModulePath,
                converterDirRelativeModulePath,
                repositoryDirRelativeModulePath,
                fileBaseName);

        //可生成optional的的文件
        customFiles.addAll(serviceAndControllerCustomFiles);

        CreateModelCodeGeneratorWithVanillaMybatis.create(GeneratorConfig.dBUrl, GeneratorConfig.dBUserName, GeneratorConfig.dBPassWord)
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
                        //使用localdatatime 如果想用date可以指定为ONLY_DATE
                        .dateType(DateType.TIME_PACK)
                )
                .strategyConfig(builder -> builder
                        //设置需要生成的表名
                        .addInclude(tableName)
                        .addTablePrefix(tablePrefix)
                        //打开模板中的 entityLombokModel 标签
                        .entityBuilder()
                        //给表字段添加填充
                        .addTableFills(new Property(doCreateTime, FieldFill.INSERT))
                        .addTableFills(new Property(doUpdateTime, FieldFill.INSERT_UPDATE))
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
                                .put("doPackagePath", getPackageName(doDirRelativeModulePath))
                                .put("mapperPackagePath", getPackageName(mapperDirRelativeModulePath))
                                .put("domainEntityPackagePath", getPackageName(domainEntityDirRelativeModulePath))
                                .put("repositoryPackagePath", getPackageName(repositoryDirRelativeModulePath))
                                .put("repositoryImplPackagePath", getPackageName(repositoryImplDirRelativeModulePath))
                                .put("converterPackagePath", getPackageName(converterDirRelativeModulePath))
                                .put("servicePackagePath", getPackageName(serviceDirRelativeModulePath))
                                .put("serviceConverterPackagePath", getPackageName(serviceConverterDirRelativeModulePath))
                                .put("starterPackagePath", getPackageName(starterDirRelativeModulePath))
                                .put("requestPackagePath", getPackageName(requestDirRelativeModulePath))
                                .build())
                        //自定义模板 key:生成文件绝对路径 value:模板名称
                        .customFile(customFiles)
                )
                .templateConfig(builder -> builder.disable())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine() {
                    //重写该方法，以自定义输出路径
                    @Override
                    protected void outputCustomFile(List<CustomFile> customFileList, TableInfo tableInfo, Map<String, Object> objectMap) {
                        customFileList.forEach(customFile -> outputFile(new File(customFile.getFilePath()), objectMap, customFile.getTemplatePath(), GeneratorConfig.fileOverride));
                    }
                })
                .execute();
    }
}
