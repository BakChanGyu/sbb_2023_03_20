package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/sbb")
    @ResponseBody
    public String home() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }
}