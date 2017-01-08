package com.positiverse.govhack.saas.dict;

import com.positiverse.govhack.saas.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by inst on 05.01.17.
 *
 * TODO: Ideas for improvement
 * This dictionary is static. It is sufficient for demo purposes. However, for production use it needs to be able
 * to grow as new consent types are introduced by organizations. It would be a good idea to place this dictionary
 * in Ethereum blockchain, as it would make it tamper-proof.
 */
public class OrganizationDictionary {
    private final static Logger logger = LoggerFactory.getLogger(ConsentTypesDictionary.class);

    private static Map<String, Organization> map = new HashMap<>();

    static {
        add(new Organization("0x00000000000000000000000000000000deadcafe", "Dead Cafe", "Test Organization", "img/cafe.png"));
        add(new Organization("0x000000000000000000000000000000deadcafe02", "Dead Cafe 2", "Test Organization", "img/cafe.png"));
        add(new Organization("0x000000000000000000000000000000deadcafe03", "Dead Cafe 3", "Test Organization", "img/cafe.png"));
        add(new Organization("0x0e81ba2134e382f2d36ad3f247035bc035746756", "eCommerce", "Your favorite mobile eShop", "img/ecommerce.png"));
        add(new Organization("0x7cd1b13da3dd0415c353b97821877577777736b6", "Interplanetary Bank", "The sky is not the limit anymore.", "img/interplanetary.png"));
    }

    public static Collection<Organization> get() {
        List<Organization> result = new ArrayList<>(map.values());
        Collections.sort(result);
        return result;
    }

    public static Organization get(String address) throws EntryNotFoundException {
        Organization result = map.get(address.toLowerCase());
        if (result == null) {
            throw new EntryNotFoundException(String.format("Unknown organization: %s", address));
        }
        return result;
    }

    private static void add(Organization organization) {
        map.put(organization.getAddress().toLowerCase(), organization);
        logger.info(String.format("Added to OrganizationDictionary: %s", organization));
    }

}
