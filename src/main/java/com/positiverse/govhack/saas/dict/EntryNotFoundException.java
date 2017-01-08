package com.positiverse.govhack.saas.dict;

/**
 * Created by gha on 31.12.16.
 */
public class EntryNotFoundException extends Exception {

    public EntryNotFoundException(String entryKey) {
        super(entryKey);
    }
}
