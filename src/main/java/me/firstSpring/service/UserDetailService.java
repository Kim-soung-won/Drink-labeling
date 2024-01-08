package me.firstSpring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;
import me.firstSpring.dto.UserDetailBasic.AddUserDetailRequest;
import me.firstSpring.dto.UserDetailBasic.UpdateUserDetailRequest;
import me.firstSpring.repository.UserDetailBasicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Service
public class UserDetailService{
    private final UserDetailBasicRepository userDetailBasicRepository;
    private final UserService userService;

    @Transactional
    public UserDetailBasic save(AddUserDetailRequest request, String email){
        return userDetailBasicRepository.save(UserDetailBasic.builder()
                .name(request.getName())
                .birth(request.getBirth())
                .sex(request.getSex())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .cardNum(request.getCardNum())
                .user_id(userService.findByEmail(email))
                .build());
    }
    @Transactional
    public UserDetailBasic saveFirst(AddUserDetailRequest request){
        System.out.println("user Service");
        return userDetailBasicRepository.save(request.toEntity(userService.findById(request.getUser_id())));
    }


    @Transactional
    public UserDetailBasic update(UpdateUserDetailRequest request, String email){
        User user = userService.findByEmail(email);
        UserDetailBasic userDetail = userDetailBasicRepository.findByUser(user);
        userDetail.update(request.getPhoneNumber(),request.getEmail(), request.getAddress(), request.getCardNum());

        return userDetail;
    }
    public UserDetailBasic findByUser(User user) {
        return userDetailBasicRepository.findByUser(user);
    }

    public void delete(long id){
        UserDetailBasic user = userDetailBasicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        userDetailBasicRepository.delete(user);
    }

}
