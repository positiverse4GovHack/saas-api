package com.positiverse.govhack.saas.logic;

import com.positiverse.govhack.saas.dict.*;
import com.positiverse.govhack.saas.client.EthereumClient;
import com.positiverse.govhack.saas.client.dto.GiveConsentEvent;
import com.positiverse.govhack.saas.client.dto.WithdrawConsentEvent;
import com.positiverse.govhack.saas.model.Consent;
import com.positiverse.govhack.saas.model.ConsentType;
import com.positiverse.govhack.saas.model.PersonalDataType;
import com.positiverse.govhack.saas.util.DateUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by gha on 03.01.17.
 */
public class ConsentLogic {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Consent> getConsentsForPersonalDataSubject(String ethereumNodeUrl, String smartContractAddress,
                                                      String personalDataSubject) throws IOException {
        return getConsents(ethereumNodeUrl, smartContractAddress, personalDataSubject, null);
    }

    public List<Consent> getConsentsForDataController(String ethereumNodeUrl, String smartContractAddress,
                                                      String dataController) throws IOException {
        return getConsents(ethereumNodeUrl, smartContractAddress, null, dataController);
    }

    /**
     * TODO: Ideas for improvement
     * This method re-reads the whole log for smart contract every time a client asks. It it inefficient.
     * It can be improved by adding addresses of personal data subject and data controller to the filter,
     * or by caching log locally in SaaS as new blocks arrive.
     */
    public List<Consent> getConsents(String ethereumNodeUrl, String smartContractAddress,
                                     String personalDataSubject, String dataController) throws IOException {

        EthereumClient ethereumClient = new EthereumClient(ethereumNodeUrl, smartContractAddress);
        List<GiveConsentEvent> giveConsentEvents = ethereumClient.getGiveConsentLog();
        List<WithdrawConsentEvent> withdrawConsentEvents = ethereumClient.getWithdrawConsentLog();

        Map<String, GiveConsentEvent> givingMap = new HashMap<>();
        for (GiveConsentEvent give : giveConsentEvents) {
            givingMap.put(give.getGiveConsentTxHash(), give);
        }

        Map<String, WithdrawConsentEvent> withdrawalMap = new HashMap<>();
        for (WithdrawConsentEvent withdraw : withdrawConsentEvents) {
            String giveConsentTxHash = "0x" + Hex.encodeHexString(withdraw.getGiveConsentTxHash());
            // filter out withdrawals of non-existent gives
            GiveConsentEvent give = givingMap.get(giveConsentTxHash);
            if (give == null) {
                continue;
            }
            // filter out withdrawals by someone who has not given
            if (!withdraw.getPersonalDataSubject().equals(give.getPersonalDataSubject())) {
                continue;
            }
            WithdrawConsentEvent previous = withdrawalMap.get(giveConsentTxHash);
            // replace withdrawal only when it is earlier than the one already in map
            if (previous == null || previous.getBlockTs().compareTo(withdraw.getBlockTs()) > 0) {
                withdrawalMap.put(giveConsentTxHash, withdraw);
            }
        }

        List<Consent> result = new ArrayList<>();
        for (GiveConsentEvent give : giveConsentEvents) {
            if (personalDataSubject != null && !personalDataSubject.equals(give.getPersonalDataSubject())) {
                continue;
            }
            if (dataController != null && !dataController.equals(give.getDataController())) {
                continue;
            }
            try {
                Consent consent = convertToConsent(give);
                WithdrawConsentEvent withdraw = withdrawalMap.get(give.getGiveConsentTxHash());
                if (withdraw != null) {
                    consent.setWithdrawTimestamp(DateUtils.convertToDate(withdraw.getBlockTs()));
                }
                result.add(consent);
            } catch (Exception e) {
                logger.info(String.format("Unable to convert consent: %s", give.toString()), e.toString());
            }
        }

        return result;
    }

    private Consent convertToConsent(GiveConsentEvent event) throws EntryNotFoundException {
        Consent consent = new Consent();
        consent.setGiveConsentTxHash(event.getGiveConsentTxHash());
        consent.setPersonalDataSubject(UserDictionary.get(event.getPersonalDataSubject()));
        consent.setDataController(OrganizationDictionary.get(event.getDataController()));
        ConsentType consentType = ConsentTypesDictionary.get(event.getConsentId());
        consent.setConsentType(consentType);
        List<PersonalDataType> eventDataTypes = PersonalDataType.convertFromBigInteger(new BigInteger(1, event.getPersonalDataSetId()));
        List<PersonalDataType> personalDataTypes = new ArrayList<>();
        for (PersonalDataType personalDataType : eventDataTypes) {
            personalDataTypes.add(personalDataType);
        }
        consent.setPersonalDataTypes(personalDataTypes);
        consent.setPersonalDataHash(event.getPersonalDataHash());
        consent.setGiveTimestamp(DateUtils.convertToDate(event.getBlockTs()));
        return consent;
    }

}
