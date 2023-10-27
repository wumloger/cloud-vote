package top.wml.service.impl;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import top.wml.entity.User;
import top.wml.exception.BusinessException;
import top.wml.feign.CandidateService;
import top.wml.mapper.UserMapper;
import top.wml.resp.CommonResp;
import top.wml.service.UserService;
import top.wml.util.JwtUtil;

import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private CandidateService candidateService;

    @Override
    public String login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User userDetail = userMapper.selectOne(wrapper);
        if(Objects.isNull(userDetail)){
            throw new BusinessException("用户不存在！");
        }
        if(!(userDetail.getPassword().equals(user.getPassword()))){
            throw new BusinessException("密码错误！");
        }

        String token = JwtUtil.createToken(userDetail.getId(), userDetail.getPassword());
        return token;
    }

    @Override
    public boolean beCandidate(User user) {
        CommonResp commonResp = candidateService.addCandidate(user);
        return commonResp.getData() == null ? false : true;
    }

    @Override
    public boolean register(User user) {
        if(Objects.isNull(user)){
//            log.info("注册用户为空，随机生成用户.");
//            User.builder()
//                    .username(UUID.randomUUID().toString())
//                    .password("123456")
//                    .build();
            throw new BusinessException("注册用户为空！");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User hasUser = userMapper.selectOne(wrapper);
        if(Objects.nonNull(hasUser)){
            throw new BusinessException("该用户已存在！");
        }
        int insert = userMapper.insert(user);
        return insert > 0;
    }
}
