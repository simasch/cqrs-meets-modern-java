package ch.martinelli.demo.traditional.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodTracer {

  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    Class<?> clazz = joinPoint.getTarget().getClass();
    if (clazz.getPackage().getName().startsWith("jdk.proxy") && clazz.getInterfaces().length > 0) {
      clazz = clazz.getInterfaces()[0];
    }

    Logger logger = LoggerFactory.getLogger(clazz);
    if (logger.isTraceEnabled()) {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      StringBuilder message = (new StringBuilder(joinPoint.getSignature().getName())).append("(");
      String args = Arrays.stream(joinPoint.getArgs()).map((o) -> o != null ? o.toString() : "null").collect(Collectors.joining(", "));
      message.append(args).append(")");

      try {
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        message.append(" | ").append(stopWatch.getTotalTimeMillis()).append(" ms");
        logger.trace(message.toString());
        return retVal;
      } catch (Throwable throwable) {
        logger.error(message.toString(), throwable);
        throw throwable;
      }
    } else {
      return joinPoint.proceed();
    }
  }
}
