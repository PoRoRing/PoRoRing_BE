package Server.Pororing.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptRequest {
    private String ocrResult;
    private String category;
    private List<String> infoOptions;
    private UserInfo userInfo;
}