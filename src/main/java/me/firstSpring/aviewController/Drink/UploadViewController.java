package me.firstSpring.aviewController.Drink;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.dto.Drink.DrinkListViewResponse;
import me.firstSpring.dto.Drink.DrinkViewResponse;
import me.firstSpring.service.DrinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UploadViewController {
    private final DrinkService drinkService;
    @GetMapping("/upload")
    public String getUploadPage(Model model){
        List<DrinkListViewResponse> drinks = drinkService.findAll().stream()
                .map(DrinkListViewResponse::new)
                .toList();
        model.addAttribute("drink",drinks);
        return "Main_page";
    }
    @GetMapping("/data/{name}")
    public String getPage(@PathVariable String name, Model model){
        Drink drink = drinkService.findByName(name);
        model.addAttribute("drink",new DrinkViewResponse(drink));
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
