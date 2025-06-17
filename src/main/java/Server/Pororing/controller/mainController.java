package Server.Pororing.controller;

import Server.Pororing.apiPayload.ApiResponse;
import Server.Pororing.converter.TestConverter;
import Server.Pororing.dto.PromptRequest;
import Server.Pororing.dto.TestResponse;
import Server.Pororing.dto.promptRequest2;
import Server.Pororing.service.GeminiService;
import Server.Pororing.service.GoogleVisionOCRService;
import Server.Pororing.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class mainController {

    private final PromptService promptService;
    private final GoogleVisionOCRService googleVisionOCRService;
    private final GeminiService geminiService;

    @Operation(summary = "서버 healthCheck API")
    @GetMapping("/exception")
    public ApiResponse<String> healthCheck (){
        return ApiResponse.onSuccess("Server is running");
    }

    @Operation(summary = "프롬프트 생성 테스트 API")
    @PostMapping("/prompt") // 프롬프트 생성 예시
    public String getPrompt(@RequestBody PromptRequest request) {
        return promptService.generatePrompt(
                request.getCategory(),
                request.getInfoOptions(),
                request.getUserInfo(),
                request.getOcrResult()
        );
    }

    @Operation(summary = "전체 로직 테스트")
    @PostMapping(value = "/gemini", consumes = "multipart/form-data")
    public ApiResponse<TestResponse.geminiResultDTO> extracasdasdtText (@RequestPart(value = "file") MultipartFile file,
                                                                        @RequestPart(value="preInformation") promptRequest2 request)throws IOException {
        String ocrResult = googleVisionOCRService.detectText(file); // 텍스트 검출
        String promptResult = promptService.generatePrompt(
                request.getCategory(),
                request.getInfoOptions(),
                request.getUserInfo(),
                ocrResult
        ); // 프롬프트 생성
        String geminiResult = geminiService.geminaiTest(promptResult); // AI 결과
        TestResponse.geminiResultDTO result = TestConverter.toGeminiResultDTO(geminiResult);
        return ApiResponse.onSuccess(result);
    }
}
