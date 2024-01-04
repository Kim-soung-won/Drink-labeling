package me.firstSpring.apiController.Price;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Price;
import me.firstSpring.dto.Price.AddPriceRequest;
import me.firstSpring.dto.Price.UpdatePriceRequest;
import me.firstSpring.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class PriceApiController {
    private final PriceService priceService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/price")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Price> addPrice(@RequestBody AddPriceRequest request){
        Price savedDrink = priceService.save(request);

        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDrink);
    }


    @DeleteMapping("/api/price/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id){
        priceService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/price/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable long id,
                                               @RequestBody UpdatePriceRequest request){
        Price updatePrice = priceService.update(id,request);

        return ResponseEntity.ok().body(updatePrice);
    }
}
