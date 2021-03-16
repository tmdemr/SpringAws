package springboot.domain.posts;
//실제 DB의 테이블과 매칭될 클래스 Entity클래스라고도 함.
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.domain.BaseTimeEntity;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity // 테이블과 링크될 클래스임을 나타낸다.
public class Posts extends BaseTimeEntity { // 실제 DB의 테이블과 매칭될 클래스

    @Id//해당 테이블의 PK필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)// DB생성규칙
    private Long id;

    // 이 클래스의 필드는 모두 컬럼이 되지만, 기본값을 변경하기 위해 표현함.
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }


}
