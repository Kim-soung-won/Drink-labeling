package me.firstSpring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.dto.Drink.AddDrinkRequest;
import me.firstSpring.dto.Drink.UpdateDrinkRequest;
import me.firstSpring.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class DrinkService {
    private final DrinkRepository drinkRepository;

    @Transactional
    public Drink save(AddDrinkRequest request){ //블로그 글을 추가하는 메서드
        return drinkRepository.save(request.toEntity());
    } // 글 작성

    public List<Drink> findAll(){
        return drinkRepository.findAll();
    } //모든 글 목록 리스트로 조회

    public Drink findById(long id) { //id로 글 조회
        return drinkRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        //예외 발생시 not found id 라는 메세지를 출력해줌
    }
    public Drink findByName(String name){
        return drinkRepository.findByName(name).
                orElseThrow(() -> new IllegalArgumentException("not found: " + name));
    }

    public void delete(long id){ //글 삭제
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found : " + id));
        drinkRepository.delete(drink);
    }

    @Transactional //트랜젝션
    public Drink update(long id, UpdateDrinkRequest request){
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        drink.update(request.getName(), request.getCal(), request.getCar(),request.getPro() , request.getFat(),
                request.getGro(), request.getOther());

        return drink;
    }

}
