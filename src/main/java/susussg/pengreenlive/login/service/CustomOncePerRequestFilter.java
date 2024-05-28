package susussg.pengreenlive.login.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import susussg.pengreenlive.login.dto.Member;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomOncePerRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Session ID: {}", request.getSession().getId());

        Object contextObject = request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (contextObject == null) {
            log.info("SecurityContext 객체가 null입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if (contextObject instanceof LinkedHashMap) {
            LinkedHashMap<?, ?> contextMap = (LinkedHashMap<?, ?>) contextObject;
            try {
                processSecurityContext(contextMap);
            } catch (Exception e) {
                log.error("LinkedHashMap 처리 중 오류 발생", e);
            }
        } else {
            log.info("SecurityContext 객체의 타입이 LinkedHashMap이 아닙니다: {}", contextObject.getClass().getName());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("Authentication principal: {}", authentication.getPrincipal());
        } else {
            log.info("Authentication is null");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void processSecurityContext(LinkedHashMap<?, ?> contextMap) {
        Map<?, ?> authenticationMap = (Map<?, ?>) contextMap.get("authentication");

        List<SimpleGrantedAuthority> authorities = ((List<Map<String, String>>) authenticationMap.get("authorities")).stream()
                .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                .collect(Collectors.toList());

        Map<?, ?> principalMap = (Map<?, ?>) authenticationMap.get("principal");
        String username = (String) principalMap.get("username");
        String userNm = (String) principalMap.get("userNm");
        Integer vendorSeqStr = (Integer) principalMap.get("vendorSeq");
        Integer channelSeqStr = (Integer) principalMap.get("channelSeq");
        String userUuid = (String) principalMap.get("userUuid");

        Long vendorSeq = vendorSeqStr != null ? Long.valueOf(vendorSeqStr) : null;
        Long channelSeq = channelSeqStr != null ? Long.valueOf(channelSeqStr) : null;

        Member member;
        if (userUuid != null) {
            // User
            member = new Member(username, "", authorities, userNm, userUuid, null, null);
        } else {
            // Vendor
            member = new Member(username, "", authorities, userNm, null, vendorSeq, channelSeq);
        }
        Authentication newAuth = new UsernamePasswordAuthenticationToken(member, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
