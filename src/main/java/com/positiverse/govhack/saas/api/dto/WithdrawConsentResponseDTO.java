package com.positiverse.govhack.saas.api.dto;

/**
 * Created by gha on 03.01.17.
 */
public class WithdrawConsentResponseDTO {

    private String withdrawConsentTxHash;

    public String getWithdrawConsentTxHash() {
        return withdrawConsentTxHash;
    }

    public void setWithdrawConsentTxHash(String withdrawConsentTxHash) {
        this.withdrawConsentTxHash = withdrawConsentTxHash;
    }

    @Override
    public String toString() {
        return "WithdrawConsentResponseDTO{" +
                "withdrawConsentTxHash='" + withdrawConsentTxHash + '\'' +
                '}';
    }
}
