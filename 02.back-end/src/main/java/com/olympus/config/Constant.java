package com.olympus.config;

public class Constant {

    /**
     * Serve side jwt secret key
     */
    public static final String JWT_SECRET = "olympus";

    public static final String MailSenderAddress = "noreply.socialnetwork.89@gmail.com";
    public static final String MailSenderPassword = "ewsj augt exxh vkgx ";

    /**
     * Expiration time : 3 days
     */
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 3;
    public static final String[] WHITE_LIST = {"/test", "/login", "/", "/index", "/home/", "/api-docs",
            "/**/swagger-ui/**", "/users/register", "/users/login", "/auth", "/v3/api-docs",
            "/swagger-ui","users/forgot-password","/users/reset-password"};


}
