package find.cf.community.controller;

import find.cf.community.dto.QuestionDTO;
import find.cf.community.dto.TagDTO;
import find.cf.community.mapper.QuestionMapper;
import find.cf.community.mapper.UserMapper;
import find.cf.community.model.Question;
import find.cf.community.model.User;
import find.cf.community.service.QuestionService;
import find.cf.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @GetMapping("/publish")
    public String publish(Model model){
        List<TagDTO> allTags = tagService.findAllTags();
        model.addAttribute("tags",allTags);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false) Long id,
                            Model model,
                            HttpServletRequest request){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);


        if(title == null || title.equals("")){
            model.addAttribute("error","问题不能为空");
            return "publish";
        }
        if(description == null || description.equals("")){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tag == null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        if(!tagService.isValid(tag)){
            model.addAttribute("error","输入标签非法");
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
//        question.setViewCount(0);
//        question.setCommentCount(0);
//        question.setLikeCount(0);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id
                         ,Model model){
        List<TagDTO> allTags = tagService.findAllTags();
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags",allTags);
        return "publish";
    }

}
