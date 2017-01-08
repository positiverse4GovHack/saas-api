package com.positiverse.govhack.saas.api;

import com.positiverse.govhack.saas.dict.*;
import com.positiverse.govhack.saas.client.EthereumClient;
import com.positiverse.govhack.saas.logic.ConsentLogic;
import com.positiverse.govhack.saas.api.dto.*;
import com.positiverse.govhack.saas.model.Consent;
import com.positiverse.govhack.saas.model.ConsentType;
import com.positiverse.govhack.saas.model.PersonalDataType;
import com.positiverse.govhack.saas.util.HashUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Jacek Szczepański on 2016-12-15.
 * Modified by Grzegorz Hałaś on 2016-12-30.
 */
@RestController
@RequestMapping("/consents")
public class ConsentController {

    @Value("${ethereum.node.url}")
    private String ethereumNodeUrl;

    @Value("${smart.contract.address}")
    private String smartContractAddress;

    @Value("${coinbase.account.password}")
    private String coinbaseAccountPassword;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.POST, value = "/give", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<GiveConsentResponseDTO> give(@RequestBody GiveConsentRequestDTO giveConsentRequest) throws Exception {
        logger.info(String.format(">> give(%s)", giveConsentRequest));

        // only known users are allowed
        UserDictionary.get(giveConsentRequest.getPersonalDataSubject());

        // only known organizations are allowed
        OrganizationDictionary.get(giveConsentRequest.getDataController());

        ConsentType consentType = new ConsentType(giveConsentRequest.getProcessingPurpose(),
                giveConsentRequest.getProcessingPurposeType(), giveConsentRequest.getProcessingType(),
                giveConsentRequest.getPotentialDisclosures());
        byte[] consentId = HashUtils.getHash(consentType);
        logger.info(String.format("give(): consentId: %s", Hex.toHexString(consentId)));
        ConsentTypesDictionary.get(consentId);

        BigInteger dataTypesMask = BigInteger.valueOf(0L);
        Map<String, Object> attributesPlaintext = new TreeMap<>();
        for (GiveConsentRequestAttributeDTO attribute: giveConsentRequest.getPersonalDataAttributes()) {
            dataTypesMask = dataTypesMask.setBit(PersonalDataType.getByApiName(attribute.getPersonalDataType()).getIndex());
            attributesPlaintext.put(attribute.getPersonalDataType(), attribute.getPersonalData());
        }
        HashUtils.toByteArray(dataTypesMask, 32);
        byte[] padding = new byte[32];
        new SecureRandom().nextBytes(padding);
        attributesPlaintext.put("_padding", padding);
        byte[] personalDataHash = HashUtils.getHash(attributesPlaintext);
        logger.info(String.format("give(): personalDataHash: %s", Hex.toHexString(personalDataHash)));

        EthereumClient ethereumClient = new EthereumClient(ethereumNodeUrl, smartContractAddress);
        String txHash = ethereumClient.giveConsent(giveConsentRequest.getPersonalDataSubject(), coinbaseAccountPassword,
                giveConsentRequest.getDataController(), consentId, HashUtils.toByteArray(dataTypesMask, 32),
                personalDataHash);

        GiveConsentResponseDTO response = new GiveConsentResponseDTO();
        response.setGiveConsentTxHash(txHash);
        response.setPersonalDataPadding(padding);

        logger.info(String.format("<< give(%s)", response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/withdraw", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<WithdrawConsentResponseDTO> withdraw(@RequestBody WithdrawConsentRequestDTO withdrawConsentRequest) throws Exception {
        logger.info(String.format(">> withdraw(%s)", withdrawConsentRequest));

        // only known users are allowed
        UserDictionary.get(withdrawConsentRequest.getPersonalDataSubject());

        String giveConsentTxHash = withdrawConsentRequest.getGiveConsentTxHash();
        if (giveConsentTxHash.startsWith("0x")) {
            giveConsentTxHash = giveConsentTxHash.substring(2);
        }
        byte[] giveConsentTxHashBytes = Hex.decode(giveConsentTxHash);

        EthereumClient ethereumClient = new EthereumClient(ethereumNodeUrl, smartContractAddress);
        String txHash = ethereumClient.withdrawConsent(withdrawConsentRequest.getPersonalDataSubject(),
                coinbaseAccountPassword, giveConsentTxHashBytes);

        WithdrawConsentResponseDTO response = new WithdrawConsentResponseDTO();
        response.setWithdrawConsentTxHash(txHash);

        logger.info(String.format("<< withdraw(%s)", response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{personalDataSubject}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<ConsentDTO>> list(@PathVariable String personalDataSubject) throws IOException, EntryNotFoundException {
        logger.info(String.format(">> list(%s)", personalDataSubject));
        List<Consent> consents = new ConsentLogic().getConsentsForPersonalDataSubject(
                ethereumNodeUrl, smartContractAddress, personalDataSubject);
        List<ConsentDTO> consentDTOs = new ArrayList<>();
        for (Consent consent : consents) {
            consentDTOs.add(ConsentDTO.convertfromConsent(consent));
        }
        logger.info(String.format("<< list(%s)", consentDTOs));
        return new ResponseEntity<>(consentDTOs, HttpStatus.OK);
    }


}
