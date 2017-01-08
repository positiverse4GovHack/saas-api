package com.positiverse.govhack.saas.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by gha on 2016-12-31.
 */
public class Consent {

    private String giveConsentTxHash;
    private User personalDataSubject;
    private Organization dataController;
    private ConsentType consentType;
    private List<PersonalDataType> personalDataTypes;
    private byte[] personalDataHash;
    private Date giveTimestamp;
    private Date withdrawTimestamp;

    public String getGiveConsentTxHash() {
        return giveConsentTxHash;
    }

    public void setGiveConsentTxHash(String giveConsentTxHash) {
        this.giveConsentTxHash = giveConsentTxHash;
    }

    public User getPersonalDataSubject() {
        return personalDataSubject;
    }

    public void setPersonalDataSubject(User personalDataSubject) {
        this.personalDataSubject = personalDataSubject;
    }

    public Organization getDataController() {
        return dataController;
    }

    public void setDataController(Organization dataController) {
        this.dataController = dataController;
    }

    public ConsentType getConsentType() {
        return consentType;
    }

    public void setConsentType(ConsentType consentType) {
        this.consentType = consentType;
    }

    public List<PersonalDataType> getPersonalDataTypes() {
        return personalDataTypes;
    }

    public void setPersonalDataTypes(List<PersonalDataType> personalDataTypes) {
        this.personalDataTypes = personalDataTypes;
    }

    public byte[] getPersonalDataHash() {
        return personalDataHash;
    }

    public void setPersonalDataHash(byte[] personalDataHash) {
        this.personalDataHash = personalDataHash;
    }

    public Date getGiveTimestamp() {
        return giveTimestamp;
    }

    public void setGiveTimestamp(Date giveTimestamp) {
        this.giveTimestamp = giveTimestamp;
    }

    public Date getWithdrawTimestamp() {
        return withdrawTimestamp;
    }

    public void setWithdrawTimestamp(Date withdrawTimestamp) {
        this.withdrawTimestamp = withdrawTimestamp;
    }

    public boolean isActive() {
        return withdrawTimestamp == null;
    }

    @Override
    public String toString() {
        return "Consent{" +
                "giveConsentTxHash='" + giveConsentTxHash + '\'' +
                ", personalDataSubject='" + personalDataSubject + '\'' +
                ", dataController='" + dataController + '\'' +
                ", consentType='" + consentType + '\'' +
                ", personalDataTypes=" + personalDataTypes +
                ", personalDataHash=" + Arrays.toString(personalDataHash) +
                ", giveTimestamp=" + giveTimestamp +
                ", withdrawTimestamp=" + withdrawTimestamp +
                '}';
    }
}
