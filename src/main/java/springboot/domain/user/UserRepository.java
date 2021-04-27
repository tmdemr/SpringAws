package springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//User의 CRUD를 책임짐.
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    // findByEmail : 이메일을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드.
}
