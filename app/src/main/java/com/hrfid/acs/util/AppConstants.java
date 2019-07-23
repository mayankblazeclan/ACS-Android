package com.hrfid.acs.util;

import com.hrfid.acs.HRFIDACSApplication;
import com.hrfid.acs.pref.SharedPrefsManager;

/**
 * Created by Akshay Kalpe on 15/04/17.
 */
public class AppConstants {


 //public static final String URL = "sit-stage-test.controlpoint.healthrfid.com/";

 public static final String URL = "10.30.10.110:8080/";



 //public static final String URL = "";

 private static SharedPrefsManager spfManager  = new SharedPrefsManager();
 //  Staging Data

 //public static final String ENDPOINT_URL_ASTRO = "https://loginstg.astro.com.my/";
 //public static final String LOGIN_URL = "10.30.10.110:8080/";
 //public static final String ENDPOINT_URL = "http://10.30.10.110:8080/";

 public static final String LOGIN_URL = spfManager.getApiUrl(HRFIDACSApplication.getInstance().getApplicationContext())+"/";
 public static final String ENDPOINT_URL = "http://"+LOGIN_URL;

 //For STAGING (SIT)
    /*public static final String ENDPOINT_URL = "https://sit-stage-test.controlpoint.healthrfid.com/";
    public static final String LOGIN_URL = "sit-stage.controlpoint.healthrfid.com/";*/

 // UAT URL
 // public static final String ENDPOINT_URL = "https://uat-api.controlpoint.healthrfid.com/";
 // public static final String LOGIN_URL = "uat-api.controlpoint.healthrfid.com/";


 public static final String ENDPOINT_URL_ASTRO_FAQ = "http://support.astro.com.my/rest/content/site/Astrohelpsupport/en-us/all/read-watch-listen-shop/";
 public static final String ENDPOINT_URL_ASTRO_WEBVIEW_CHECKER = "http://stg-hotspot.pink.cat/rest/";
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


 public static final String API_ASTRO_FAQ = "";
 public static final String API_ASTRO_WEBVIEW_CHECKER = "WEBVIEW_CHECKER";
 public static final String TEXT_GEMPAK = "hotspotfaq";
 public static final String GEMPAK_RANDOM_DATA = "Bar12345Bar12345";
 public static final String IV = "RandomInitVector";
 public static final String API_KENTICO = "KENTICO";
 public static final String API_ASTRO = "ASTRO";
 public static final String TEXT_GEMPAK_P = "hotspot@faq";
 public static final String ACTIVITY = "ACTIVITY";
 public static final String INQUEUE = "In_Queue";
 public static String API_TO_HIT = "";

 //ACS Constant
 public static final String APP_NAME = "ACS";
 public static final String APP_VERSION = "1.0";
 public static final String APP_OS = "Android";
 public static final String LOGIN_EVENT = "App Login";

 public static final String LOGOUT = "App Logout";
 public static final String GET_NOTIFICATION = "Get Notifications";
 public static final String READ_NOTIFICATION = "Read Notifications";
 public static final String CREATE_SCHEDULE = "Create Study Schedule";
 public static final String GET_SCHEDULE = "Study Schedule List";
 public static final String MODIFY_ACTIVITY = "Modify Study Schedule";
 public static final String DELETE_ACTIVITY = "Delete Study Schedule";
 public static final String ADD_SUBJECT = "Subject Onboarding";
 public static final String GET_SUBJECT = "Subject List";
 public static final String MAP_SUBJECT = "Map Subject";
 public static final String DELETE_SUBJECT = "Delete Subject";
 public static final String MODIFY_SUBJECT = "Modify Subject";

 public static final String SUBJECT_APPROVE = "Approve Subject";
 public static final String SUBJECT_REJECT = "Reject Subject";
 public static final String SEARCH_SUBJECT = "Search Subject";
 public static final String IDENTIFY_SUBJECT = "Identify Subject";
 public static final String IN_STOCK = "In_Stock";
 public static final String ADD_KIT = "Add Kit";
 public static final String GET_KIT_DETAILS = "Kit List";
 public static final String MODIFY_KIT = "Modify Kit";
 public static final String MAP_KIT = "Map Kit";
 public static final String KIT_REASON = "Dismiss Kit";
 public static final String SEARCH_KIT = "Kit Advance Search";
 public static final String KIT_RETURN = "Return Kit";
 public static final String ADD_TSU = "Add TSU";
 public static final String GET_TSU_DETAILS = "TSU List";
 public static final String MODIFY_TSU = "Modify TSU";

}
