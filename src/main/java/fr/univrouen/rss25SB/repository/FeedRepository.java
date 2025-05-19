package fr.univrouen.rss25SB.repository;

import fr.univrouen.rss25SB.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    
    @Query("SELECT f FROM Feed f WHERE f.title = :title AND f.pubDate = :pubDate")
    Optional<Feed> findByTitleAndPubDate(@Param("title") String title, @Param("pubDate") LocalDateTime pubDate);
}