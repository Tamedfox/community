package find.cf.community.controller;

import find.cf.community.dto.NotificationDTO;
import find.cf.community.model.User;
import find.cf.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @RequestMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable("id") Long id){

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            return "redirec:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if(notificationDTO != null){
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }

    }

}
