package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name ="age", nullable = false)
    private Long age;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "tall")
    private Long tall;


    @Builder
    public Inform(Long age, String name, String comment, Long weight, Long tall) {
        this.age = age;
        this.name = name;
        this.comment = comment;
        this.weight = weight;
        this.tall = tall;
    }

    public void update(String comment, Long weight, Long tall) {
        this.weight = weight;
        this.tall = tall;
        this.comment = comment;
    }


}
