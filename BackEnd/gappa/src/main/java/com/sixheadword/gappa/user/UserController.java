package com.sixheadword.gappa.user;

import com.sixheadword.gappa.user.request.CheckPhoneRequestDto;
import com.sixheadword.gappa.user.request.CheckPwRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // API 1. 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        return userService.login(request);
    }

    // API 2. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        return userService.setUserInfo(request);
    }

    // API 3. 회원정보 수정
    @PutMapping
    public ResponseEntity<?> modifyUserInfo(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.modifyUserInfo(request, Long.parseLong(authentication.getName()));
    }

    // API 4. 회원탈퇴
    @DeleteMapping
    public ResponseEntity<?> deleteUserInfo(Authentication authentication) {
        return userService.deleteUserInfo(Long.parseLong(authentication.getName()));
    }

    // API 5. 신용점수 조회
    @GetMapping("/credit")
    public ResponseEntity<?> getUserCreditScore(Authentication authentication) {
        return userService.getUserCreditScore(Long.parseLong(authentication.getName()));
    }

    // API 6. 아이디 중복확인
    @PostMapping("/checkid")
    public ResponseEntity<?> checkIdDuplication(@RequestBody Map<String, String> request) {
        return userService.checkIdDuplication(request);
    }

    // API 7. 전화번호 중복확인
    @PostMapping("/checkphone")
    public ResponseEntity<?> checkPhoneDuplication(@RequestBody CheckPhoneRequestDto checkPhoneRequestDto) {
        return ResponseEntity.ok(userService.checkPhoneDuplication(checkPhoneRequestDto));
    }

    // API 8. 간편 비밀번호 설정
    @PostMapping("/pin/set")
    public ResponseEntity<?> setPinPassword(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.setPinPassword(request, authentication.getName());
    }

    // API 9. 간편 비밀번호 확인
    @PostMapping("/pin/check")
    public ResponseEntity<?> checkPinPassword(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.checkPinPassword(request, authentication.getName());
    }

    // API 10. 아이디 찾기
    @PostMapping("/findid")
    public ResponseEntity<?> findUserId(@RequestBody Map<String, String> request) {
        return userService.findUserId(request);
    }

    // API 11. 유저 조회
    @GetMapping("/{userSeq}")
    public ResponseEntity<?> searchUserInfo(@PathVariable("userSeq") Long userSeq, Authentication authentication) {
        return userService.searchUserInfo(userSeq, Long.parseLong(authentication.getName()));
    }

    // API 12. 비밀번호 재설정
    @PutMapping("/setpw")
    public ResponseEntity<?> updateUserPw(@RequestBody Map<String, String> request) {
        return userService.updateUserPw(request);
    }

    // API 13. 알림 조회
    @GetMapping("/alarm")
    public ResponseEntity<?> selectUserAlarm(Authentication authentication) {
        return userService.selectUserAlarm(Long.parseLong(authentication.getName()));
    }

    // API 14. 휴대폰 인증번호 전송
    @PostMapping("/phone/send")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request){
        return userService.sendVerificationCode(request);
    }

    // API 15. 휴대폰 인증번호 확인
    @PostMapping("/phone/check")
    public ResponseEntity<?> checkVerificationCode(@RequestBody Map<String, String> request){
        return userService.checkVerificationCode(request);
    }

    // API 16. 비밀번호 찾기 인증
    @PostMapping("/findpw")
    public ResponseEntity<?> checkVerificationPw(@RequestBody CheckPwRequestDto checkPwRequestDto){
        return userService.checkVerificationPw(checkPwRequestDto);
    }

    // API 17. 간편 비밀번호 유효성 검증
    @PostMapping("/pin/validate")
    public ResponseEntity<?> checkValidatePinPassword(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.checkValidatePinPassword(request, Long.parseLong(authentication.getName()));
    }

    // API 18. 알림 확인
    @PutMapping("/alarm/single")
    public ResponseEntity<?> checkSingleAlarm(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.checkSingleAlarm(request, Long.parseLong(authentication.getName()));

    }

    // API 19. 알림 전체 확인
    @PutMapping("/alarm/all")
    public ResponseEntity<?> checkAllAlarm(Authentication authentication) {
        return userService.checkAllAlarm(Long.parseLong(authentication.getName()));
    }
}
