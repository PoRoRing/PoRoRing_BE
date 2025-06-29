package Server.Pororing.service;

import Server.Pororing.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptService {

    private static final Map<String, String> CATEGORY_MAP = Map.of(
            "식품", "과자, 음료 라벨, 즉석식품 용기 등의 식품 포장지",
            "의약품", "일반 상비약 제품의 포장 및 설명서",
            "뷰티 또는 미용 제품", "피부, 모발, 얼굴에 사용하는 일반 미용제품",
            "문서", "안내문, 고지서, 공지사항 등의 문서",
            "기타", "일반 분류에 속하지 않는 기타 이미지"
    );

    private static final Map<String, String> INFO_MAP = Map.of(
            "이게 무슨 내용인가요?", "전체 내용을 쉽게 요약해줘.",
            "중요한 부분만 알려줘요", "중요하거나 핵심적인 정보만 추려줘.",
            "주의해야 할 게 있나요?", "주의사항이나 조심해야할 것이 있다면 알려줘.",
            "내가 먹어도/써도 괜찮은가요?", "건강 상태나 알레르기를 고려해서 먹거나 써도 되는지 알려줘.",
            "언제, 어떻게 사용하나요?", "사용 방법이나 복용법이 있다면 알려줘.",
            "전체 내용을 알고 싶어요", "전체 내용을 그대로 알려줘."
    );

    public String generatePrompt(String category, List<String> infoOptions, UserInfo userInfo, String ocrResult) {
        StringBuilder prompt = new StringBuilder();

        String categoryDesc = CATEGORY_MAP.getOrDefault(category, "");

        prompt.append(ocrResult);
        prompt.append("\n \n");
        prompt.append("위 텍스트는 ").append(category).append(" 관련 이미지의 OCR 텍스트 검출 결과야.");
        if (!categoryDesc.isEmpty()) {
            prompt.append(" (").append(categoryDesc).append(")");
        }

        prompt.append("\n\n사용자의 정보는 이래.\n");
        prompt.append("나이: ").append(userInfo.getAge()).append("세\n");
        prompt.append("키와 몸무게는 ").append(userInfo.getHeight()).append(", ").append(userInfo.getWeight()).append("야.\n");
        prompt.append("직업은 ").append(userInfo.getJob()).append("이야.\n");

        if (!userInfo.getHealthConditions().isEmpty()) {
            prompt.append("건강상 유의할 점은 ").append(String.join(", ", userInfo.getHealthConditions())).append("이 있어.\n");
        }

        if (!userInfo.getAllergies().isEmpty()) {
            prompt.append("알레르기는 ").append(String.join(", ", userInfo.getAllergies())).append("가 있어.\n");
        }

        if (userInfo.getEtc() != null && !userInfo.getEtc().isBlank()) {
            prompt.append("그 외에 참고로 ").append(userInfo.getEtc()).append(" 이야.\n");
        }

        if (infoOptions.size() == 1 && infoOptions.contains("전체 내용을 그대로 알려줘.")) {
            prompt.append("-이미지의 종류가 식품, 의약품, 뷰티 및 미용 제품이라면 상품명, 문서라면 문서 제목을 만들어서 알려줘.\n");
            prompt.append("-이미지에서 인식된 전체 내용을 가능한 한 빠짐없이 설명해 줘. 글자 수 제한은 없으며, 이모티콘, 마크업, 불필요한 꾸밈 표현은 절대 사용하지 말고, 일반 텍스트만 사용해.");
        } else {
            prompt.append("\n아래 내용들을 알고 싶어.\n");
            for (String option : infoOptions) {
                prompt.append("-이미지의 종류가 식품, 의약품, 뷰티 및 미용 제품이라면 상품명, 문서라면 문서 제목을 만들어서 알려줘.\n");
                prompt.append("- ").append(INFO_MAP.getOrDefault(option, option)).append("\n");
            }

            prompt.append("\n응답은 반드시 다음 형식의 순수 JSON 문자열이어야 해: {\"title\": \"...\", \"result\": \"...\"}.");
            prompt.append("\n'title'은 이미지에서 인식한 대상의 이름이나 제목을 의미해. 식품이나 의약품, 뷰티 제품의 경우 상품명을, 문서의 경우 문서 제목을 간결하게 작성해 줘.");
            prompt.append("\n'result'는 사용자가 요청한 정보를 바탕으로 존댓말로 작성한 설명이야. 전체 분량은 1000자 이내로 제한하고, 소제목이나 문단 구분 없이 하나의 줄글 형태로 이어서 작성해 줘.");
            prompt.append("\n절대로 마크다운 코드 블록(예: ```json), 작은 따옴표('), 줄바꿈 문자(\\n), 이스케이프 문자(\\)를 사용하지 마.");
            prompt.append("\nJSON은 하나의 객체로만 구성해야 하고, 앞뒤 설명 없이 JSON 객체만 응답해 줘.");
            prompt.append("\n질문에 직접적으로 관련된 내용만 포함해 줘. 배경 설명이나 일반적인 정보, 서론적인 문장은 생략해 줘.");
            prompt.append("\n'안녕하세요', '~에 대해 말씀드리겠습니다', '~를 고려할 때' 같은 형식적인 서론 문장은 절대 쓰지 마.");
            prompt.append("\n설명은 항상 핵심 내용부터 바로 시작해 줘. 사용자의 상태 요약도 생략하고, 질문과 직접 관련된 정보만 포함해 줘.");
            prompt.append("\n추가적인 배경 설명, 인사말, 문맥 소개 없이 바로 설명을 시작해.");
            prompt.append("\n이모티콘, 마크업, 불필요한 꾸밈 표현은 절대 사용하지 말고, 일반 텍스트만으로 작성해 줘.");
        }
        return prompt.toString().trim();
    }
}