package Nova.Post.repository;

import Nova.Post.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<FileEntity,Long> {
}
