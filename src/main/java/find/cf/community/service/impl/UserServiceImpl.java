package find.cf.community.service.impl;

import find.cf.community.mapper.UserMapper;
import find.cf.community.model.User;
import find.cf.community.model.UserExample;
import find.cf.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新或新建用户
     * @param user
     */
    @Override
    public void createOrUpdate(User user) {
        //查找数据库的user
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        //判断查找处的结果是否存在
        if(users.size() == 0){
            //不存在,新建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //存在,更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }

    /**
     * 根据token查找用户
     * @param token
     * @return
     */
    @Override
    public User findByToken(String token) {
        UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() != 0){
            return users.get(0);
        }
        return null;
    }
}
