package me.firstSpring.controller.Drink;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.domain.User;
import me.firstSpring.dto.Drink.AddDrinkRequest;
import me.firstSpring.dto.Drink.UpdateDrinkRequest;
import me.firstSpring.service.DrinkService;
import me.firstSpring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class DrinkApiController {
    private final DrinkService drinkService;
    private final UserService userService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/drink")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Drink> addArticle(@RequestBody AddDrinkRequest request, Authentication authentication){
        Drink savedDrink = drinkService.save(request);
        System.out.println("POST : "+ authentication);
        System.out.println("Principal : "+authentication.getPrincipal());
        System.out.println("getName() : "+authentication.getName());
        User user = userService.findByEmail(authentication.getName());
        System.out.println(user.getEmail());
        System.out.println("role : "+ user.getRole());

        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDrink);
    }


    @DeleteMapping("/api/drink/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        drinkService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/drink/{id}")
    public ResponseEntity<Drink> updateDrink(@PathVariable long id,
                                                 @RequestBody UpdateDrinkRequest request){
        Drink updateDrink = drinkService.update(id,request);

        return ResponseEntity.ok().body(updateDrink);
    }
}
