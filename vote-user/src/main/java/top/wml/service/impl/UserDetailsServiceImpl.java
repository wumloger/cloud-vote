//package top.wml.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import jakarta.annotation.Resource;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import top.wml.entity.LoginUser;
//import top.wml.entity.User;
//import top.wml.exception.BusinessException;
//import top.wml.mapper.UserMapper;
//import top.wml.service.UserService;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Resource
//    private UserMapper userMapper;
//
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, username);
//        User user = userMapper.selectOne(wrapper);
//        if(user == null){
//            throw new BusinessException("500","用户名不能为空");
//        }
//
//        return new LoginUser(user);
//    }
//}
