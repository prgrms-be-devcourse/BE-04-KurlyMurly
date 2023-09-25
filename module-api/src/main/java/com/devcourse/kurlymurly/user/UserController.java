package com.devcourse.kurlymurly.user;

import com.devcourse.kurlymurly.module.user.service.UserService;
import com.devcourse.kurlymurly.web.common.KurlyResponse;
import com.devcourse.kurlymurly.web.dto.user.CheckEmail;
import com.devcourse.kurlymurly.web.dto.user.CheckId;
import com.devcourse.kurlymurly.web.dto.user.Join;
import com.devcourse.kurlymurly.web.dto.user.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "auth", description = "유저 API")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Tag(name = "auth")
    @Operation(description = "로그인 API", responses = {
            @ApiResponse(responseCode = "200", description = "로그인에 성공한 경우"),
            @ApiResponse(responseCode = "422", description = "잘못된 로그인 정보를 입력한 경우"),
    })
    @PostMapping("/login")
    @ResponseStatus(OK)
    public KurlyResponse<Login.Response> login(@RequestBody @Valid Login.Request request) {
        Login.Response response = userService.login(request.loginId(), request.password());

        return KurlyResponse.ok(response);
    }

    @Tag(name = "auth")
    @Operation(description = "회원 가입 API", responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입에 성공한 경우"),
            @ApiResponse(responseCode = "400", description = "변경할 비밀번호와 확인 비밀번호가 일치하지 않는 경우"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 아이디를 입력한 경우"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일을 입력한 경우")
    })
    @PostMapping("/signUp")
    @ResponseStatus(OK)
    public KurlyResponse<Void> join(@RequestBody @Valid Join.Request request) {
        userService.join(request);
        return KurlyResponse.noData();
    }

    @Tag(name = "auth")
    @Operation(description = "id 중복 체크 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 id의 중복검사를 진행"),
    })
    @PostMapping("/check-id")
    @ResponseStatus(OK)
    public KurlyResponse<Void> checkId(@RequestBody @Valid CheckId.Request request) {
        boolean result = userService.checkId(request.loginId());
        return KurlyResponse.ok(result);
    }

    @Tag(name = "auth")
    @Operation(description = "이메일 중복 체크 API", responses = {
            @ApiResponse(responseCode = "200", description = "해당 이메일에 대한 중복검사를 진행")
    })
    @PostMapping("/check-email")
    @ResponseStatus(OK)
    public KurlyResponse<Void> checkEmail(@RequestBody @Valid CheckEmail.Request request) {
        boolean result = userService.checkEmail(request.email());
        return KurlyResponse.ok(result);
    }
}
