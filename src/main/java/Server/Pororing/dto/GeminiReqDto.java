package Server.Pororing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiReqDto {

    private List<Content> contents;

    public void createGeminiReqDto(String text){
        this.contents = new ArrayList<>();
        Content content = new Content(text);
        contents.add(content);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {

        private List<Part> parts;

        public Content(String text) {
            parts = new ArrayList<>();
            parts.add(new Part(text));
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Part {
            private String text;
        }
    }
}

