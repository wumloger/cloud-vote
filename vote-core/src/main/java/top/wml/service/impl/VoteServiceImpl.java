package top.wml.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.wml.entity.Candidate;
import top.wml.entity.Vote;
import top.wml.exception.BusinessException;
import top.wml.fegin.CandidateService;
import top.wml.mapper.VoteMapper;
import top.wml.resp.CommonResp;
import top.wml.service.VoteService;

import java.util.Date;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {
    @Resource
    private VoteMapper voteMapper;
    @Resource
    private CandidateService candidateService;
    @Override
    public boolean voteForCandidate(Vote vote, Candidate candidate) {
        if(vote.getIsClosed()){
            throw new BusinessException("这张票已经失效或已经使用过了!");
        }
        if(new Date().getTime() > vote.getEndTime().getTime()){
            vote.setIsClosed(true);
            voteMapper.updateById(vote);
            throw new BusinessException("这张票卷已经过期了！");
        }
        //得票数加1
        candidate.setVotes(candidate.getVotes() + 1);
        System.out.println(candidate);
        vote.setIsClosed(true);
        voteMapper.updateById(vote);
        CommonResp commonResp = candidateService.updateCandidate(candidate);
        return commonResp.getData() != null;
    }
}
