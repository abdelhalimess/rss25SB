package fr.univrouen.rss25SB.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univrouen.rss25SB.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Tu peux ajouter d'autres méthodes si besoin, par exemple :
    boolean existsByTitleAndPublicationDate(String title, LocalDateTime publicationDate);
}
