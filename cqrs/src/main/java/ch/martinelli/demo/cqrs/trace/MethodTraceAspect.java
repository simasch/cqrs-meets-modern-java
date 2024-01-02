package ch.martinelli.demo.cqrs.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MethodTraceAspect {

  private final MethodTracer methodTracer = new MethodTracer();

  @Around("execution(* ch.martinelli.demo..*.*(..))")
  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    return methodTracer.logMethod(joinPoint);
  }
}
