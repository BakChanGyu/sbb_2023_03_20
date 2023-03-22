package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
public class HomeController {

    @GetMapping("/sbb")
    @ResponseBody
    public String home() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/sbb/question/list";
    }

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int answer = 0;
        int prevDigit = 0;
        int num = 666;
        while (true) {
            if (prevDigit % 10 == 6) {
                for (int i = 0; i < 10; i++) {
                    answer++;
                    num++;
                    if (answer == n) {
                        System.out.println(prevDigit + "" + num);
                        break;
                    }
                }
            }
            else if (prevDigit % 100 == 66) {
                for (int i = 0; i < 100; i++) {
                    answer++;
                    num++;
                    if (answer == n) {
                        System.out.println(prevDigit + "" + num);
                        break;
                    }
                }
            }
            else if (prevDigit % 1000 == 666) {
                for (int i = 0; i < 1000; i++) {
                    answer++;
                    num++;
                    if (answer == n) {
                        System.out.println(prevDigit + "" + num);
                        break;
                    }
                }
            }

            answer++;
            num++;
            prevDigit++;
        }
    }
}


