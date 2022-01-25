package ir.urmia.cloudservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/login-page")
    public String loginPage() {
        return "index";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}
