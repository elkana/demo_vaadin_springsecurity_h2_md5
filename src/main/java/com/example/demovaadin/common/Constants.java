package com.example.demovaadin.common;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String MODIFIER_SYSTEM = "SYSMATEL";

    public static final String ROLE_EXECUTOR = "EXE";
    // 7jan23 sementara disamakan dengan mobcol utk rulenya krn dianggap standardnya verena
    // jd jika ada perubahan akan impact global
    public static final String KEY_SETUP_PWD_MIN_LENGTH = "PWD_MIN_LENGTH";
    public static final String KEY_SETUP_PWD_HAS_SPECIAL_CHAR = "PWD_HAS_SPECIAL_CHAR";
    public static final String KEY_SETUP_PWD_HAS_NUMERIC = "PWD_HAS_NUMERIC";
    public static final String KEY_SETUP_PWD_HAS_CAPITAL = "PWD_HAS_CAPITAL";
    public static final String KEY_SETUP_PWD_PERIODIC_CHANGE_DAYS = "PWD_PERIODIC_CHANGE_DAYS";
    public static final String KEY_SETUP_MAX_FAILED_ATTEMPT = "MAX_FAILED_ATTEMPT";
    public static final String KEY_SETUP_MATEL_MOBILE_TITLE = "MATEL_MOBILE_TITLE";
    public static final String KEY_SETUP_MATEL_OTP_MINUTES = "MATEL_OTP_MINUTES";

    public static final String MSG_UNAUTHORIZED = "Mismatch UserId or Password";
    public static final String MSG_CHANGE_PWD = "Password change required";

    // TODO will be removed soon. see uri.nonjwt within yml
    public static List<String> excludedUrls = Arrays.asList("/auth/v1/login"
    , "/auth/v2/login"
    , "/auth/v1/signup_nik"
    // , "/auth/v1/reset_pwd"
    , "/auth/v1/change_pwd"
    , "/setup/v1/check_latest_version"
    , "/setup/v1/check_apk"
    , "/setup/v1/check_email"
    , "/h2-console/**"
    // , "/skt/v1/media/**"
    // , "/skt/v1/summary**"   // diopen selama development/uat saja
    // , "/skt/v1/assignment**"   // diopen selama development/uat saja
    // , "/skt/v1/assignments**"   // diopen selama development/uat saja
    // , "/search/v1/**"   // diopen selama development/uat saja
    // , "/master/v1/**"   // diopen selama development/uat saja
    , "/skt/v1/update_assignment"   // optional spy tdk mengganggu entri form
    , "/media/v1/upload_foto"       // optional spy tdk mengganggu entri form
    , "/media/v1/skt/**"
    , "/media/v1/avatar/**"
    , "/media/v1/skt_by/**"
    , "/demo/create_demo"
    , "/demo/hello1"
    , "/demo/hello2"
    );
}
