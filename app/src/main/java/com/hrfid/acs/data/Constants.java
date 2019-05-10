package com.hrfid.acs.data;

import android.Manifest;

public class Constants {
    public static final String SERVER_BASE_VERSION = "0.12.6";


    public static final String ENV_STAGE = "env_stage";
    public static final String ENV_PROD = "env_prod";


    public static final String HTTPS_PROTOCOL = "https://";
    public static final String HTTP_PROTOCOL = "http://";

    public static final String DLG_INTERNET_CONNECTION = "dlg_internet_connection";
    public static final String DLG_DEV_NOT_REGISTERED = "dlg_dev_not_registered";
    public static final String DLG_SERVER_UNAVAILABLE = "dlg_server_unavailable";
    public static final String DLG_VERSION_UPGRADE = "dlg_version_upgrade";
    public static final String DLG_BLOOD_COMPONENT_NOT_REGISTERED = "dlg_blood_component_not_registered";

    public static final int CHECK_CONN_TASK = 1;
    public static final int CHECK_REGISTRATION_TASK = 2;
    public static final int CHECK_SERVER_TASK = 3;
    public static final int CHECK_VERSION_TASK = 4;
    public static final int CHECK_LICENSE_TASK = 5;
    public static final int CHECK_REGISTRATION_BLOOD_GROUP_COMPONENT = 7;

    //RK
    public static final int CHECK_APP_PERMISSIONS = 6;


    public static final int CLOSE_ACTIVITY = 0;
    public static final int REGISTRATION_ACTIVITY = 1;
    public static final int MAIN_ACTIVITY = 2;
    public static final int USER_AUTH_ACTIVITY = 3;
    public static final int REG_BlOOD_COMP_ACTIVITY = 4;
    public static final int SEND_TO_LOC_ACTIVITY = 5;
    public static final int REC_TO_LOC_ACTIVITY = 6;
    public static final int DEVICE_REGISTRATION_ACTIVITY = 7;
    public static final int DEVICE_RFID_SCAN_ACTIVITY = 8;
    public static final int LOCATION_SELECT_ACTIVITY = 9;
    public static final int DEVICE_NAME_ACTIVITY = 10;

    public static final String REG_ACTIVITY_FRAG = "reg_activity_frag";
    public static final String MAIN_ACTIVITY_FRAG = "main_activity_frag";

    public static final int MAIN_SERVER_FRAG = 1;

    public static final int REG_SERVER_FRAG = 1;
    public static final int REG_LICENSE_FRAG = 2;
    public static final int REG_DEV_FRAG = 3;
    public static final int REG_LOC_FRAG = 4;
    //Prod
    public static final String DEV_CONTROLPOINT = "10.0.16.224:8080";
    public static final String TEST_CONTROLPOINT = "test";
    public static final String UAT_CONTROLPOINT = "uat";
    public static final String SIT_CONTROLPOINT = "sit";
    public static final String CONTROLPOINT = "controlpoint.healthrfid.com";

    //Stage
    public static final String SIT_STAGE_CONTROLPOINT = "sit-stage";

    public static final String SIT_NEW_CONTROLPOINT = "http://sit-controlpoint-alb-new-640763997.ap-southeast-2.elb.amazonaws.com";


    public static final int DEV_CONTROLPOINT_ID = 0;
    public static final int TEST_CONTROLPOINT_ID = 1;
    public static final int UAT_CONTROLPOINT_ID = 2;
    public static final int SIT_CONTROLPOINT_ID = 3;
    public static final int CONTROLPOINT_ID = 4;

    public static final String EMPTY_BLOCK = "";
    public static final String SCAN_MR_NUM = "mr_number";
    public static final String SCAN_CONFIRM_UR_CODE = "confirm_ur_code";
    public static final String SCAN_ADM_NUM = "adm_number";
    public static final String SCAN_BED_NUMBER = "bed_number";
    public static final String BEEP_SOUND = "android.resource://au.com.healthrfid.pathologyapp/raw/beep_cutted";


    public static final int USER_TAG_ID = 14;
    // TRANS LOG MODULE
    public static final int BED_COLLECTION = 7;
    // TRANS LOG EVENT TYPE
    public static final int LOGIN = 1;

    //RK Permission

    public static final int PERMISSION_REQUEST_CODE = 1240;
    public static final String[] appPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    //RK DATE FORMATS
    public static final String DATE_FORMAT_MYSQL_DB = "yyyy-MM-dd HH:mm:ss";
    /**
     * Status Result
     **/
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";

    //      Scan Barcodes
    public static final String SCAN_EXPIRY_DATE = "bc_expiry_date";
    public static final String SCAN_DONATION_ID = "scan_donation_id";
    public static final String SCAN_BLOOD_GROUP_CODE = "scan_blood_group_code";
    public static final String SCAN_BLOOD_GROUP_NAME = "scan_blood_group_name";
    public static final String SCAN_COMPONENT_CODE = "scan_component_code";
    public static final String SCAN_COMPONENT_NAME = "scan_component_name";
    public static final String SCAN_EXPIRY_DATE_CODE = "scan_expiry_date_code";
    public static final String SCAN_SPECIAL_TESTING_CODE = "scan_special_testing_code";
    public static final String SCAN_SPECIAL_TESTING_NAME = "scan_special_testing_name";
    public static final String USER_ROLE_SYSTEM_ADMIN = "System Admin";
    public static final String USER_ROLE_ADMIN = "Admin";
    public static final int USER_CARD_TAG_LENGHT = 16;
    public static final String USER_ROLE_TYPE = "User_role";
    public static final String USER_ROLE_TYPE_SENIOR_STAFF = "Senior Staff";
    public static final String USER_ROLE_TYPE_NURSE_STAFF = "Nurse";
    public static final String USER_ROLE_TYPE_LAB_STAFF = "Lab Staff";

    public static final String SENIOR_STAFF = "Senior Staff";
    public static final String TYPE_NURSE_STAFF = "Nurse";
    public static final String LAB_STAFF = "Lab Staff";


    public static int YELLOW_BUTTON = 139;
    public static int LEFT_BUTTON = 280;
    public static int RIGHT_BUTTON = 278;


    public static int CODABAR_LENGTH_DONATION_ID = 9;
    public static int CODABAR_LENGTH_BLOOD_GROUP = 5;
    public static int CODABAR_LENGTH_COMPONENT_CODE = 9;
    public static int CODABAR_LENGTH_EXPIRY_DATE_AND_TIME = 10;


    public static String CODABAR_DONATION_ID = "codabar_128_donation_id";
    public static String CODABAR_BLOOD_GROUP = "codabar_blood_group";
    public static String CODABAR_COMPONENT_CODE = "codabar_component_code";
    public static String CODABAR_EXPIRY_DATE_AND_TIME = "codabar_128_expiry_date_and_time";


    public static int ISBT_LENGTH_DONATION_ID = 16;
    public static int ISBT_LENGTH_BLOOD_GROUP = 6;
    public static int ISBT_LENGTH_COMPONENT_CODE = 10;
    public static int ISBT_LENGTH_EXPIRY_DATE_AND_TIME = 12;
    public static int ISBT_LENGTH_SPECIAL_TESTING = 20;

    public static String ISBT_DONATION_ID = "isbt_128_donation_id";
    public static String ISBT_BLOOD_GROUP = "isbt_128_blood_group";
    public static String ISBT_COMPONENT_CODE = "isbt_128_component_code";
    public static String ISBT_EXPIRY_DATE_AND_TIME = "isbt_128_expiry_date_and_time";
    public static String ISBT_SPECIAL_TESTING = "isbt_128_special_testing";


    public static final String BARCODE_SCAN_TYPE_CODABAR = "bc_scan_type_codabar";
    public static final String BARCODE_SCAN_TYPE_ISBT_128 = "bc_scan_type_isbt_128";

    //    Bundle keys
    public static final String KEY_ORG_ID = "key_org_id";

    //api fields for device registration
    public static final String DEVICE_MODEL = "deviceModel";
    public static final String DEVICE_MANUFACTURE = "deviceManufacturer";
    public static final String DEVICE_SERIAL = "deviceSerialNumber";
    public static final String DEVICE_VERSION = "deviceVersion";
    public static final String DEVICE_ORG_ID = "orgId";
    public static final String DEVICE_LOCATION_ID = "locationId";
    public static final String DEVICE_DEVICE_ID = "deviceId";
    public static final String DEVICE_DEVICE_TYPE = "deviceType";
    public static final String DEVICE_WIFI_MAC_ADDRESS = "deviceWifimac";
    public static final String DEVICE_BLUETOOTH_MAC_ADDRESS = "deviceBluetoothmac";
    public static final String DEVICE_APP_VERSION = "appVersion";
    public static final String DEVICE_NAME = "deviceName";
    public static final String DEVICE_ID = "deviceId";

//    Server Status
    public static final int STATUS_CODE_SUCCESS =200;
    public static final String STATUS_MESSAGE_SUCCESS ="REQ_SUCCESS";

    public static final String KEY_API_STATUS = "status";

//    Api Location Register keys
    public static final String KEY_REGISTER_LOCATION_ORG_ID ="orgId";
    public static final String KEY_REGISTER_LOCATION_DEVICE_ID ="deviceId";
    public static final String KEY_REGISTER_LOCATION_DEVICE_NAME ="deviceName";
    public static final String KEY_REGISTER_LOCATION_LOCATION_ID ="locationId";


    //Error Log
    public static final int ERROR_SOMETHING_WENT_WRONG =0;
    public static final int ERROR_DEVICE_NOT_REGISTERED =1;
    public static final int ERROR_DEVICE_ABRADE_REGISTERED_TO_DIFFERENT_ORG =1062;
}
