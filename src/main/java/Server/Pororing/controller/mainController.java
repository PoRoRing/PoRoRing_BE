package Server.Pororing.controller;

import Server.Pororing.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class mainController {

    @Operation(summary = "Test API")
    @GetMapping("/exception")
    public ApiResponse<String> healthCheck (){
        return ApiResponse.onSuccess("Server is running");
    }
}
