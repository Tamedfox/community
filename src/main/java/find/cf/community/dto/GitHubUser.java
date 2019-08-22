package find.cf.community.dto;

import lombok.Data;

@Data
public class GitHubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
