package find.cf.community.service;

import find.cf.community.dto.NotificationDTO;
import find.cf.community.dto.PaginationDTO;
import find.cf.community.model.User;

public interface NotificationService {

    /**
     * 查找当前用户的所有消息通知
     * @param id
     * @param page
     * @param size
     * @return
     */
    PaginationDTO list(Long id, Integer page, Integer size);

    /**
     * 查找
     * @return
     */
    Long unreadCount(Long id);

    /**
     * 阅读通知，消息数累-
     * @param id
     * @param user
     * @return
     */
    NotificationDTO read(Long id, User user);
}
