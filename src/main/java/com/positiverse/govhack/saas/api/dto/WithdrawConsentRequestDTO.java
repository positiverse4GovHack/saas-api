package com.positiverse.govhack.saas.api.dto;

/**
 * Created by gha on 03.01.17.
 */
public class WithdrawConsentRequestDTO {

    private String personalDataSubject;
    private String giveConsentTxHash;

    public String getPersonalDataSubject() {
        return personalDataSubject;
    }

    public void setPersonalDataSubject(String personalDataSubject) {
        this.personalDataSubject = personalDataSubject;
    }

    public String getGiveConsentTxHash() {
        return giveConsentTxHash;
    }

    public void setGiveConsentTxHash(String giveConsentTxHash) {
        this.giveConsentTxHash = giveConsentTxHash;
    }

    @Override
    public String toString() {
        return "WithdrawConsentRequestDTO{" +
                "personalDataSubject='" + personalDataSubject + '\'' +
                ", giveConsentTxHash='" + giveConsentTxHash + '\'' +
                '}';
    }
}
