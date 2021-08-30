package it.fabrik.aspect;

import it.fabrik.error.ErrorCode;
import it.fabrik.error.ErrorMessage;
import it.fabrik.error.ErrorResponse;
import it.fabrik.response.BaseResponse;
import it.fabrik.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
public class AuthorizeAspect {

    private final JwtService jwtService;
    @Value("${jwt.refresh}")
    private boolean refresh;

    public AuthorizeAspect(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Around("@annotation(it.fabrik.annotation.Auth)")
    public Object verifyAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String bearer = (request.getHeader("Authorization").replace("Bearer ", "")).trim();
        if (!jwtService.validateToken(bearer)) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .type(ErrorCode.SESSION_EXPIRED.getCode())
                    .message(ErrorMessage.SESSION_EXPIRED.getMessage())
                    .status("FAILURE")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } else {
            return pjp.proceed();
        }
    }

    @AfterReturning(value = ("@annotation(it.fabrik.annotation.Auth)"),
            returning = "response")
    public void endpointAfterReturning(ResponseEntity response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        BaseResponse body;
        if (!(response.getBody() instanceof ErrorResponse)) {
            String bearer = request.getHeader("Authorization");
            bearer = bearer.replace("Bearer ", "");
            if (refresh) {
                bearer = jwtService.refreshToken(bearer);
            }
            body = (BaseResponse) response.getBody();
            body.setJwt(bearer);
            body.setStatus("SUCCESS");
        } else {
            body = (BaseResponse) response.getBody();
            body.setStatus("FAILURE");
        }
    }
}
