package springboot.web.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.domain.posts.Posts;
import springboot.domain.posts.PostsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }
    // junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    // 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
    // 여러 테스트가 동시에 수행되면 H2에 데이터가 그대로 남아 있어 다음 테스트에 영향을 줄 수 있음.

    @Test
    public void 게시글저장_불러오기(){
        //given
        //테스트 기반 환경을 구축하는 단계
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(// 테이블 posts에 insert/update 쿼리를 실행함. id값이 있으면 update, 없으면 insert 쿼리가 실행.
                Posts.builder()
                .title(title)
                .content(content)
                .author("tmdemr99@gmail.com")
                .build());

        // when
        // 테스트 하고자 하는 행위 선언.
        // 여기선 Posts가 DB에 insert 되는 것을 확인하기 위함.
        List<Posts> postsList = postsRepository.findAll();
        // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드.

        //then
        // 테스트 결과 검증
        // 실제 DB에 insert 되었는지 확인하기 위해 조회후, 입력된 값 확인.
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,3,16,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>> createdDate="+ posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);


    }
}
