package Server.Pororing.converter;


import Server.Pororing.dto.TestResponse;

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

    public static TestResponse.geminiResultDTO toGeminiResultDTO(String result){
        return TestResponse.geminiResultDTO.builder()
                .geminiResult(result)
                .build();
    }
}

