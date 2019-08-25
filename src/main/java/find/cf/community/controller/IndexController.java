package find.cf.community.controller;


import find.cf.community.dto.PaginationDTO;
import find.cf.community.mapper.UserMapper;
import find.cf.community.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionServiceImpl questionServiceImp;

    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "10") Integer size){
        PaginationDTO pagination = questionServiceImp.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
