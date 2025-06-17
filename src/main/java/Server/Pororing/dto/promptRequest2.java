package Server.Pororing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class promptRequest2 {
    private String category;
    private List<String> infoOptions;
    private UserInfo userInfo;
}
