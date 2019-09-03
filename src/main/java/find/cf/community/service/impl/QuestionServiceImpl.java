package find.cf.community.service.impl;

import find.cf.community.dto.PaginationDTO;
import find.cf.community.dto.QuestionDTO;
import find.cf.community.exception.CustomizeErrorCode;
import find.cf.community.exception.CustomizeException;
import find.cf.community.mapper.QuestionExtMapper;
import find.cf.community.mapper.QuestionMapper;
import find.cf.community.mapper.UserMapper;
import find.cf.community.model.Question;
import find.cf.community.model.QuestionExample;
import find.cf.community.model.User;
import find.cf.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 首页查找所有的问题
     * @return
     * @param page
     * @param size
     */
    @Override
    public PaginationDTO list(String search ,Integer page, Integer size) {

        if(StringUtils.isNoneBlank(search)){
            String[] split = StringUtils.split(search, " ");
            search = Arrays.stream(split).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage = null;

        Integer totalCount = (int)questionMapper.countByExample(null);

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

        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
           User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    @Override
    public PaginationDTO listByUserId(Long userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage = null;

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);

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

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);

        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 更新或创建问题
     * @param question
     */
    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else{
            //更新问题
            question.setGmtModified(question.getGmtCreate());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 累加阅读数量
     * @param id
     */
    @Override
    public void increamentView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.increamentView(question);
    }

    /**
     * 查找相关问题
     * @param questionDTO
     * @return
     */
    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(questionDTO.getTag(), "-");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        return questions.stream().map(q -> {
            QuestionDTO relativeQ = new QuestionDTO();
            BeanUtils.copyProperties(q, relativeQ);
            return relativeQ;
        }).collect(Collectors.toList());
    }
}
