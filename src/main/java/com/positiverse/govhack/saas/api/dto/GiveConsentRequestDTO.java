package com.positiverse.govhack.saas.api.dto;

import java.util.List;

/**
 * Created by Grzegorz Hałaś on 2016-12-30.
 */
public class GiveConsentRequestDTO {

    private String personalDataSubject;
    private String dataController;
    private String processingPurpose;
    private String processingPurposeType;
    private String processingType;
    private String potentialDisclosures;
    private List<GiveConsentRequestAttributeDTO> personalDataAttributes;

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

    public List<GiveConsentRequestAttributeDTO> getPersonalDataAttributes() {
        return personalDataAttributes;
    }

    public void setPersonalDataAttributes(List<GiveConsentRequestAttributeDTO> personalDataAttributes) {
        this.personalDataAttributes = personalDataAttributes;
    }

    @Override
    public String toString() {
        return "GiveConsentRequestDTO{" +
                "personalDataSubject='" + personalDataSubject + '\'' +
                ", dataController='" + dataController + '\'' +
                ", processingPurpose='" + processingPurpose + '\'' +
                ", processingPurposeType='" + processingPurposeType + '\'' +
                ", processingType='" + processingType + '\'' +
                ", potentialDisclosures='" + potentialDisclosures + '\'' +
                ", personalDataAttributes=" + personalDataAttributes +
                '}';
    }
}
