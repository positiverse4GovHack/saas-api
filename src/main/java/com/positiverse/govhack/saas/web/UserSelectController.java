package com.positiverse.govhack.saas.web;

import com.positiverse.govhack.saas.dict.UserDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserSelectController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/userSelect")
    public String userSelect(Model model) {
        model.addAttribute("users", UserDictionary.get());
        return "userSelect";
    }

}
