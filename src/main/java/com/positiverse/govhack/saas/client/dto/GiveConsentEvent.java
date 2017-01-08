package com.positiverse.govhack.saas.client.dto;

import com.positiverse.govhack.saas.util.DateUtils;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

/**
 * Created by Jacek Szczepański on 2016-12-30.
 */
public class GiveConsentEvent {

    private String giveConsentTxHash;
    private String personalDataSubject;
    private String dataController;
    private byte[] consentId;
    private byte[] personalDataSetId;
    private byte[] personalDataHash;
    private BigInteger blockTs;

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

    public byte[] getConsentId() {
        return consentId;
    }

    public void setConsentId(byte[] consentId) {
        this.consentId = consentId;
    }

    public byte[] getPersonalDataSetId() {
        return personalDataSetId;
    }

    public void setPersonalDataSetId(byte[] personalDataSetId) {
        this.personalDataSetId = personalDataSetId;
    }

    public byte[] getPersonalDataHash() {
        return personalDataHash;
    }

    public void setPersonalDataHash(byte[] personalDataHash) {
        this.personalDataHash = personalDataHash;
    }

    public BigInteger getBlockTs() {
        return blockTs;
    }

    public void setBlockTs(BigInteger blockTs) {
        this.blockTs = blockTs;
    }

    @Override
    public String toString() {
        return "GiveConsentEvent{" +
                "giveConsentTxHash='" + giveConsentTxHash + '\'' +
                ", personalDataSubject='" + personalDataSubject + '\'' +
                ", dataController='" + dataController + '\'' +
                ", consentId='" + Hex.encodeHexString(consentId) + '\'' +
                ", personalDataSetId='" + Hex.encodeHexString(personalDataSetId) + '\'' +
                ", personalDataHash='" + Hex.encodeHexString(personalDataHash) + '\'' +
                ", blockTs='" + DateUtils.convertToDate(blockTs) + '\'' +
                '}';
    }
}
