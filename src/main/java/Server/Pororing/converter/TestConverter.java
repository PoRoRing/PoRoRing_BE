package Server.Pororing.converter;


import Server.Pororing.dto.TestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class TestConverter {

    public static TestResponse.TempTestDTO toTempTestDTO(){
        return TestResponse.TempTestDTO.builder()
                .testString("테스트입니다.")
                .build();
    }

    public static TestResponse.TempExceptionDTO toTempExceptionDTO(Integer flag){
        return TestResponse.TempExceptionDTO.builder()
                .flag(flag)
                .build();
    }

    public static TestResponse.ocrResultDTO toOcrResultDTO(String result){
        return TestResponse.ocrResultDTO.builder()
                .ocrResult(result)
                .build();
    }

    public static TestResponse.geminiResultDTO toGeminiResultDTO(String result) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> parsed = objectMapper.readValue(result, Map.class);
            return TestResponse.geminiResultDTO.builder()
                    .name(parsed.get("title"))
                    .geminiResult(parsed.get("result"))
                    .build();
        } catch (Exception e) {
            return TestResponse.geminiResultDTO.builder()
                    .name("파싱 실패")
                    .geminiResult(result)
                    .build();
        }
    }
}

