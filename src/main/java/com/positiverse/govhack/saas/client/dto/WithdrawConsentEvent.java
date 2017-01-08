package com.positiverse.govhack.saas.client.dto;

import com.positiverse.govhack.saas.util.DateUtils;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

/**
 * Created by Jacek Szczepański on 2016-12-30.
 */
public class WithdrawConsentEvent {

    private String withdrawConsentTxHash;
    private String personalDataSubject;
    private byte[] giveConsentTxHash;
    private BigInteger blockTs;

    public String getWithdrawConsentTxHash() {
        return withdrawConsentTxHash;
    }

    public void setWithdrawConsentTxHash(String withdrawConsentTxHash) {
        this.withdrawConsentTxHash = withdrawConsentTxHash;
    }

    public String getPersonalDataSubject() {
        return personalDataSubject;
    }

    public void setPersonalDataSubject(String personalDataSubject) {
        this.personalDataSubject = personalDataSubject;
    }

    public byte[] getGiveConsentTxHash() {
        return giveConsentTxHash;
    }

    public void setGiveConsentTxHash(byte[] giveConsentTxHash) {
        this.giveConsentTxHash = giveConsentTxHash;
    }

    public BigInteger getBlockTs() {
        return blockTs;
    }

    public void setBlockTs(BigInteger blockTs) {
        this.blockTs = blockTs;
    }

    @Override
    public String toString() {
        return "WithdrawConsentEvent{" +
                "withdrawConsentTxHash='" + withdrawConsentTxHash + '\'' +
                ", personalDataSubject='" + personalDataSubject + '\'' +
                ", giveConsentTxHash='" + Hex.encodeHexString(giveConsentTxHash) + '\'' +
                ", blockTs='" + DateUtils.convertToDate(blockTs) + '\'' +
                '}';
    }
}
