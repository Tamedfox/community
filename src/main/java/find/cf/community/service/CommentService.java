package find.cf.community.service;

import find.cf.community.dto.CommentDTO;
import find.cf.community.model.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 插入数据
     * @param comment
     */
    void insert(Comment comment);

    /**
     * 根据问题id下的所有评论
     * @param id
     * @return
     */
    List<CommentDTO> getByQuestionId(Long id);
}
