package top.wml.aspect;

import cn.hutool.http.server.HttpServerRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.wml.annotation.TokenRequired;
import top.wml.exception.BusinessException;
import top.wml.util.JwtUtil;

import java.net.http.HttpRequest;

@Aspect
@Component
public class TokenAspect {

    @Around("@annotation(top.wml.annotation.TokenRequired)")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        if (token == null) {
            throw new BusinessException("没有token！");
        }
        boolean validate = JwtUtil.validate(token);
        if (!validate) {
            throw new BusinessException("token无效！");
        }

        // 如果 token 有效，继续执行方法
        return joinPoint.proceed();
    }

}
