package org.arch.framework.automate.xmind.nodespace;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:46
 */
public enum Annotation {
    // TODO: 2021.4.29 添加 pkg
    GET_MAPPING("GetMapping", ""),
    POST_MAPPING("PostMapping", ""),
    PUT_MAPPING("PutMapping", ""),
    DELETE_MAPPING("DeleteMapping", ""),
    REQUEST_MAPPING("RequestMapping", ""),
    MATRIX_VARIABLE("MatrixVariable", ""),
    CONTROLLER_ADVICE("ControllerAdvice", ""),
    EXCEPTION_HANDLER("ExceptionHandler", ""),
    REQUEST_BODY("RequestBody", ""),
    RESPONSE_BODY("ResponseBody", ""),
    PATH_VARIABLE("PathVariable", ""),
    REQUIRED("Required", ""),
    INIT_BINDER("InitBinder", ""),
    MODEL_ATTRIBUTE("ModelAttribute", ""),
    PATCH_MAPPING("PatchMapping", ""),
    REQUEST_HEADER("RequestHeader", ""),
    REQUEST_ATTRIBUTE("RequestAttribute", ""),
    REQUEST_PARAM("RequestParam", ""),
    REQUEST_PART("RequestPart", ""),
    RESPONSE_STATUS("ResponseStatus", ""),
    REST_CONTROLLER("RestController", ""),
    REST_CONTROLLER_ADVICE("RestControllerAdvice", ""),
    SESSION_ATTRIBUTE("SessionAttribute", ""),
    SESSION_ATTRIBUTES("SessionAttributes", ""),
    COOKIE_VALUE("CookieValue", ""),
    CROSS_ORIGIN("CrossOrigin", ""),
    TABLE_ID("TableId", ""),
    TABLE_FIELD("TableField", ""),
    TABLE_NAME("TableName", ""),
    TABLE_LOGIC("TableLogic", ""),
    VERSION("Version", ""),
    VALID("Valid", ""),
    VALIDATED("Validated", ""),
    NOTNULL("NotNull", ""),
    EMAIL("Email", ""),
    NOT_BLANK("NotBlank", ""),
    NOT_EMPTY("NotEmpty", ""),
    DIGITS("Digits", ""),
    MAX("Max", ""),
    MIN("Min", ""),
    NULL("Null", ""),
    PATTERN("Pattern", ""),
    SIZE("Size", ""),
    DECIMAL_MAX("DecimalMax", ""),
    DECIMAL_MIN("DecimalMin", ""),
    POSITIVE("Positive", ""),
    POSITIVE_OR_ZERO("PositiveOrZero", ""),
    PAST("Past", ""),
    PAST_OR_PRESENT("PastOrPresent", ""),
    NEGATIVE("Negative", ""),
    NEGATIVE_OR_ZERO("NegativeOrZero", ""),
    FUTURE("Future", ""),
    FUTURE_OR_PRESENT("FutureOrPresent", "");

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
