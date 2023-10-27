package top.wml.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.wml.entity.Candidate;
import top.wml.resp.CommonResp;

@FeignClient("candidate-server")
public interface CandidateService {

    @PostMapping("/candidate/get")
    public CommonResp getCandidate(@RequestParam Integer id);

    @PutMapping("/candidate/update")
    public CommonResp updateCandidate(Candidate candidate);

    @GetMapping("/candidate/list")
    public CommonResp getCandidates();

}
