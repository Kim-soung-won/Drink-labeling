package me.firstSpring.service;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.Inform;
import me.firstSpring.dto.Inform.AddInformRequest;
import me.firstSpring.repository.UserInformRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InformService {
    private final UserInformRepository informRepository;

    public Inform save(AddInformRequest request){
        return informRepository.save(request.toEntity());
    }
}
