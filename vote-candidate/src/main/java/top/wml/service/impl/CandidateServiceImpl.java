package top.wml.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.wml.entity.Candidate;
import top.wml.mapper.CandidateMapper;
import top.wml.service.CandidateService;

@Service
public class CandidateServiceImpl extends ServiceImpl<CandidateMapper, Candidate> implements CandidateService {
}
