package find.cf.community.dto;

import find.cf.community.model.Tags;
import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private Long id;
    private String category;
    private List<Tags> tags;
}
