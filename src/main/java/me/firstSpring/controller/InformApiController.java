package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Inform;
import me.firstSpring.dto.Inform.AddInformRequest;
import me.firstSpring.service.InformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InformApiController {
    private final InformService informService;

    @PostMapping("/api/inform")
    public ResponseEntity<Inform> addInform(@RequestBody AddInformRequest request){
        Inform savedInform = informService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInform);
    }
}
