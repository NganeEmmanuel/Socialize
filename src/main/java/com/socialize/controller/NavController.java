package com.socialize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class NavController {

    @GetMapping({"", "/sign-in"})
    public String getSignInPage(Model model) {
        // Add any model attributes if needed
        return "sign-in"; // This will resolve to signi.html template
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        // Add any model attributes if needed
        return "sign-up"; // This will resolve to signup.html template
    }

    @GetMapping("/feed")
    public String getFeed(Model model) {
        // Add any model attributes if needed
        return "feed"; // This will resolve to signup.html template
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        // Add any model attributes if needed
        return "profile"; // This will resolve to signup.html template
    }

    @GetMapping("/profile-edit")
    public String getEditProfile(Model model) {
        // Add any model attributes if needed
        return "profile-edit"; // This will resolve to signup.html template
    }

    @GetMapping("/account-setting")
    public String getAcountSetting(Model model) {
        // Add any model attributes if needed
        return "account-setting"; // This will resolve to signup.html template
    }

    @GetMapping("/privacy-setting")
    public String getPrivacySetting(Model model) {
        // Add any model attributes if needed
        return "privacy-setting"; // This will resolve to signup.html template
    }
}
