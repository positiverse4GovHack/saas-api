package com.positiverse.govhack.saas.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.math.BigInteger;

/**
 * Created by gha on 30.12.16.
 */
public class HashUtils {

    public static byte[] computeSHA3256(byte[] plaintext) {
        SHA3.Digest256 digest = new SHA3.Digest256();
        byte[] hash = digest.digest(plaintext);
        return hash;
    }

    public static byte[] getHash(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(object);
            byte[] hash = computeSHA3256(jsonInString.getBytes());
            return hash;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toByteArray(BigInteger bi, int length) {
        byte[] bytes = new byte[32];
        byte[] setBytes = bi.toByteArray();
        System.arraycopy(setBytes, 0, bytes, bytes.length - setBytes.length, setBytes.length);
        return bytes;
    }

}
