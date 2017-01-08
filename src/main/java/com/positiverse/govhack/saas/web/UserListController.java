package com.positiverse.govhack.saas.web;

import com.positiverse.govhack.saas.dict.EntryNotFoundException;
import com.positiverse.govhack.saas.model.User;
import com.positiverse.govhack.saas.dict.UserDictionary;
import com.positiverse.govhack.saas.logic.ConsentLogic;
import com.positiverse.govhack.saas.model.Consent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class UserListController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ethereum.node.url}")
    private String ethereumNodeUrl;

    @Value("${smart.contract.address}")
    private String smartContractAddress;

    @RequestMapping("/userList")
    public String userList(@RequestParam(value="address", required=true) String address, Model model)
            throws IOException, EntryNotFoundException {
        User user = UserDictionary.get(address);
        model.addAttribute("userName", user.getName());
        List<Consent> consents = new ConsentLogic().getConsentsForPersonalDataSubject(
                ethereumNodeUrl, smartContractAddress, address);
        logger.info(String.format("userList(): %d consents read from ethereum", consents.size()));
        model.addAttribute("consents", consents);
        return "userList";
    }

}
