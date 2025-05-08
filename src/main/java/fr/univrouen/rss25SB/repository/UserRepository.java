package fr.univrouen.rss25SB.repository;

import fr.univrouen.rss25SB.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}