package find.cf.community.service.impl;

import find.cf.community.dto.CommentDTO;
import find.cf.community.enums.CommentTypeEnum;
import find.cf.community.exception.CustomizeErrorCode;
import find.cf.community.exception.CustomizeException;
import find.cf.community.mapper.CommentMapper;
import find.cf.community.mapper.QuestionExtMapper;
import find.cf.community.mapper.QuestionMapper;
import find.cf.community.mapper.UserMapper;
import find.cf.community.model.*;
import find.cf.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExits(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_WRONG);
        }

        //双重评论判断评论类型
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //要回复的父类评论
            Comment commentInDB = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(null == commentInDB){
                throw new CustomizeException(CustomizeErrorCode.NO_COMMENT);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw  new CustomizeException(CustomizeErrorCode.NO_QUESTION);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.increamentCommentCount(question);
        }
    }

    @Override
    public List<CommentDTO> getByQuestionId(Long id) {
        //获得问题下的所有评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);

        if(commentList.size() == 0){
            return new ArrayList<>();
        }
        //管道式处理评论数据,找出所有的评论人主键
        Set<Long> commentors = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentors);

        //查找所有的评论的用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        //将用户列表转为map集合
        Map<Long, User> usersMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //用户信息和评论匹配
        List<CommentDTO> list = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(usersMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return list;
    }
}
