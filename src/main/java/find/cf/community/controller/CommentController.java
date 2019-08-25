package find.cf.community.controller;

import find.cf.community.dto.CommentCreateDTO;
import find.cf.community.dto.CommentDTO;
import find.cf.community.dto.ResultDTO;
import find.cf.community.enums.CommentTypeEnum;
import find.cf.community.exception.CustomizeErrorCode;
import find.cf.community.model.Comment;
import find.cf.community.model.User;
import find.cf.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 插入问题评论
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    public Object commentPost(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_CONTENT);
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO,comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment, user);

        //返回状态
        return ResultDTO.okOf();
    }


    /**
     * 查询二级评论
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.getByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
