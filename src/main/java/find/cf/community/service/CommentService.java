package find.cf.community.service;

import find.cf.community.dto.CommentDTO;
import find.cf.community.enums.CommentTypeEnum;
import find.cf.community.model.Comment;
import find.cf.community.model.User;

import java.util.List;

public interface CommentService {

    /**
     * 插入数据
     * @param comment
     * @param commentator
     */
    void insert(Comment comment, User commentator);

    /**
     * 根据问题id下的所有评论
     * @param id
     * @param type
     * @return
     */
    List<CommentDTO> getByTargetId(Long id, CommentTypeEnum type);
}
