package com.olympus.config;

public class Constant {

    /**
     * Serve side jwt secret key
     */
    public static final String JWT_SECRET = "olympus";

    public static final String MailSenderAddress = "noreply.socialnetwork.89@gmail.com";
    public static final String MailSenderPassword = "ewsj augt exxh vkgx ";
    public static final String HTTP_STATUS_CODE_400 = "400";
    public static final String HTTP_STATUS_CODE_200 = "200";
    public static final String HTTP_STATUS_CODE_201 = "201";
    public static final String HTTP_STATUS_CODE_403 = "403";
    public static final String HTTP_STATUS_CODE_404 = "404";

    /**
     * Expiration time : 3 days
     */
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24 * 3;
    public static final String[] WHITE_LIST = {"/test", "/login", "/", "/index", "/home/", "/api-docs",
            "/**/swagger-ui/**", "/v3/api-docs", "/swagger-ui",
            "/v1/account/forgot-password", "/v1/account/reset-password", "/v1/account/login",
            "/v1/auth", "/v1/account/register", "/v1/account/validate-reset-password"};

    public static final String MSG_SUCCESS_ACCOUNT_REGISTER = "Register new user successfully";
    public static final String MSG_SUCCESS_ACCOUNT_OTP_SENT = "OTP sent successfully";
    public static final String MSG_SUCCESS_ACCOUNT_PWD_RESET_TOKEN_SENT = "Token sent successfully";
    public static final String MSG_SUCCESS_ACCOUNT_PWD_RESET_TOKEN_VALIDATE = "Token is valid";
    public static final String MSG_SUCCESS_ACCOUNT_PWD_RESET = "Reset password successfully";
    public static final String MSG_SUCCESS_POST_COMMENT_CREATE = "Create new comment successfully";
    public static final String MSG_SUCCESS_POST_COMMENT_UPDATE = "Update comment successfully";
    public static final String MSG_SUCCESS_POST_COMMENT_DELETE = "Delete comment successfully";
    public static final String MSG_SUCCESS_FRIENDSHIP_DELETE = "You two are no longer friends";
    public static final String MSG_SUCCESS_POST_CREATE = "Create new post successfully";
    public static final String MSG_SUCCESS_POST_UPDATE = "Update post successfully";
    public static final String MSG_SUCCESS_POST_DELETE = "Delete post successfully";
    public static final String MSG_SUCCESS_USER_UPDATE = "Update user successfully";
    public static final String ERR_FRIEND_REQUEST_REQUEST_EXIST = "Friend request is already sent";
    public static final String ERR_FRIEND_REQUEST_FRIENDSHIP_EXIST = "You two are already friends";
    public static final String ERR_FRIEND_REQUEST_FRIENDSHIP_NOT_EXIST = "You two are not friends";
    public static final String ERR_FRIEND_REQUEST_REQUEST_DUPLICATE_SENDER_RECEIVER = "Sender and Receiver are the same user";
    public static final String ERR_FRIENDSHIP_DUPLICATE_SENDER_RECEIVER = "Sender and Receiver are the same user";
    public static final String ERR_FRIEND_REQUEST_REQUEST_NOT_VALID_CANCELER = "Not valid sender either receiver";
    public static final String ERR_FRIEND_REQUEST_REQUEST_NOT_VALID_ACCEPTER = "Not valid receiver";
    public static final String ERR_CONFLICT_PATH_VARIABLE_REQUEST_BODY ="Path variable and body ids conflict";

    public static final String MSG_OK = "ok";
    public static final String MSG_SUCCESS = "success";
    public static final String MSG_ERROR = "error";
}
