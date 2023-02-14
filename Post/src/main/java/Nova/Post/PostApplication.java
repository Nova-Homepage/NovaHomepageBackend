package Nova.Post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //어노테이션으로 JPA Auditing을 활성화 해준다.
public class PostApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApplication.class, args);
	}

}
