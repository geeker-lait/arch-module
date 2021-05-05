package org.arch.framework.automate.xmind.nodespace;

/**
 * spring/swagger/test/mybatis/lombok/jackson2
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:46
 */
public enum Annotation {

    // spring bean
    AUTOWIRED("Autowired", "org.springframework.beans.factory.annotation.Autowired"),
    CONFIGURABLE("Configurable", "org.springframework.beans.factory.annotation.Configurable"),
    LOOKUP("Lookup", "org.springframework.beans.factory.annotation.Lookup"),
    QUALIFIER("Qualifier", "org.springframework.beans.factory.annotation.Qualifier"),
    VALUE("Value", "org.springframework.beans.factory.annotation.Value"),

    // spring core
    ALIAS_FOR("AliasFor", "org.springframework.core.annotation.AliasFor"),
    ORDER("Order", "org.springframework.core.annotation.Order"),
    ASPECT("Aspect", "org.aspectj.lang.annotation.Aspect"),
    ADVICE_NAME("AdviceName", "org.aspectj.lang.annotation.AdviceName"),
    AFTER("After", "org.aspectj.lang.annotation.After"),
    AFTER_RETURNING("AfterReturning", "org.aspectj.lang.annotation.AfterReturning"),
    AFTER_THROWING("AfterThrowing", "org.aspectj.lang.annotation.AfterThrowing"),
    AROUND("Around", "org.aspectj.lang.annotation.Around"),
    BEFORE("Before", "org.aspectj.lang.annotation.Before"),
    DECLARE_ANNOTATION("DeclareAnnotation", "org.aspectj.lang.annotation.DeclareAnnotation"),
    DECLARE_ERROR("DeclareError", "org.aspectj.lang.annotation.DeclareError"),
    DECLARE_MIXIN("DeclareMixin", "org.aspectj.lang.annotation.DeclareMixin"),
    DECLARE_PARENTS("DeclareParents", "org.aspectj.lang.annotation.DeclareParents"),
    DECLARE_PRECEDENCE("DeclarePrecedence", "org.aspectj.lang.annotation.DeclarePrecedence"),
    DECLARE_WARNING("DeclareWarning", "org.aspectj.lang.annotation.DeclareWarning"),
    POINTCUT("Pointcut", "org.aspectj.lang.annotation.Pointcut"),
    REQUIRED_TYPES("RequiredTypes", "org.aspectj.lang.annotation.RequiredTypes"),
    SUPPRESS_AJ_WARNINGS("SuppressAjWarnings", "org.aspectj.lang.annotation.SuppressAjWarnings"),

    // spring context
    CONFIGURATION("Configuration", "org.springframework.context.annotation.Configuration"),
    BEAN("Bean", "org.springframework.context.annotation.Bean"),
    COMPONENT_SCAN("ComponentScan", "org.springframework.context.annotation.ComponentScan"),
    COMPONENT_SCANS("ComponentScans", "org.springframework.context.annotation.ComponentScans"),
    CONDITIONAL("Conditional", "org.springframework.context.annotation.Conditional"),
    DEPENDS_ON("DependsOn", "org.springframework.context.annotation.DependsOn"),
    DESCRIPTION("Description", "org.springframework.context.annotation.Description"),
    ENABLE_ASPECT_JAUTO_PROXY("EnableAspectJAutoProxy", "org.springframework.context.annotation.EnableAspectJAutoProxy"),
    ENABLE_LOAD_TIME_WEAVING("EnableLoadTimeWeaving", "org.springframework.context.annotation.EnableLoadTimeWeaving"),
    ENABLE_MBEAN_EXPORT("EnableMBeanExport", "org.springframework.context.annotation.EnableMBeanExport"),
    IMPORT("Import", "org.springframework.context.annotation.Import"),
    IMPORT_RESOURCE("ImportResource", "org.springframework.context.annotation.ImportResource"),
    LAZY("Lazy", "org.springframework.context.annotation.Lazy"),
    PRIMARY("Primary", "org.springframework.context.annotation.Primary"),
    PROFILE("Profile", "org.springframework.context.annotation.Profile"),
    PROPERTY_SOURCE("PropertySource", "org.springframework.context.annotation.PropertySource"),
    PROPERTY_SOURCES("PropertySources", "org.springframework.context.annotation.PropertySources"),
    ROLE("Role", "org.springframework.context.annotation.Role"),
    SCOPE("Scope", "org.springframework.context.annotation.Scope"),
    ASYNC("Async", "org.springframework.scheduling.annotation.Async"),
    ENABLE_ASYNC("EnableAsync", "org.springframework.scheduling.annotation.EnableAsync"),
    ENABLE_SCHEDULING("EnableScheduling", "org.springframework.scheduling.annotation.EnableScheduling"),
    SCHEDULED("Scheduled", "org.springframework.scheduling.annotation.Scheduled"),
    SCHEDULES("Schedules", "org.springframework.scheduling.annotation.Schedules"),
    CACHE_CONFIG("CacheConfig", "org.springframework.cache.annotation.CacheConfig"),
    CACHEABLE("Cacheable", "org.springframework.cache.annotation.Cacheable"),
    CACHE_EVICT("CacheEvict", "org.springframework.cache.annotation.CacheEvict"),
    CACHE_PUT("CachePut", "org.springframework.cache.annotation.CachePut"),
    CACHING("Caching", "org.springframework.cache.annotation.Caching"),
    ENABLE_CACHING("EnableCaching", "org.springframework.cache.annotation.EnableCaching"),
    EVENT_LISTENER("EventListener", "org.springframework.context.event.EventListener"),

    // spring autoconfiguration
    AUTO_CONFIGURATION_PACKAGE("AutoConfigurationPackage", "org.springframework.boot.autoconfigure.AutoConfigurationPackage"),
    AUTO_CONFIGURE_AFTER("AutoConfigureAfter", "org.springframework.boot.autoconfigure.AutoConfigureAfter"),
    AUTO_CONFIGURE_BEFORE("AutoConfigureBefore", "org.springframework.boot.autoconfigure.AutoConfigureBefore"),
    AUTO_CONFIGURE_ORDER("AutoConfigureOrder", "org.springframework.boot.autoconfigure.AutoConfigureOrder"),
    ENABLE_AUTO_CONFIGURATION("EnableAutoConfiguration", "org.springframework.boot.autoconfigure.EnableAutoConfiguration"),
    IMPORT_AUTO_CONFIGURATION("ImportAutoConfiguration", "org.springframework.boot.autoconfigure.ImportAutoConfiguration"),
    SPRING_BOOT_APPLICATION("SpringBootApplication", "org.springframework.boot.autoconfigure.SpringBootApplication"),
    ENABLE_CONFIGURATION_PROPERTIES("EnableConfigurationProperties", "org.springframework.boot.context.properties.EnableConfigurationProperties"),
    NESTED_CONFIGURATION_PROPERTY("NestedConfigurationProperty", "org.springframework.boot.context.properties.NestedConfigurationProperty"),
    CONSTRUCTOR_BINDING("ConstructorBinding", "org.springframework.boot.context.properties.ConstructorBinding"),
    CONFIGURATION_PROPERTIES_SCAN("ConfigurationPropertiesScan", "org.springframework.boot.context.properties.ConfigurationPropertiesScan"),
    CONFIGURATION_PROPERTIES_BINDING("ConfigurationPropertiesBinding", "org.springframework.boot.context.properties.ConfigurationPropertiesBinding"),
    CONFIGURATION_PROPERTIES("ConfigurationProperties", "org.springframework.boot.context.properties.ConfigurationProperties"),

    // spring security
    ENABLE_WEB_SECURITY("EnableWebSecurity", "org.springframework.security.config.annotation.web.configuration.EnableWebSecurity"),
    ENABLE_GLOBAL_AUTHENTICATION("EnableGlobalAuthentication", "org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication"),
    ENABLE_GLOBAL_METHOD_SECURITY("EnableGlobalMethodSecurity", "org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity"),
    ENABLE_REACTIVE_METHOD_SECURITY("EnableReactiveMethodSecurity", "org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity"),

    // spring boot test
    SPRING_BOOT_TEST("SpringBootTest", "org.springframework.boot.test.context.SpringBootTest"),
    TEST_COMPONENT("TestComponent", "org.springframework.boot.test.context.TestComponent"),
    TEST_CONFIGURATION("TestConfiguration", "org.springframework.boot.test.context.TestConfiguration"),

    // spring MVC
    CONTROLLER("Controller", "org.springframework.stereotype.Controller"),
    COMPONENT("Component", "org.springframework.stereotype.Component"),
    INDEXED("Indexed", "org.springframework.stereotype.Indexed"),
    REPOSITORY("Repository", "org.springframework.stereotype.Repository"),
    SERVICE("Service", "org.springframework.stereotype.GetMapping.Service"),
    GET_MAPPING("GetMapping", "org.springframework.web.bind.annotation.GetMapping"),
    POST_MAPPING("PostMapping", "org.springframework.web.bind.annotation.PostMapping"),
    PUT_MAPPING("PutMapping", "org.springframework.web.bind.annotation.PutMapping"),
    DELETE_MAPPING("DeleteMapping", "org.springframework.web.bind.annotation.DeleteMapping"),
    REQUEST_MAPPING("RequestMapping", "org.springframework.web.bind.annotation.RequestMapping"),
    MATRIX_VARIABLE("MatrixVariable", "org.springframework.web.bind.annotation.MatrixVariable"),
    CONTROLLER_ADVICE("ControllerAdvice", "org.springframework.web.bind.annotation.ControllerAdvice"),
    EXCEPTION_HANDLER("ExceptionHandler", "org.springframework.web.bind.annotation.ExceptionHandler"),
    REQUEST_BODY("RequestBody", "org.springframework.web.bind.annotation.RequestBody"),
    RESPONSE_BODY("ResponseBody", "org.springframework.web.bind.annotation.ResponseBody"),
    PATH_VARIABLE("PathVariable", "org.springframework.web.bind.annotation.PathVariable"),
    REQUIRED("Required", "org.springframework.web.bind.annotation.Required"),
    INIT_BINDER("InitBinder", "org.springframework.web.bind.annotation.InitBinder"),
    MODEL_ATTRIBUTE("ModelAttribute", "org.springframework.web.bind.annotation.ModelAttribute"),
    PATCH_MAPPING("PatchMapping", "org.springframework.web.bind.annotation.PatchMapping"),
    REQUEST_HEADER("RequestHeader", "org.springframework.web.bind.annotation.RequestHeader"),
    REQUEST_ATTRIBUTE("RequestAttribute", "org.springframework.web.bind.annotation.RequestAttribute"),
    REQUEST_PARAM("RequestParam", "org.springframework.web.bind.annotation.RequestParam"),
    REQUEST_PART("RequestPart", "org.springframework.web.bind.annotation.RequestPart"),
    RESPONSE_STATUS("ResponseStatus", "org.springframework.web.bind.annotation.ResponseStatus"),
    REST_CONTROLLER("RestController", "org.springframework.web.bind.annotation.RestController"),
    REST_CONTROLLER_ADVICE("RestControllerAdvice", "org.springframework.web.bind.annotation.RestControllerAdvice"),
    SESSION_ATTRIBUTE("SessionAttribute", "org.springframework.web.bind.annotation.SessionAttribute"),
    SESSION_ATTRIBUTES("SessionAttributes", "org.springframework.web.bind.annotation.SessionAttributes"),
    COOKIE_VALUE("CookieValue", "org.springframework.web.bind.annotation.CookieValue"),
    CROSS_ORIGIN("CrossOrigin", "org.springframework.web.bind.annotation.CrossOrigin"),
    CONDITIONAL_ON_MISSING_BEAN("ConditionalOnMissingBean", "org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean"),
    CONDITIONAL_ON_CLASS("ConditionalOnClass", "org.springframework.boot.autoconfigure.condition.ConditionalOnClass"),
    CONDITIONAL_ON_BEAN("ConditionalOnBean", "org.springframework.boot.autoconfigure.condition.ConditionalOnBean"),
    CONDITIONAL_ON_MISSING_CLASS("ConditionalOnMissingClass", "org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass"),
    CONDITIONAL_ON_PROPERTY("ConditionalOnProperty", "org.springframework.boot.autoconfigure.condition.ConditionalOnProperty"),
    CONDITIONAL_ON_RESOURCE("ConditionalOnResource", "org.springframework.boot.autoconfigure.condition.ConditionalOnResource"),
    CONDITIONAL_ON_WEB_APPLICATION("ConditionalOnWebApplication", "org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication"),

    // mybatis
    TABLE_ID("TableId", "com.baomidou.mybatisplus.annotation.TableId"),
    TABLEID("TableId", "com.baomidou.mybatisplus.annotation.TableId"),
    TABLE_FIELD("TableField", "com.baomidou.mybatisplus.annotation.TableField"),
    TABLE_NAME("TableName", "com.baomidou.mybatisplus.annotation.TableName"),
    TABLE_LOGIC("TableLogic", "com.baomidou.mybatisplus.annotation.TableLogic"),
    VERSION("Version", "com.baomidou.mybatisplus.annotation.Version"),

    // JACKSON2,
    JACKSON_STD_IMPL("JacksonStdImpl", "com.fasterxml.jackson.databind.annotation.JacksonStdImpl"),
    JSON_APPEND("JsonAppend", "com.fasterxml.jackson.databind.annotation.JsonAppend"),
    JSON_DESERIALIZE("JsonDeserialize", "com.fasterxml.jackson.databind.annotation.JsonDeserialize"),
    JSON_NAMING("JsonNaming", "com.fasterxml.jackson.databind.annotation.JsonNaming"),
    JSON_POJOBUILDER("JsonPOJOBuilder", "com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder"),
    JSON_SERIALIZE("JsonSerialize", "com.fasterxml.jackson.databind.annotation.JsonSerialize"),
    JSON_TYPE_ID_RESOLVER("JsonTypeIdResolver", "com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver"),
    JSON_TYPE_RESOLVER("JsonTypeResolver", "com.fasterxml.jackson.databind.annotation.JsonTypeResolver"),
    JSON_VALUE_INSTANTIATOR("JsonValueInstantiator", "com.fasterxml.jackson.databind.annotation.JsonValueInstantiator"),
    JACKSON_ANNOTATION("JacksonAnnotation", "com.fasterxml.jackson.annotation.JacksonAnnotation"),
    JACKSON_ANNOTATIONS_INSIDE("JacksonAnnotationsInside", "com.fasterxml.jackson.databind.annotation.JacksonAnnotationsInside"),
    JACKSON_ANNOTATION_VALUE("JacksonAnnotationValue", "com.fasterxml.jackson.databind.annotation.JacksonAnnotationValue"),
    JACKSON_INJECT("JacksonInject", "com.fasterxml.jackson.databind.annotation.JacksonInject"),
    JSON_ALIAS("JsonAlias", "com.fasterxml.jackson.databind.annotation.JsonAlias"),
    JSON_ANY_GETTER("JsonAnyGetter", "com.fasterxml.jackson.databind.annotation.JsonAnyGetter"),
    JSON_ANY_SETTER("JsonAnySetter", "com.fasterxml.jackson.databind.annotation.JsonAnySetter"),
    JSON_AUTO_DETECT("JsonAutoDetect", "com.fasterxml.jackson.databind.annotation.JsonAutoDetect"),
    JSON_BACK_REFERENCE("JsonBackReference", "com.fasterxml.jackson.databind.annotation.JsonBackReference"),
    JSON_CLASS_DESCRIPTION("JsonClassDescription", "com.fasterxml.jackson.databind.annotation.JsonClassDescription"),
    JSON_CREATOR("JsonCreator", "com.fasterxml.jackson.databind.annotation.JsonCreator"),
    JSON_ENUM_DEFAULT_VALUE("JsonEnumDefaultValue", "com.fasterxml.jackson.databind.annotation.JsonEnumDefaultValue"),
    JSON_FILTER("JsonFilter", "com.fasterxml.jackson.databind.annotation.JsonFilter"),
    JSON_FORMAT("JsonFormat", "com.fasterxml.jackson.databind.annotation.JsonFormat"),
    JSON_GETTER("JsonGetter", "com.fasterxml.jackson.databind.annotation.JsonGetter"),
    JSON_IDENTITY_INFO("JsonIdentityInfo", "com.fasterxml.jackson.databind.annotation.JsonIdentityInfo"),
    JSON_IDENTITY_REFERENCE("JsonIdentityReference", "com.fasterxml.jackson.databind.annotation.JsonIdentityReference"),
    JSON_IGNORE("JsonIgnore", "com.fasterxml.jackson.databind.annotation.JsonIgnore"),
    JSON_IGNORE_PROPERTIES("JsonIgnoreProperties", "com.fasterxml.jackson.databind.annotation.JsonIgnoreProperties"),
    JSON_IGNORE_TYPE("JsonIgnoreType", "com.fasterxml.jackson.databind.annotation.JsonIgnoreType"),
    JSON_INCLUDE("JsonInclude", "com.fasterxml.jackson.databind.annotation.JsonInclude"),
    JSON_MANAGED_REFERENCE("JsonManagedReference", "com.fasterxml.jackson.databind.annotation.JsonManagedReference"),
    JSON_MERGE("JsonMerge", "com.fasterxml.jackson.databind.annotation.JsonMerge"),
    JSON_PROPERTY("JsonProperty", "com.fasterxml.jackson.databind.annotation.JsonProperty"),
    JSON_PROPERTY_DESCRIPTION("JsonPropertyDescription", "com.fasterxml.jackson.databind.annotation.JsonPropertyDescription"),
    JSON_PROPERTY_ORDER("JsonPropertyOrder", "com.fasterxml.jackson.databind.annotation.JsonPropertyOrder"),
    JSON_RAW_VALUE("JsonRawValue", "com.fasterxml.jackson.databind.annotation.JsonRawValue"),
    JSON_ROOT_NAME("JsonRootName", "com.fasterxml.jackson.databind.annotation.JsonRootName"),
    JSON_SETTER("JsonSetter", "com.fasterxml.jackson.databind.annotation.JsonSetter"),
    JSON_SUB_TYPES("JsonSubTypes", "com.fasterxml.jackson.databind.annotation.JsonSubTypes"),
    JSON_TYPE_ID("JsonTypeId", "com.fasterxml.jackson.databind.annotation.JsonTypeId"),
    JSON_TYPE_INFO("JsonTypeInfo", "com.fasterxml.jackson.databind.annotation.JsonTypeInfo"),
    JSON_TYPE_NAME("JsonTypeName", "com.fasterxml.jackson.databind.annotation.JsonTypeName"),
    JSON_UNWRAPPED("JsonUnwrapped", "com.fasterxml.jackson.databind.annotation.JsonUnwrapped"),
    JSON_VALUE("JsonValue", "com.fasterxml.jackson.databind.annotation.JsonValue"),
    JSON_VIEW("JsonView", "com.fasterxml.jackson.databind.annotation.JsonView"),

    // validation
    VALID("Valid", "javax.validation.VALID"),
    VALIDATED("Validated", "org.springframework.validation.annotation.Validated"),
    NOTNULL("NotNull", "javax.validation.constraints.NotNull"),
    NOT_NULL("NotNull", "javax.validation.constraints.NotNull"),
    EMAIL("Email", "javax.validation.constraints.Email"),
    NOT_BLANK("NotBlank", "javax.validation.constraints.NotBlank"),
    NOTBLANK("NotBlank", "javax.validation.constraints.NotBlank"),
    NOT_EMPTY("NotEmpty", "javax.validation.constraints.NotEmpty"),
    NOTEMPTY("NotEmpty", "javax.validation.constraints.NotEmpty"),
    DIGITS("Digits", "javax.validation.constraints.Digits"),
    MAX("Max", "javax.validation.constraints.Max"),
    MIN("Min", "javax.validation.constraints.Min"),
    NULL("Null", "javax.validation.constraints.Null"),
    PATTERN("Pattern", "javax.validation.constraints.Pattern"),
    SIZE("Size", "javax.validation.constraints.Size"),
    DECIMAL_MAX("DecimalMax", "javax.validation.constraints.DecimalMax"),
    DECIMAL_MIN("DecimalMin", "javax.validation.constraints.DecimalMin"),
    POSITIVE("Positive", "javax.validation.constraints.Positive"),
    POSITIVE_OR_ZERO("PositiveOrZero", "javax.validation.constraints.PositiveOrZero"),
    PAST("Past", "javax.validation.constraints.Past"),
    PAST_OR_PRESENT("PastOrPresent", "javax.validation.constraints.PastOrPresent"),
    NEGATIVE("Negative", "javax.validation.constraints.Negative"),
    NEGATIVE_OR_ZERO("NegativeOrZero", "javax.validation.constraints.NegativeOrZero"),
    FUTURE("Future", "javax.validation.constraints.Future"),
    FUTURE_OR_PRESENT("FutureOrPresent", "javax.validation.constraints.FutureOrPresent"),

    // lombok
    JACKSONIZED("Jacksonized", "lombok.extern.jackson.Jacksonized"),
    BUILDER("Builder", "lombok.Builder"),
    CLEANUP("Cleanup", "lombok.Cleanup"),
    DATA("Data", "lombok.Data"),
    EQUALS_AND_HASH_CODE("EqualsAndHashCode", "lombok.EqualsAndHashCode"),
    GENERATED("Generated", "lombok.Generated"),
    GETTER("Getter", "lombok.Getter"),
    NO_ARGS_CONSTRUCTOR("NoArgsConstructor", "lombok.NoArgsConstructor"),
    REQUIRED_ARGS_CONSTRUCTOR("RequiredArgsConstructor", "lombok.RequiredArgsConstructor"),
    SETTER("Setter", "lombok.Setter"),
    SLF4J("Slf4j", "lombok.extern.slf4j.Slf4j"),
    SNEAKY_THROWS("SneakyThrows", "lombok.SneakyThrows"),
    SYNCHRONIZED("Synchronized", "lombok.Synchronized"),
    TO_STRING("ToString", "lombok.ToString"),
    TOSTRING("ToString", "lombok.ToString"),
    ACCESSORS("Accessors", "lombok.experimental.Accessors"),
    DELEGATE("Delegate", "lombok.experimental.Delegate"),
    EXTENSION_METHOD("ExtensionMethod", "lombok.experimental.ExtensionMethod"),
    FIELD_DEFAULTS("FieldDefaults", "lombok.experimental.FieldDefaults"),
    FIELD_NAME_CONSTANTS("FieldNameConstants", "lombok.experimental.FieldNameConstants"),
    HELPER("Helper", "lombok.experimental.Helper"),
    NON_FINAL("NonFinal", "lombok.experimental.NonFinal"),
    PACKAGE_PRIVATE("PackagePrivate", "lombok.experimental.PackagePrivate"),
    SUPER_BUILDER("SuperBuilder", "lombok.experimental.SuperBuilder"),
    TOLERATE("Tolerate", "lombok.experimental.Tolerate"),
    UTILITY_CLASS("UtilityClass", "lombok.experimental.UtilityClass"),
    WITH_BY("WithBy", "lombok.experimental.WithBy"),
    SINGULAR("Singular", "lombok.Singular"),
    LOMBOK_VALUE("Value", "lombok.Value"),
    WITH("With", "lombok.With"),
    X_SLF4J("XSlf4j", "jlombok.extern.slf4j.XSlf4j"),
    COMMONS_LOG("CommonsLog", "lombok.extern.apachecommons.CommonsLog"),
    FLOGGER("Flogger", "lombok.extern.flogger.Flogger"),
    LOG("Log", "lombok.extern.java.Log"),
    JBOSS_LOG("JBossLog", "lombok.extern.jbosslog.JBossLog"),
    LOG4J("Log4j", "lombok.extern.log4j.Log4j"),
    LOG4J2("Log4j2", "lombok.extern.log4j.Log4j2"),

    // swagger3
    API("Api", "io.swagger.annotations.Api"),
    API_MODEL("ApiModel", "io.swagger.annotations.ApiModel"),
    API_MODEL_PROPERTY("ApiModelProperty", "io.swagger.annotations.ApiModelProperty"),
    API_OPERATION("ApiOperation", "io.swagger.annotations.ApiOperation"),
    API_PARAM("ApiParam", "io.swagger.annotations.ApiParam"),
    RESPONSE_HEADER("ResponseHeader", "io.swagger.annotations.ResponseHeader"),
    API_IMPLICIT_PARAMS("ApiImplicitParams", "io.swagger.annotations.ApiImplicitParams"),
    API_RESPONSE("ApiResponse", "io.swagger.v3.oas.annotations.responses.ApiResponse"),
    API_RESPONSES("ApiResponses", "io.swagger.v3.oas.annotations.responses.ApiResponses"),
    API_RESPONSE_2("ApiResponse", "io.swagger.annotations.ApiResponse"),
    API_RESPONSES_2("ApiResponses", "io.swagger.annotations.ApiResponses"),
    ENABLE_OPEN_API("EnableOpenApi", "springfox.documentation.oas.annotations.EnableOpenApi"),
    REQUEST_BODY_SWAGGER("RequestBody", "io.swagger.v3.oas.annotations.parameters.RequestBody"),
    ENABLE_SWAGGER_2("EnableSwagger2", "springfox.documentation.swagger2.annotations.EnableSwagger2"),
    API_IGNORE("ApiIgnore", "springfox.documentation.annotations.ApiIgnore"),
    SCHEMA("Schema", "io.swagger.v3.oas.annotations.media.Schema"),
    CONTENT("Content", "io.swagger.v3.oas.annotations.media.Content"),
    EXAMPLE_OBJECT("ExampleObject", "io.swagger.v3.oas.annotations.media.ExampleObject"),
    HEADER("Header", "io.swagger.v3.oas.annotations.headers.Header"),
    PARAMETERS("Parameters", "io.swagger.v3.oas.annotations.Parameters"),
    PARAMETER("Parameter", "io.swagger.v3.oas.annotations.Parameter"),
    OPERATION("Operation", "io.swagger.v3.oas.annotations.Operation"),
    HIDDEN("Hidden", "io.swagger.v3.oas.annotations.Hidden"),
    TAG("Tag", "io.swagger.v3.oas.annotations.tags.Tag"),
    TAGS("Tags", "io.swagger.v3.oas.annotations.tags.Tags"),
    EXTERNAL_DOCUMENTATION("ExternalDocumentation", "io.swagger.v3.oas.annotations.ExternalDocumentation"),
    OPEN_APIDEFINITION("OpenAPIDefinition", "io.swagger.v3.oas.annotations.OpenAPIDefinition"),
    CALLBACK("Callback", "io.swagger.v3.oas.annotations.callbacks.Callback"),
    CALLBACKS("Callbacks", "io.swagger.v3.oas.annotations.callbacks.Callbacks"),


    // test
    TEST("Test", "org.junit.Test"),
    AFTER_JUNIT("After", "org.junit.After"),
    BEFORE_JUNIT("Before", "org.junit.Before"),
    RUN_WITH("RunWith", "org.junit.runner.RunWith"),
    ORDER_WITH("OrderWith", "org.junit.runner.OrderWith"),
    AFTER_CLASS("AfterClass", "org.junit.AfterClass"),
    BEFORE_CLASS("BeforeClass", "org.junit.BeforeClass"),
    CLASS_RULE("ClassRule", "org.junit.ClassRule"),
    FIX_METHOD_ORDER("FixMethodOrder", "org.junit.FixMethodOrder"),
    IGNORE("Ignore", "org.junit.Ignore"),
    RULE("Rule", "org.junit.Rule");

    private final String annotName;
    private final String pkg;

    Annotation(String annotName, String pkg) {
        this.annotName = annotName;
        this.pkg = pkg;
    }

    public String getAnnotName() {
        return annotName;
    }

    public String getPkg() {
        return pkg;
    }
    }
