package find.cf.community.controller;

import find.cf.community.dto.PaginationDTO;
import find.cf.community.mapper.UserMapper;
import find.cf.community.model.User;
import find.cf.community.service.NotificationService;
import find.cf.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "10") Integer size,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            return "index";
        }

        //登录验证
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO pagination = questionService.listByUserId(user.getId(),page,size);
            model.addAttribute("pagination",pagination);
        }else if("replies".equals(action)){
            PaginationDTO pagination = notificationService.list(user.getId(),page,size);
            model.addAttribute("pagination",pagination);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
        }



        return "profile";
}
}
