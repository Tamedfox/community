package find.cf.community.service;

import find.cf.community.model.User;

public interface UserService {

    /**
     * 更新或创建用户
     * @param user
     */
    void createOrUpdate(User user);

    /**
     * 根据token查找用户
     * @param token
     * @return
     */
    User findByToken(String token);
}
