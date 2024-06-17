package com.gjchiu.currencyservice.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private final ObjectMapper objectMapper;

    @Around("execution(* com.gjchiu.currencyservice.controller.*.*(..))")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        int lastPointIndex = joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".") + 1;
        String class_method = joinPoint.getSignature().getDeclaringTypeName().substring(lastPointIndex) + "." +
                joinPoint.getSignature().getName() + "()";

        Object result = joinPoint.proceed();
        logger.info("------------------------------------------");
        logger.info("[{}] [{}] [{}] [{}] [{} ms]", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), class_method, System.currentTimeMillis() - startTime);
        logger.info("-----------------REQUEST------------------");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} = {}", headerName, request.getHeader(headerName));
        }
        logger.info("request parameter: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(joinPoint.getArgs()));
        logger.info("-----------------RESPONSE-----------------");
        logger.info("RESPONSE: {}",this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        logger.info("------------------------------------------");

        return result;
    }
}
