package find.cf.community.mapper;

import find.cf.community.model.Question;

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

}