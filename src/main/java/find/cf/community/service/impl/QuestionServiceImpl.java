package find.cf.community.service.impl;

import find.cf.community.dto.PaginationDTO;
import find.cf.community.dto.QuestionDTO;
import find.cf.community.mapper.QuestionMapper;
import find.cf.community.mapper.UserMapper;
import find.cf.community.model.Question;
import find.cf.community.model.User;
import find.cf.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 首页查找所有的问题
     * @return
     * @param page
     * @param size
     */
    @Override
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage = null;

        Integer totalCount = questionMapper.count();

        if(totalCount % size == 0 ){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }


        if(page <1 ){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
           User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    @Override
    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage = null;

        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount % size == 0 ){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }


        if(page <1 ){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }
}
