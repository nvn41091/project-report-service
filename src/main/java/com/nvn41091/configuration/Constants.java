package com.nvn41091.configuration;

public class Constants {

    public static final char DEFAULT_ESCAPE_CHAR = '&';
    public static final Integer TIME_TYPE_DATE = 1;
    public static final Integer TIME_TYPE_MONTH = 2;
    public static final Integer TIME_TYPE_QUARTER = 3;
    public static final Integer TIME_TYPE_YEAR = 4;

    public static final String RESULT_VALIDATE_MSG = "RESULT_VALIDATE_MSG";

    public static final Long CONST_ROLE_ID_FOR_USER = 3L;
    public static final Long CONST_ROLE_ID_FOR_ADMIN = 2L;
    public static final Long CONST_USER_ADMIN_ID = 1L;
    public static final Long CONST_COMPANY_ID_ADMIN = 17L;

    static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/error"
    };
}
