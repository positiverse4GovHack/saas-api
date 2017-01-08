package com.positiverse.govhack.saas.dict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.positiverse.govhack.saas.model.ConsentType;
import com.positiverse.govhack.saas.util.HashUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gha on 31.12.16.
 *
 * TODO: Ideas for improvement
 * This dictionary is static. It is sufficient for demo purposes. However, for production use it needs to be able
 * to grow as new consent types are introduced by organizations. It would be a good idea to place this dictionary
 * in Ethereum blockchain, as it would make it tamper-proof.
 */
public class ConsentTypesDictionary {

    private final static Logger logger = LoggerFactory.getLogger(ConsentTypesDictionary.class);
    private static Map<String, ConsentType> map = new HashMap<>();

    static {
        try {
            specialAdd(BigInteger.valueOf(1L), new ConsentType("Processing of the personal data usage consents through myConsents mobile app.", "serviceSignUp", "prerequisite", "none"));
            add(new ConsentType("Processing of the personal data usage consents through myConsents mobile app.", "serviceSignUp", "prerequisite", "none"));
            add(new ConsentType("Usage of services offered by eCommerce, processing of a shopping basket and purchases of products offered.", "serviceUsage", "required", "none"));
            add(new ConsentType("Finalization of the shopping transaction and delivery of purchased products.", "transaction", "required", "none"));
            add(new ConsentType("Better user experience of eCommerce service, registration of you shopping preference, analytics of eCommerce online service usage.", "profiling", "optional", "none"));
            add(new ConsentType("Marketing communication in an electronic form.", "marketing", "optional", "none"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static ConsentType get(byte[] hash) throws EntryNotFoundException {
        ConsentType result = map.get(Base64.getEncoder().encodeToString(hash));
        if (result == null) {
            throw new EntryNotFoundException(String.format("Unknown consent type: %s", Base64.getEncoder().encodeToString(hash)));
        }
        return result;
    }

    private static void add(ConsentType consentType) throws JsonProcessingException {
        byte[] hash = HashUtils.getHash(consentType);
        logger.info(String.format("Added to ConsentTypesDictionary: %s: %s", Hex.encodeHexString(hash), consentType));
        map.put(Base64.getEncoder().encodeToString(hash), consentType);
    }

    private static void specialAdd(BigInteger key, ConsentType consentType) throws JsonProcessingException {
        map.put(Base64.getEncoder().encodeToString(key.toByteArray()), consentType);
        logger.info(String.format("Added specially to ConsentTypesDictionary: %s: %s", key.toString(16), consentType));
    }
}
