package find.cf.community.mapper;

import find.cf.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    /**
     * 自增阅读数
     * @param record
     * @return
     */
   int increamentView(Question record);

    /**
     * 增加提问得评论数量
     * @param record
     * @return
     */
   int increamentCommentCount(Question record);

    /**
     * 模糊查询
     * @param question
     * @return
     */
    List<Question> selectRelated(Question question);

}