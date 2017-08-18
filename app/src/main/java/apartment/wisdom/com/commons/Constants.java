package apartment.wisdom.com.commons;


public class Constants {

    public static final String PASS_STRING = "pass_string";
    public static final String PASS_OBJECT = "pass_object";
    public static final String PASS_NMAE = "pass_name";
    public static final boolean DEBUG = true;
    public static final String FRIST_OPEN_APP = "is_first_open_app";
    public static int PAGE_SIZE = 10;
    public static String JZ_PIC = "http://i.shangc.net/2017/0731/20170731120542973.jpg";
    public static String JZ_PIC_1 = "http://upload.techweb.com.cn/2017/0801/1501573284355.jpg";
    public static String JZ_PIC_2 = "http://www.imegabox.com/filmimages/216_261/201541175159249237.png";
    public static String IS_LOGIN_STATUS = "is_login_status";
    public static String LOGIN_USER_PHONE = "login_user_phone";

    /*****
     * 支付宝支付参数
     */
    public static class AliPay {
        // 商户PID
        public static final String PARTNER = "2088121919039375";
        // 商户收款账号
        public static final String SELLER = "pay@yalalat.com";
        // 商户私钥，pkcs8格式
        public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3jULWYOnSb3mslbtO4HdIJAaApm5H/C02duaBoLZJfseu/sMLtCan6k6ZHF1H8nV2EDns7N9cJF2zmNWxUGZQVpNaw0hhg1vdZIWZnWzyQ+wlZuLuUE7pl+uSYR+xMbEwfXQoWQ/Gw7o7nH7WUt9rPXAy+5PN1PmPRBtV8J9InAgMBAAECgYEAhGxqdLncykWJLx/D0lKsOTWetJtPqtk6gL5mHb+JSHMEGWxtUQMNokTlgyhA0yRgej8F+lqp7oSgmYlR6GIeEztLeJZ+UsQMCqO2d3pCkZCl2GT5rDfV3o96cWwc5HG+oIF+JyDfPFMeslZT0byLQLenGwNbbr7B6WOTEpiWOgECQQDtR6Ztfp22xvlXB6+2XbJGni/OFOZFQb4qINQUWf48t+/ZxqHQIrcyrLMqX7fi7kO7kf+y8Zh8wv3mbqo/FZXhAkEAzN59Ge17/s4VfXTW598MRDoduwmXGmF55WhU29syOaErSzJA2LZ2NjsEQyVOZTlRKES53Yk7ucn+gkBlbSXZBwJAKPO3zqgOslAPEq0572CmdzewCoJi58sb9gtqbwTFM1ePpud4YALN2Yoi9gpFI3555DKjjg/SgJ3q0k2BRpgEIQJBAMQk+C2MvDy23shgxyj02m+wHKroga0WSnijrStZ7/pRHnw+Puu9tewyEOCgpTAw8kzVkAZUz8QPawcV1IXtyJ0CQC/dk2K4I6vOS1MkdTS9l2rzauzv27dLTUPdC2ee2o//66h4a9mzIzrMw5m+sUGe81JbmE5xQE1EoEopQds5g/o=";     // 支付宝公钥
        public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
        public static final int SDK_PAY_FLAG = 1;
    }

    public static class WXPay {
        //appid
        //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wx275d8ba3c5fccaf5"/>为新设置的appid
        public static final String APP_ID = "wx120b649484505c67";
        //商户号
        public static final String MCH_ID = "1306645401";
        //  API密钥，在商户平台设置
        public static final String API_KEY = "70FB3464792B4C33B89B1AB11548C4E2";
    }


    public static class Net {
        //测试接口
//        public static final String URL = "http://120.24.47.139:8088";
        //正式接口
//        public static final String URL = "https://api.chezhy.cn:2043";

    }
}
