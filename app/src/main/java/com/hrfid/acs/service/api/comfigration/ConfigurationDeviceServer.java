package com.hrfid.acs.service.api.comfigration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfigurationDeviceServer {

    @SerializedName("app")
    @Expose
    private String app;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("db")
    @Expose
    private int db;

    @SerializedName("serverdatetime")
    @Expose
    private String serverdatetime;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public String getServerdatetime() {
        return serverdatetime;
    }

    public void setServerdatetime(String serverdatetime) {
        this.serverdatetime = serverdatetime;
    }
}
