package top.wml.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wml.annotation.TokenRequired;
import top.wml.entity.User;
import top.wml.exception.BusinessException;
import top.wml.resp.CommonResp;
import top.wml.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping("/list")
    @TokenRequired
    public CommonResp getUsers(){
        List<User> list = userService.list();
        return CommonResp.success(list);
    }

    @PostMapping("/login")
    public CommonResp login(@RequestBody User user){
        String token = userService.login(user);
        return CommonResp.success(token);
    }

    @PostMapping("/be/{id}")
    public CommonResp beCandidate(@PathVariable Integer id){
        User byId = userService.getById(id);
        if(Objects.isNull(byId)){
            throw new BusinessException("该用户不存在!");
        }
        boolean b = userService.beCandidate(byId);
        return b ? CommonResp.success(b):CommonResp.fail("该用户已经是候选人了！");
    }

    @PostMapping("/register")
    public CommonResp register(@RequestBody User user){
        boolean register = userService.register(user);
        return CommonResp.success(register);
    }
}
