package me.firstSpring.apiController.User;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;
import me.firstSpring.dto.UserDetailBasic.AddUserDetailRequest;
import me.firstSpring.dto.UserDetailBasic.UpdateUserDetailRequest;
import me.firstSpring.dto.UserDetailBasic.UserDetailViewResponse;
import me.firstSpring.service.UserDetailService;
import me.firstSpring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserDetailApiController {
    private final UserDetailService userDetailService;
    private final UserService userService;
    @PostMapping("/api/userData/details")
    @Transactional
    public ResponseEntity<UserDetailBasic> saveUserDetail(@RequestBody @Valid AddUserDetailRequest request, Authentication authentication){
        UserDetailBasic userDetailBasic = userDetailService.save(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailBasic);
    }
    @PostMapping("/api/first/userData/details")
    @Transactional
    public ResponseEntity<UserDetailBasic> saveFirstUserDetail(@RequestBody @Valid AddUserDetailRequest request){
        UserDetailBasic userDetailBasic = userDetailService.saveFirst(request);
        System.out.println("이름 : " + request.getName());
        System.out.println("전화번호 : "+request.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailBasic);
    }
    @GetMapping("/api/userDetailPage")
    public ResponseEntity<UserDetailViewResponse> getUserDetail(Authentication authentication){
        User user = userService.findByEmail(authentication.getName());
        System.out.println("getName() : "+authentication);
        UserDetailBasic userDetailBasic = userDetailService.findByUser(user);
        System.out.println("detail : " + userDetailBasic);

        return ResponseEntity.ok(new UserDetailViewResponse(userDetailBasic));
    }
    @PutMapping("/api/userData/details")
    @Transactional
    public ResponseEntity<UserDetailBasic> updateUserDetail(@RequestBody UpdateUserDetailRequest request,
                                                            Authentication authentication){
        System.out.println("authentication"+authentication);
        System.out.println("class"+authentication.getClass());
        UserDetailBasic updateUserDetailBasic = userDetailService.update(request, authentication.getName());

        return ResponseEntity.ok().body(updateUserDetailBasic);
    }
//    @DeleteMapping("/api/userData/details")
//    public ResponseEntity<Void> deleteUserDetail(Authentication authentication){
//    }
}
