package com.socials.UserProfile.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "IMAGE-SERVICE")
public interface ImageServiceProxy {

    @GetMapping("/getPathByEmail")
    public String getImagePath(@RequestParam String email);
}
