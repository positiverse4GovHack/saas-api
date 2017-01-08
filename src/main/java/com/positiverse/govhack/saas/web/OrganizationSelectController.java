package com.positiverse.govhack.saas.web;

import com.positiverse.govhack.saas.dict.OrganizationDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrganizationSelectController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/organizationSelect")
    public String organizationSelect(Model model) {
        model.addAttribute("organizations", OrganizationDictionary.get());
        return "organizationSelect";
    }

}
