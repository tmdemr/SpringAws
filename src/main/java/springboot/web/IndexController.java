package springboot.web;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.config.oauth.LoginUser;
import springboot.config.oauth.dto.SessionUser;
import springboot.service.PostsService;
import springboot.web.dto.PostsResponseDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;



    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        // httpSession.getAttribute"user" 로 가져오던 세션 정보값을 개선함.
        // 어느 컨트롤러든 @LoginUser만 사용하면 세션 정보를 가져올 수 있음.
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }


    /*
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
    // 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때, 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다.
*/
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
