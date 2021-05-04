package org.arch.framework.automate.xmind.nodespace;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:46
 */
@EnableAutoConfiguration
public enum Annotation {

    // spring bean
    AUTOWIRED("Autowired", "org.springframework.beans.factory.annotation.Autowired"),
    CONFIGURABLE("Configurable", "org.springframework.beans.factory.annotation."),
    LOOKUP("Lookup", "org.springframework.beans.factory.annotation."),
    QUALIFIER("Qualifier", "org.springframework.beans.factory.annotation."),
    VALUE("Value", "org.springframework.beans.factory.annotation."),

    // spring core
    ALIAS_FOR("AliasFor", "org.springframework.core.annotation."),
    ORDER("Order", "org.springframework.core.annotation."),

    // spring context
    CONFIGURATION("Configuration", "org.springframework.context.annotation."),
    BEAN("Bean", "org.springframework.context.annotation."),
    COMPONENT_SCAN("ComponentScan", "org.springframework.context.annotation."),
    COMPONENT_SCANS("ComponentScans", "org.springframework.context.annotation."),
    CONDITIONAL("Conditional", "org.springframework.context.annotation."),
    DEPENDS_ON("DependsOn", "org.springframework.context.annotation."),
    DESCRIPTION("Description", "org.springframework.context.annotation."),
    ENABLE_ASPECT_JAUTO_PROXY("EnableAspectJAutoProxy", "org.springframework.context.annotation."),
    ENABLE_LOAD_TIME_WEAVING("EnableLoadTimeWeaving", "org.springframework.context.annotation."),
    ENABLE_MBEAN_EXPORT("EnableMBeanExport", "org.springframework.context.annotation."),
    IMPORT("Import", "org.springframework.context.annotation."),
    IMPORT_RESOURCE("ImportResource", "org.springframework.context.annotation."),
    LAZY("Lazy", "org.springframework.context.annotation."),
    PRIMARY("Primary", "org.springframework.context.annotation."),
    PROFILE("Profile", "org.springframework.context.annotation."),
    PROPERTY_SOURCE("PropertySource", "org.springframework.context.annotation."),
    PROPERTY_SOURCES("PropertySources", "org.springframework.context.annotation."),
    ROLE("Role", "org.springframework.context.annotation."),
    SCOPE("Scope", "org.springframework.context.annotation."),

    // spring autoconfiguration
    AUTO_CONFIGURATION_PACKAGE("AutoConfigurationPackage", "org.springframework.boot.autoconfigure."),
    AUTO_CONFIGURE_AFTER("AutoConfigureAfter", "org.springframework.boot.autoconfigure."),
    AUTO_CONFIGURE_BEFORE("AutoConfigureBefore", "org.springframework.boot.autoconfigure."),
    AUTO_CONFIGURE_ORDER("AutoConfigureOrder", "org.springframework.boot.autoconfigure."),
    ENABLE_AUTO_CONFIGURATION("EnableAutoConfiguration", "org.springframework.boot.autoconfigure."),
    IMPORT_AUTO_CONFIGURATION("ImportAutoConfiguration", "org.springframework.boot.autoconfigure."),
    SPRING_BOOT_APPLICATION("SpringBootApplication", "org.springframework.boot.autoconfigure."),

    // spring boot test
    SPRING_BOOT_TEST("SpringBootTest", "org.springframework.boot.test.context."),
    TEST_COMPONENT("TestComponent", "org.springframework.boot.test.context."),
    TEST_CONFIGURATION("TestConfiguration", "org.springframework.boot.test.context."),


//    ("", "org.springframework.boot.test.context."),

    // spring MVC
    CONTROLLER("Controller", "org.springframework.stereotype.GetMapping.Controller"),
    COMPONENT("Component", "org.springframework.stereotype.GetMapping.Component"),
    INDEXED("Indexed", "org.springframework.stereotype.GetMapping.Indexed"),
    REPOSITORY("Repository", "org.springframework.stereotype.GetMapping.Repository"),
    SERVICE("Service", "org.springframework.stereotype.GetMapping."),
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

    // mybatis
    TABLE_ID("TableId", "com.baomidou.mybatisplus.annotation.TableId"),
    TABLEID("TableId", "com.baomidou.mybatisplus.annotation.TableId"),
    TABLE_FIELD("TableField", "com.baomidou.mybatisplus.annotation.TableField"),
    TABLE_NAME("TableName", "com.baomidou.mybatisplus.annotation.TableName"),
    TABLE_LOGIC("TableLogic", "com.baomidou.mybatisplus.annotation.TableLogic"),
    VERSION("Version", "com.baomidou.mybatisplus.annotation.Version"),


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
    EQUALS_AND_HASH_CODE("EqualsAndHashCode", "lombok.EQUALS_AND_HASH_CODE"),
    GENERATED("Generated", "lombok.GENERATED"),
    GETTER("Getter", "lombok.GETTER"),
    NO_ARGS_CONSTRUCTOR("NoArgsConstructor", "lombok.NO_ARGS_CONSTRUCTOR"),
    REQUIRED_ARGS_CONSTRUCTOR("RequiredArgsConstructor", "lombok.REQUIRED_ARGS_CONSTRUCTOR"),
    SETTER("Setter", "lombok.SETTER"),
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
    LOG4J2("Log4j2", "lombok.extern.log4j.Log4j2");

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
