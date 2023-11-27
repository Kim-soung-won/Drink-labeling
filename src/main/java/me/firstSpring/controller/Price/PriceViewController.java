package me.firstSpring.controller.Price;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.domain.Price;
import me.firstSpring.dto.Drink.DrinkListViewResponse;
import me.firstSpring.dto.Drink.DrinkViewResponse;
import me.firstSpring.dto.Price.PriceListViewResponse;
import me.firstSpring.dto.Price.PriceViewResponse;
import me.firstSpring.service.PriceService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PriceViewController {
    private final PriceService priceService;

    @GetMapping("/priceList")
    public String getPriceList(Model model){ //Model : HTML쪽으로 값을 넘겨주는 객체
        List<PriceListViewResponse> prices = priceService.findAll().stream()
                .map(PriceListViewResponse::new)
                .toList();
        model.addAttribute("price",prices);

        return "Price/priceList";
    }
    @GetMapping("/dataPrice/{name}")
    public String getPrice(Model model, @PathVariable String name){ //Model : HTML쪽으로 값을 넘겨주는 객체
        List<PriceListViewResponse> prices = priceService.findByBrand(name).stream()
                .map(PriceListViewResponse::new)
                .toList();
        model.addAttribute("price",prices);

        return "Price/price_ui";
    }

    @GetMapping("/price/{id}")
    public String getPrice(@PathVariable Long id, Model model){
        Price price = priceService.findById(id);
        model.addAttribute("price",new PriceViewResponse(price));
        return "Price/priceData";
    }

    @GetMapping("/new-price")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String newDrink(@RequestParam(required = false) Long id, Model model){
        if(id==null){ //id가 없으면 생성
            model.addAttribute("price", new PriceViewResponse());
        } else{ //있으면 수정
            Price price = priceService.findById(id);
            model.addAttribute("price", new PriceViewResponse(price));
        }
        return "Price/newPrice";
    }

    @GetMapping("/order/drink/{id}")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String orderDrink(@PathVariable Long id, Model model){
        Price price = priceService.findById(id);
        model.addAttribute("price", new PriceViewResponse(price));
        return "Price/privateInform";
    }
}
