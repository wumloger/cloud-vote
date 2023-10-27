package top.wml.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import top.wml.entity.User;
import top.wml.resp.CommonResp;

@FeignClient("candidate-server")
public interface CandidateService {
    @PostMapping("/candidate/add")
    CommonResp addCandidate(User user);
}
