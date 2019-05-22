package com.hrfid.acs.util;

/**
 * Created by Akshay Kalpe on 15/04/17.
 */
public class AppConstants {

    //  Staging Data

    public static final String APP_PARTNER_KEY = "hotspotappstg";
    public static final String APP_PARTNER_P = "hotspotappstg@stg123";

    public static final String SSO_LOGIN_URL =
            "https://loginstg.astro.com.my/ssowebnx/login.aspx?pid=" + APP_PARTNER_KEY
                    + "&ReturnUrl=https://loginstg.astro.com.my/ssowebnx/appreturn.aspx";
    public static final String SSO_REGISTRATION_URL =
            "https://loginstg.astro.com.my/ssowebnx/registration.aspx?pid=" + APP_PARTNER_KEY +
                    "&ReturnUrl=https://loginstg.astro.com.my/ssowebnx/appreturn.aspx";

    public static final String ENDPOINT_URL_ASTRO = "https://loginstg.astro.com.my/";
    public static final String ENDPOINT_URL = "http://10.30.10.110:8080/";
    public static final String ENDPOINT_URL_ASTRO_FAQ = "http://support.astro.com.my/rest/content/site/Astrohelpsupport/en-us/all/read-watch-listen-shop/";
    public static final String ENDPOINT_URL_ASTRO_WEBVIEW_CHECKER = "http://stg-hotspot.pink.cat/rest/";
    public static final String ABOUT_URL = "http://stg-hotspot.pink.cat/MobileAppPages/About-US-Apps.aspx";
    public static final String PRIVACY_URL = "http://stg-hotspot.pink.cat/MobileAppPages/Privacy-Policy-Apps.aspx";
    public static final String APP_LANGUAGE = "eng";
    public static final String ENDPOINT_EDIT_PROFILE_URL = "https://loginstg.astro.com.my/profile/Overview.aspx?pid=hotspotappstg";
    public static final String ENDPOINT_URL_IMAGE = "http://stg-hotspot.pink.cat/CMSPages/GetFile.aspx?guid=";
    public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_USER = "hotspotapi";
    public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_PASS = "hotspot@pi";


    //*-----------*//


    // HOTSPOT.com.my pointing  Production Data
/*
    public static final String SSO_LOGIN_URL = "https://login.astro.com.my/ssowebnx/login.aspx?pid=hotspotapp&ReturnUrl=https://login.astro.com.my/ssowebnx/appreturn.aspx";
    public static final String APP_PARTNER_KEY = "hotspotapp";
    public static final String APP_PARTNER_P = "hotspotapp@prd123";
    public static final String SSO_REGISTRATION_URL = "https://login.astro.com.my/ssowebnx/registration.aspx?pid=hotspotapp&returnUrl=https://login.astro.com.my/ssowebnx/appreturn.aspx";
    public static final String ENDPOINT_URL_ASTRO = "https://login.astro.com.my";
    public static final String ENDPOINT_URL_KENTICO = "http://prod-hotspot.pink.cat/customapi/";
    public static final String ENDPOINT_URL_ASTRO_FAQ = "http://support.astro.com.my/rest/content/site/Astrohelpsupport/en-us/all/read-watch-listen-shop/";
    public static final String ENDPOINT_EDIT_PROFILE_URL = "https://login.astro.com.my/profile/Overview.aspx?pid=hotspotapp";
    public static final String ENDPOINT_URL_ASTRO_WEBVIEW_CHECKER = "http://hotspot.com.my/rest/";
    public static final String ABOUT_URL = "http://www.hotspot.com.my/MobileAppPages/About-US-Apps.aspx";
    public static final String PRIVACY_URL = "http://www.hotspot.com.my/MobileAppPages/Privacy-Policy-Apps.aspx";
    public static final String APP_LANGUAGE = "eng";
    public static String ENDPOINT_URL_IMAGE = "http://www.hotspot.com.my/CMSPages/GetFile.aspx?guid=";
    public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_USER = "hotspotform";
    public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_PASS = "Hot$pot2018";*/
    //*-----------*//
    //*-----------*//


    public static final String STRING_SSO_TICKET_ID = "ssoticketid";
    public static final String STRING_TBT_TICKET_ID = "tbtticketid";
    public static final String API_ASTRO_FAQ = "";
    public static final String API_ASTRO_WEBVIEW_CHECKER = "WEBVIEW_CHECKER";
    public static final String JWPLAYER_AD_ID = "http://serv.adx.astro.com.my/ads/v2?apikey:1764d2919d0f69bcfaf3e41b57d3f05d;adformat:vpaid;device:mobile;type:app;app:[app_name];playerWidth:__player-width__;playerHeight:__player-height__;mediaTitle:__item-title__;mediaDescription:__item-description__;mediaDuration:__item-duration__;cb:__random-number__";
    public static final String TEXT_GEMPAK = "hotspotfaq";
    public static final String GEMPAK_RANDOM_DATA = "Bar12345Bar12345";
    public static final String IV = "RandomInitVector";
    public static final String SSO_SUCCESS_RESULT_CODE = "01";
    public static final String API_KENTICO = "KENTICO";
    public static final String API_ASTRO = "ASTRO";
    //public static final String GEMPAK_APP_FOLDER_PATH = "GEMPAK";
    public static final String DMP_APPLICATION_ID = "49707";
    public static final String TEXT_GEMPAK_P = "hotspot@faq";



    public static String USER_ID = "9B6716D95245BDC9DD1C4938CA2701";
    public static final String ComScore_PUBLISHER_SECRET_KEY = "434644afe0bd45e1d643c12f7cd99255";
    public static final String ComScore_PUBLISHER_ID = "6036540";
    //public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_USER = "hotspotform";
    // public static final String TEXT_HOTSPOT_WEBVIEW_CHECKER_PASS = "Hot$pot2018";
    public static String API_TO_HIT = "";
    public static boolean isComingFromBranchIO = false;
    public static boolean isLiveTabSelected = false;
//  Staging
//  Partner key: hotspotappstg
//  Password: hotspotappstg@stg123
//
//      Production
//  Parner key: hotspotapp
//  Password: hotspotapp@prd123

    //ACS Constant
    public static final String APP_NAME = "ACS";
    public static final String APP_VERSION = "1.0";
    public static final String APP_OS = "Android";
    public static final String LOGIN_EVENT = "APP LOGIN";
    public static final String LOGIN_URL = "sit-stage.controlpoint.healthrfid.com/";
    public static final String LOGOUT = "App Logout";
    public static final String GET_NOTIFICATION = "Get Notifications";

}
