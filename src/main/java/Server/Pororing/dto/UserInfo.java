package Server.Pororing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int age;
    private String height;
    private String weight;
    private String job;
    private List<String> healthConditions;
    private List<String> allergies;
    private String etc;

}