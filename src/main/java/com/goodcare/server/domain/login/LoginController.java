package com.goodcare.server.domain.login;

import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Tag(name ="login", description = "로그인 API")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/")
    @Operation(
            summary = "로그인 API",
            description = "로그인 api입니다."
    )
    public ApiResponse<?> doLogin(
            @RequestBody LoginDTO loginDTO
    ){
        return loginService.login(loginDTO) ?
                ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), true) :
                ApiResponse.onFailure(Status.INTERNAL_SERVER_ERROR.getCode(), Status.INTERNAL_SERVER_ERROR.getMessage(), false);
    }
}
