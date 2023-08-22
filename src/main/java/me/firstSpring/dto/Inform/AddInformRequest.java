package me.firstSpring.dto.Inform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Inform;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddInformRequest {
    private String name;
    private Long age;
    private Long weight;
    private Long tall;

    public Inform toEntity(){
        return Inform.builder()
                .name(name)
                .age(age)
                .weight(weight)
                .tall(tall)
                .build();
    }

}
