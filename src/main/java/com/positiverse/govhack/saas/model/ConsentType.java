package com.positiverse.govhack.saas.model;

/**
 * Created by gha on 31.12.16.
 */
public class ConsentType {

    private String processingPurpose;
    private String processingPurposeType;
    private String processingType;
    private String potentialDisclosures;

    public ConsentType(String processingPurpose, String processingPurposeType, String processingType, String potentialDisclosures) {
        this.processingPurpose = processingPurpose;
        this.processingPurposeType = processingPurposeType;
        this.processingType = processingType;
        this.potentialDisclosures = potentialDisclosures;
    }

    public String getProcessingPurpose() {
        return processingPurpose;
    }

    public String getProcessingPurposeType() {
        return processingPurposeType;
    }

    public String getProcessingType() {
        return processingType;
    }

    public String getPotentialDisclosures() {
        return potentialDisclosures;
    }

    @Override
    public String toString() {
        return "ConsentType{" +
                "processingPurpose='" + processingPurpose + '\'' +
                ", processingPurposeType='" + processingPurposeType + '\'' +
                ", processingType='" + processingType + '\'' +
                ", potentialDisclosures='" + potentialDisclosures + '\'' +
                '}';
    }
}
