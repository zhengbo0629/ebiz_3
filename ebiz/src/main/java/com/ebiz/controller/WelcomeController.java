package com.ebiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ebiz.common.ConstantPath.PATH_INDEX;

@Controller
public class WelcomeController {
    @RequestMapping("/")
    public String index() {
        return PATH_INDEX;
    }
}
