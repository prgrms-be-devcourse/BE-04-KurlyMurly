package com.devcourse.kurlymurly.web.common;

public class UserRegexp {
    private UserRegexp() {
    }

    public static final String PHONE_NUMBER_REGEXP = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
    public static final String EMAIL_REGEXP = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
    public static final String USER_NAME_REGEXP = "^[가-힣a-z]{2,20}";
    public static final String PASSWORD_REGEXP = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$";
}
