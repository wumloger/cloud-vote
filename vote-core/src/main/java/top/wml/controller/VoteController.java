package top.wml.controller;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import top.wml.annotation.TokenRequired;
import top.wml.entity.Candidate;
import top.wml.entity.Vote;
import top.wml.exception.BusinessException;
import top.wml.fegin.CandidateService;
import top.wml.resp.CommonResp;
import top.wml.service.VoteService;
import top.wml.util.JwtUtil;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Resource
    private VoteService voteService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private CandidateService candidateService;

    @PutMapping("/add")
    @TokenRequired
    @Operation(summary = "用户创建票卷")
    public CommonResp add(){
        //根据tokern获取用户的id
        String token = request.getHeader("token");
        JSONObject jsonObject = JwtUtil.getJSONObject(token);
        String idStr = jsonObject.get("id").toString();
        int id = Integer.parseInt(idStr);
        //有效期3天
        Long validTime = Long.valueOf(72 * 60 * 60 * 1000);
        Long nowTime = new Date().getTime();
        Vote vote = Vote.builder()
                .description("给别人投票吧！")
                .creatorId(id)
                .startTime(new Date())
                .endTime(new Date(nowTime + validTime))
                .isClosed(false)
                .title("票卷")
                .build();
        boolean save = voteService.save(vote);
        return save ? CommonResp.success(save) : CommonResp.fail("添加失败");
    }

    @PostMapping("/{candidateId}")
    @TokenRequired
    @Operation(summary = "用户给候选人投票")
    public CommonResp voteForCandidate(@PathVariable Integer candidateId){
        //从token中解析出用户id
        String token = request.getHeader("token");
        JSONObject jsonObject = JwtUtil.getJSONObject(token);
        String idStr = jsonObject.get("id").toString();
        int id = Integer.parseInt(idStr);
        //根据id拿到所有该用户能用的票卷
        List<Vote> userVote = voteService.getUserVote(id);
        //长度为0就是没有有效的
        if(userVote.size() == 0){
            throw new BusinessException("抱歉，您没有可用的投票卷");
        }
        //有票就拿第一个
        Vote vote = userVote.get(0);
        //发远程请求拿候选人信息
        CommonResp resp = candidateService.getCandidate(candidateId);
        if(resp.getData() == null){
            throw new BusinessException("没有该候选人");
        }
        Candidate candidate = new ObjectMapper().convertValue(resp.getData(), Candidate.class);
        System.out.println(candidate.toString());
        //投票
        boolean b = voteService.voteForCandidate(vote,candidate);
        CommonResp candidates = candidateService.getCandidates();
        if(b) {
            candidates.setMsg("投票成功");
            return candidates;
        }
        candidates.setMsg("投票失败");

        return candidates;
    }

}
