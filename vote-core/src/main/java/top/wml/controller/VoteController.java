package top.wml.controller;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import top.wml.annotation.TokenRequired;
import top.wml.entity.Candidate;
import top.wml.entity.Vote;
import top.wml.fegin.CandidateService;
import top.wml.resp.CommonResp;
import top.wml.service.VoteService;
import top.wml.util.JwtUtil;

import java.util.Date;

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

    @PostMapping("/{voteId}/{candidateId}")
    @TokenRequired
    public CommonResp voteForCandidate(@PathVariable Integer voteId,@PathVariable Integer candidateId){
        //根据id拿到vote和candidate
        Vote vote = voteService.getById(voteId);
        CommonResp resp = candidateService.getCandidate(candidateId);
        Candidate candidate = new ObjectMapper().convertValue(resp.getData(), Candidate.class);
        System.out.println(candidate.toString());
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
