package com.positiverse.govhack.saas.web;

import com.positiverse.govhack.saas.dict.EntryNotFoundException;
import com.positiverse.govhack.saas.dict.OrganizationDictionary;
import com.positiverse.govhack.saas.logic.ConsentLogic;
import com.positiverse.govhack.saas.model.Consent;
import com.positiverse.govhack.saas.model.Organization;
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
public class OrganizationListController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ethereum.node.url}")
    private String ethereumNodeUrl;

    @Value("${smart.contract.address}")
    private String smartContractAddress;

    @RequestMapping("/organizationList")
    public String organizationList(@RequestParam(value="address", required=true) String address, Model model)
            throws IOException, EntryNotFoundException {
        Organization organization = OrganizationDictionary.get(address);
        model.addAttribute("organization", organization);
        List<Consent> consents = new ConsentLogic().getConsentsForDataController(
                ethereumNodeUrl, smartContractAddress, address);
        logger.info(String.format("organizationList(): %d consents read from ethereum", consents.size()));
        model.addAttribute("consents", consents);
        return "organizationList";
    }

}
