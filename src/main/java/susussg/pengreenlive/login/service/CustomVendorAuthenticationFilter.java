package susussg.pengreenlive.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import susussg.pengreenlive.login.dto.Member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CustomVendorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("CustomVendorAuthenticationFilter: Request Parameter Map: {}", request.getParameterMap());
        request.getParameterMap().forEach((key, value) -> {
            log.info("Request Parameter: {} = {}", key, String.join(", ", value));
        });

        try {
            Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");
            log.info("로그인 정보 {} {}", username, password );


            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> result = new HashMap<>();

        Member member = (Member) authResult.getPrincipal();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", member.getUsername());
        userInfo.put("name", member.getUserNm());
        userInfo.put("role", member.getAuthorities().toString());

        // SecurityContext에 Member 객체 저장
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        log.info("생성된 세션 아이디 {}", request.getSession().getId());

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        log.info("session info {}", SecurityContextHolder.getContext().getAuthentication());

        result.put("user", objectMapper.writeValueAsString(userInfo));
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Content-Length");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Authentication Failed");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
