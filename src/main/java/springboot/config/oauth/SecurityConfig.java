package springboot.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import springboot.domain.user.Role;

@RequiredArgsConstructor
@EnableWebSecurity //spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                //h2-console 화면을 사용하기 위해 disable 함.
                .and()
                .authorizeRequests()
                //URL별 권한 관리를 설정하는 옵션의 시작점.
                //authorizeRequest가 선언되어야만 antMatchers 옵션을 사용할 수 있음.
                .antMatchers("/","/css/**","/images/**","/js/**","h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                //권한 관리 대상을 지정하는 옵션, premitALl 전체 열람 권한, hasRole USER 권한을 가진 사람만 열람 가능
                .anyRequest().authenticated()
                // 설정된 값들 이외 나머지 URL들, authenticated를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용하도록 함. 로그인한 사용자.
                .and()
                .logout().logoutSuccessUrl("/")
                //로그아웃 기능에 대한 여러 설정의 진입점. /주소로 이동함.
                .and()
                .oauth2Login()
                //OAuth2 로그인 기능에 대한 설정의 진입점.
                .userInfoEndpoint()
                // 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당함.
                .userService(customOAuth2UserService);
                // 소셜 로그인 성공 시 후속 조치를 진행할 UserServcice 인터페이스의 구현체를 등록한다.
                // 리소스서버에서 사용자 정보를 가져온 사태에서 추가로 진행하고자 하는 기능들을 명시할 수 있음.

    }
}
