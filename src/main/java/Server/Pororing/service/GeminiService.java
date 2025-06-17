package Server.Pororing.service;


import Server.Pororing.apiPayload.code.status.ErrorStatus;
import Server.Pororing.apiPayload.exception.GeneralException;
import Server.Pororing.dto.GeminiReqDto;
import Server.Pororing.dto.GeminiResDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    public String geminaiTest(String preResult) {

        String geminiURL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="
                + apiKey;

        StringBuilder requestTextBuilder = new StringBuilder();
        requestTextBuilder.append(preResult);
        String requestText = requestTextBuilder.toString();

        GeminiReqDto request = new GeminiReqDto();
        request.createGeminiReqDto(requestText);
        String description = "";
        RestTemplate restTemplate = new RestTemplate();
        try {
            GeminiResDto response = restTemplate.postForObject(geminiURL, request, GeminiResDto.class);
            description = response.getCandidates().get(0).getContent().getParts().get(0).getText();
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.GEMINI_NOT_WORK);
        }
        return description;
    }
}