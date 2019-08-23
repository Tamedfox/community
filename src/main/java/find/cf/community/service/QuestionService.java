package find.cf.community.service;

import find.cf.community.dto.PaginationDTO;
import find.cf.community.dto.QuestionDTO;
import find.cf.community.model.Question;

public interface QuestionService {

    /**
     * 首页查找所有的问题
     * @return
     * @param page
     * @param size
     */
    PaginationDTO list(Integer page, Integer size);


    /**
     * 查找当前用户的问题
     * @param id
     * @param page
     * @param size
     * @return
     */
    PaginationDTO listByUserId(Integer id, Integer page, Integer size);

    /**
     *根据问题主键id查找问题
     * @param id
     * @return
     */
    QuestionDTO getById(Integer id);

    /**
     * 更新或新建方法
     * @param question
     */
    void createOrUpdate(Question question);
}
