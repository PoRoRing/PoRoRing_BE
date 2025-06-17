package Server.Pororing.apiPayload;


import Server.Pororing.apiPayload.code.BaseCode;
import Server.Pororing.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess","code","message","result"}) // JSON으로 직렬화를 할 때 필드의 순서를 지정하는데 사용
public class ApiResponse<T> {

    @JsonProperty("isSuccess") // 이름을 명확하게 지정
    private final Boolean isSuccess;
    private final String code; // 응답코드
    private final String message; // 메시지
    @JsonInclude(JsonInclude.Include.NON_NULL) // result 필드가 null일 경우 JSON 응답에 포함하지 않도록 설절
    private T result; // null이거나 나중에 값이 바뀔 수 있음

    // 성공한 경우 응답 생성

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result){
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}
