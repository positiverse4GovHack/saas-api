package com.positiverse.govhack.saas.api.dto;

/**
 * Created by gha on 31.12.16.
 */
public class GiveConsentRequestAttributeDTO {

    private String personalDataType;
    private String personalData;

    public String getPersonalDataType() {
        return personalDataType;
    }

    public void setPersonalDataType(String personalDataType) {
        this.personalDataType = personalDataType;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    @Override
    public String toString() {
        return "GiveConsentRequestAttributeDTO{" +
                "personalDataType='" + personalDataType + '\'' +
                ", personalData='" + personalData + '\'' +
                '}';
    }
}
