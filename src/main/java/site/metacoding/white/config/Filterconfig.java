package site.metacoding.white.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.config.auth.JwtAuthenticationFilter;
import site.metacoding.white.config.auth.JwtAuthorizationFilter;
import site.metacoding.white.domain.UserRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Filterconfig {

    private final UserRepository userRepository; // DI(스프링 IOD 컨테이너에서 옴)

    // Bean : IOC Container 등록 : 서버 실행 시 실행됨.
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
        log.debug("디버그 : 인증 필터 등록");
        // 필터 생성
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthenticationFilter(userRepository));
        bean.addUrlPatterns("/login");
        bean.setOrder(1); // 낮은 순서대로 실행
        return bean;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
        log.debug("디버그 : 인가 필터 등록");
        // 필터 생성
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter());
        bean.addUrlPatterns("/s/*"); // 원래 두개인데, 이 친구만 예외
        return bean;
    }
}