package me.firstSpring.controller.Drink;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UploadViewController {
    @GetMapping("/upload")
    public String getUploadPage(){

        return "Main_page";
    }
    @GetMapping("/코카콜라")
    public String getCokePage(){
        return "Coke_page";
    }

    @GetMapping("/펩시")
    public String getPepsiPage(){
        return "Coke_page";
    }
    @GetMapping("/사이다")
    public String getSodaPage(){
        return "Coke_page";
    }
    @GetMapping("/가격")
    public String getPricePage(){
        return "Coke_price_page";
    }
    @GetMapping("/order")
    public String getOrderPage(){
        return "testPayment";
    }
}
