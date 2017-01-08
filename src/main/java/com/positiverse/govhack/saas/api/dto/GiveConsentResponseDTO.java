package com.positiverse.govhack.saas.api.dto;

import java.util.Arrays;

/**
 * Created by gha on 31.12.16.
 */
public class GiveConsentResponseDTO {

    private String giveConsentTxHash;
    private byte[] personalDataPadding;

    public String getGiveConsentTxHash() {
        return giveConsentTxHash;
    }

    public void setGiveConsentTxHash(String giveConsentTxHash) {
        this.giveConsentTxHash = giveConsentTxHash;
    }

    public byte[] getPersonalDataPadding() {
        return personalDataPadding;
    }

    public void setPersonalDataPadding(byte[] personalDataPadding) {
        this.personalDataPadding = personalDataPadding;
    }

    @Override
    public String toString() {
        return "GiveConsentResponseDTO{" +
                "giveConsentTxHash='" + giveConsentTxHash + '\'' +
                ", personalDataPadding=" + Arrays.toString(personalDataPadding) +
                '}';
    }
}
