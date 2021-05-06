package springboot.config.oauth;

// HandlerMethodArgumentResolver 의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘기는 기능을 함.

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import springboot.config.oauth.dto.SessionUser;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    // HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolver를 통해 추가해야만 함.
    // 다른 HandlerMethodArgumentResolver가 필요하면 같은 방식으로 추가하면 됨.
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }
    // 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단한다.
    // @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class 인 경우 true를 반환한다.

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws  Exception{
        return httpSession.getAttribute("user");
    }
    // 파라미터에 전달할 객체를 생성한다.
    // 세션에서 객체를 가져옴.
}
