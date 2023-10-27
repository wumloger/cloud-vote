package top.wml.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.wml.entity.Candidate;
import top.wml.entity.Vote;

public interface VoteService extends IService<Vote> {
    boolean voteForCandidate(Vote vote,Candidate candidate);
}
