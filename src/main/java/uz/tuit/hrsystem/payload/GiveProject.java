package uz.tuit.hrsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.hrsystem.entity.enums.ProjectLevel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiveProject {

    private String email;
    private String projectName;
    private ProjectLevel projectLevel;

}
