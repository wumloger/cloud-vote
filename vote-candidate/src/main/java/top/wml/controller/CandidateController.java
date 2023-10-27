package top.wml.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.wml.annotation.TokenRequired;
import top.wml.entity.Candidate;
import top.wml.entity.User;
import top.wml.exception.BusinessException;
import top.wml.resp.CommonResp;
import top.wml.service.CandidateService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Resource
    private CandidateService candidateService;

    @GetMapping("/list")
    public CommonResp getCandidates() {
        List<Candidate> list = candidateService.list();
        if(list.size() == 0 || list == null){
            throw new BusinessException("当前候选人列表为空");
        }
        return CommonResp.success(list);
    }

    @PostMapping("/get")
    @TokenRequired
    public CommonResp getCandidate(@RequestParam("id") Integer id) {
        Candidate byId = candidateService.getById(id);
        return CommonResp.success(byId);
    }


    @PostMapping("/add")
    @TokenRequired
    public CommonResp addCandidate(@RequestBody User user){
        if (Objects.isNull(user)){
            throw new BusinessException("要成为候选者的用户不能为空！");
        }

        LambdaQueryWrapper<Candidate> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Candidate::getUserId, user.getId());
        Candidate one = candidateService.getOne(wrapper);
        if(!Objects.isNull(one)){
            throw new BusinessException("该用户已经是候选人了！");
        }
        Candidate candidate = Candidate.builder()
                .name(user.getUsername())
                .description("候选人")
                .userId(user.getId())
                .votes(0)
                .build();
        boolean save = candidateService.save(candidate);
        return CommonResp.success(save);
    }

    @PutMapping("/update")
    @TokenRequired
    public CommonResp updateCandidate(@RequestBody Candidate candidate){
        if(Objects.isNull(candidate)){
            throw new BusinessException("要修改的候选者不能为空！");
        }
        boolean b = candidateService.updateById(candidate);
        return CommonResp.success(b);
    }
}
