package me.firstSpring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Price;
import me.firstSpring.dto.Drink.AddDrinkRequest;
import me.firstSpring.dto.Drink.UpdateDrinkRequest;
import me.firstSpring.dto.Price.AddPriceRequest;
import me.firstSpring.dto.Price.UpdatePriceRequest;
import me.firstSpring.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class PriceService {
    private final PriceRepository priceRepository;

    @Transactional
    public Price save(AddPriceRequest request){ //블로그 글을 추가하는 메서드
        return priceRepository.save(request.toEntity());
    } // 글 작성

    public List<Price> findAll(){
        return priceRepository.findAll();
    } //모든 글 목록 리스트로 조회

    public Price findById(long id) { //id로 글 조회
        return priceRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        //예외 발생시 not found id 라는 메세지를 출력해줌
    }
    public Price findByName(String name){
        return priceRepository.findByName(name).
                orElseThrow(() -> new IllegalArgumentException("not found: " + name));
    }

    public void delete(long id){ //글 삭제
        Price price = priceRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found : " + id));
        priceRepository.delete(price);
    }

    @Transactional //트랜젝션
    public Price update(long id, UpdatePriceRequest request){
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        price.update(request.getName(), request.getPrice());

        return price;
    }

}
