package com.matrix.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * author         : Jason Lee
 * date           : 2023-08-10
 * description    :
 */
@Controller
public class MainController {

    @GetMapping(value = {"", "/"})
    public String root() {
        return "forward:/articles";
    }
}
