package com.positiverse.govhack.saas.model;

import com.positiverse.govhack.saas.dict.EntryNotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gha on 31.12.16.
 *
 * TODO: Ideas for improvement
 * This dictionary could be based on a hash map to avoid iterating through every element on lookup.
 */
public enum PersonalDataType {

    NAME(0, "name", "name"),
    BIRTH_DATE(1, "birthDate", "birth date"),
    POSTAL_ADDRESS(2, "postalAddress", "postal address"),
    MOBILE_NUMBER(3, "mobileNumber", "mobile number"),
    EMAIL_ADDRESS(4, "emailAddress", "e-mail address"),
    BROWSER_COOKIES(5, "browserCookies", "browser cookies");

    private int index;
    private String apiName;
    private String webName;

    PersonalDataType(int index, String apiName, String webName) {
        this.index = index;
        this.apiName = apiName;
        this.webName = webName;
    }

    public int getIndex() {
        return index;
    }

    public String getApiName() {
        return apiName;
    }

    public String getWebName() {
        return webName;
    }

    public static PersonalDataType getByIndex(int index) throws EntryNotFoundException {
        for (PersonalDataType type : PersonalDataType.values()) {
            if (type.getIndex() == index) {
                return type;
            }
        }
        throw new EntryNotFoundException(String.format("Unknown personal data type id: %d", index));
    }

    public static PersonalDataType getByApiName(String apiName) throws EntryNotFoundException {
        for (PersonalDataType type : PersonalDataType.values()) {
            if (type.getApiName().equals(apiName)) {
                return type;
            }
        }
        throw new EntryNotFoundException(String.format("Unknown personal data type: %s", apiName));
    }

    public static List<PersonalDataType> convertFromBigInteger(BigInteger bi) throws EntryNotFoundException {
        List<PersonalDataType> result = new ArrayList<>();
        int bitLength = bi.bitLength();
        for (int i = 0; i < bitLength; i++) {
            if (bi.testBit(i)) {
                result.add(getByIndex(i));
            }
        }
        return result;
    }

}
