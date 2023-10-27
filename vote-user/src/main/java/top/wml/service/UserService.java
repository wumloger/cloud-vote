package top.wml.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wml.entity.User;

public interface UserService extends IService<User> {
    public String login(User user);

    public boolean beCandidate(User user);

    public boolean register(User user);
}
