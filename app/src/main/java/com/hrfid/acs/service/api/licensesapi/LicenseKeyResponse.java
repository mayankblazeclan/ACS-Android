package com.hrfid.acs.service.api.licensesapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LicenseKeyResponse {

    @SerializedName("isLicenseKeyValid")
    @Expose
    private boolean isLicenseKeyValid;

    public boolean isLicenseKeyValid() {
        return isLicenseKeyValid;
    }

    public void setLicenseKeyValid(boolean licenseKeyValid) {
        isLicenseKeyValid = licenseKeyValid;
    }
}
