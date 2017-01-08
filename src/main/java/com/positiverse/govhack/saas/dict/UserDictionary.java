package com.positiverse.govhack.saas.dict;

import com.positiverse.govhack.saas.model.User;
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
public class UserDictionary {
    private final static Logger logger = LoggerFactory.getLogger(ConsentTypesDictionary.class);

    private static Map<String, User> map = new HashMap<>();

    static {
        add(new User("0x95a074c547bc45ecbe98280cac66fd67ea4da743", "John Doe"));
        add(new User("0x711b74c63d99f198372e20961ba03e5e4d04eaa0", "Alicia Horn"));
        add(new User("0x4f6314521d1244f5ffdb9845aa4f1ffd7f6e6f54", "Stephen Blade"));
        add(new User("0x1f23a888ead35dc6f165cc175e213b8d111788b5", "Francis Grass"));
        add(new User("0x70ecb7bbc2835f7f12881a1a869a939034b9c528", "Monica Loren"));
    }

    public static Collection<User> get() {
        List<User> result = new ArrayList<>(map.values());
        Collections.sort(result);
        return result;
    }

    public static User get(String address) throws EntryNotFoundException {
        User result = map.get(address.toLowerCase());
        if (result == null) {
            throw new EntryNotFoundException(String.format("Unknown user: %s", address));
        }
        return result;
    }

    private static void add(User user) {
        map.put(user.getAddress().toLowerCase(), user);
        logger.info(String.format("Added to UserDictionary: %s", user));
    }

}
