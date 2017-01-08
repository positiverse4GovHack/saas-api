package com.positiverse.govhack.saas.api.dto;

import com.positiverse.govhack.saas.model.Consent;
import com.positiverse.govhack.saas.model.PersonalDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by gha on 2016-12-31.
 */
public class ConsentDTO {

    private String giveConsentTxHash;
    private String personalDataSubject;
    private String dataController;
    private String processingPurpose;
    private String processingPurposeType;
    private String processingType;
    private String potentialDisclosures;
    private List<String> personalDataTypes;
    private byte[] personalDataHash;
    private Date giveTimestamp;
    private Date withdrawTimestamp;

    public String getGiveConsentTxHash() {
        return giveConsentTxHash;
    }

    public void setGiveConsentTxHash(String giveConsentTxHash) {
        this.giveConsentTxHash = giveConsentTxHash;
    }

    public String getPersonalDataSubject() {
        return personalDataSubject;
    }

    public void setPersonalDataSubject(String personalDataSubject) {
        this.personalDataSubject = personalDataSubject;
    }

    public String getDataController() {
        return dataController;
    }

    public void setDataController(String dataController) {
        this.dataController = dataController;
    }

    public String getProcessingPurpose() {
        return processingPurpose;
    }

    public void setProcessingPurpose(String processingPurpose) {
        this.processingPurpose = processingPurpose;
    }

    public String getProcessingPurposeType() {
        return processingPurposeType;
    }

    public void setProcessingPurposeType(String processingPurposeType) {
        this.processingPurposeType = processingPurposeType;
    }

    public String getProcessingType() {
        return processingType;
    }

    public void setProcessingType(String processingType) {
        this.processingType = processingType;
    }

    public String getPotentialDisclosures() {
        return potentialDisclosures;
    }

    public void setPotentialDisclosures(String potentialDisclosures) {
        this.potentialDisclosures = potentialDisclosures;
    }

    public List<String> getPersonalDataTypes() {
        return personalDataTypes;
    }

    public void setPersonalDataTypes(List<String> personalDataTypes) {
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

    public static ConsentDTO convertfromConsent(Consent consent) {
        ConsentDTO result = new ConsentDTO();
        result.setGiveConsentTxHash(consent.getGiveConsentTxHash());
        result.setPersonalDataSubject(consent.getPersonalDataSubject().getAddress());
        result.setDataController(consent.getDataController().getAddress());
        result.setProcessingPurpose(consent.getConsentType().getProcessingPurpose());
        result.setProcessingPurposeType(consent.getConsentType().getProcessingPurposeType());
        result.setProcessingType(consent.getConsentType().getProcessingType());
        result.setPotentialDisclosures(consent.getConsentType().getPotentialDisclosures());
        List<String> apiNames = new ArrayList<>();
        for (PersonalDataType type : consent.getPersonalDataTypes()) {
            apiNames.add(type.getApiName());
        }
        result.setPersonalDataTypes(apiNames);
        result.setPersonalDataHash(consent.getPersonalDataHash());
        result.setGiveTimestamp(consent.getGiveTimestamp());
        result.setWithdrawTimestamp(consent.getWithdrawTimestamp());
        return result;
    }

    @Override
    public String toString() {
        return "ConsentDTO{" +
                "giveConsentTxHash='" + giveConsentTxHash + '\'' +
                ", personalDataSubject='" + personalDataSubject + '\'' +
                ", dataController='" + dataController + '\'' +
                ", processingPurpose='" + processingPurpose + '\'' +
                ", processingPurposeType='" + processingPurposeType + '\'' +
                ", processingType='" + processingType + '\'' +
                ", potentialDisclosures='" + potentialDisclosures + '\'' +
                ", personalDataTypes=" + personalDataTypes +
                ", personalDataHash=" + Arrays.toString(personalDataHash) +
                ", giveTimestamp=" + giveTimestamp +
                ", withdrawTimestamp=" + withdrawTimestamp +
                '}';
    }
}
