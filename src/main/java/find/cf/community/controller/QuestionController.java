package find.cf.community.controller;

import find.cf.community.dto.CommentDTO;
import find.cf.community.dto.QuestionDTO;
import find.cf.community.enums.CommentTypeEnum;
import find.cf.community.service.CommentService;
import find.cf.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //查看文章累加浏览数
        questionService.increamentView(id);
        //查询提问
        QuestionDTO questionDTO = questionService.getById(id);
        //查找右侧相关问题列表
        List<QuestionDTO> relatedList = questionService.selectRelated(questionDTO);

        //查询评论内容
        List<CommentDTO> comments = commentService.getByTargetId(questionDTO.getId(), CommentTypeEnum.QUESTION);

        model.addAttribute("relativedQuestion",relatedList );
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments", comments);
        return "question";

    }
}
