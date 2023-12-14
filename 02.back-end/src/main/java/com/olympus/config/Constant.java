package com.olympus.config;

public class Constant {

    /**
     * Serve side jwt secret key
     */
    public static final String JWT_SECRET = "olympus";

    public static final String MailSenderAddress = "noreply.socialnetwork.89@gmail.com";
    public static final String MailSenderPassword = "ewsj augt exxh vkgx ";
    public static final String HTTP_STATUS_CODE_400 = "400";
    public static final String HTTP_STATUS_CODE_403 = "403";
    public static final String HTTP_STATUS_CODE_404 = "404";

    /**
     * Expiration time : 3 days
     */
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 3;
    public static final String[] WHITE_LIST = {"/test", "/login", "/", "/index", "/home/", "/api-docs",
            "/**/swagger-ui/**", "/v3/api-docs", "/swagger-ui",
            "/v1/users/forgot-password", "/v1/users/reset-password", "/v1/users/login",
            "/v1/auth", "/v1/users/register", "/v1/users/validate-reset-password", "/v1/users/example/{id}"};


}
