package top.wml;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("top.wml.mapper")
public class CandidateApplication {
    public static void main(String[] args) {
        SpringApplication.run(CandidateApplication.class,args);
        log.info("CandidateApplication is running...");
    }
}
