package app.exam.repository;

import app.exam.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByName(String name);
}
