package server.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void service() {}

    @Around("service()")
    public Object interceptResource(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int len = proceedingJoinPoint.getArgs().length;
        if (len > 0 && proceedingJoinPoint.getArgs()[len - 1] instanceof String) {
            TenantContextHolder.setTenantId((String) proceedingJoinPoint.getArgs()[len - 1]);
            System.out.println(TenantContextHolder.getTenant());
        }
        Object msg = proceedingJoinPoint.proceed();
        TenantContextHolder.clear();
        return msg;
    }

}
