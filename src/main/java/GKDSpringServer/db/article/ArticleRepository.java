package GKDSpringServer.db.article;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    List<ArticleEntity> findByAuthor_Id(Long authorId);
}
