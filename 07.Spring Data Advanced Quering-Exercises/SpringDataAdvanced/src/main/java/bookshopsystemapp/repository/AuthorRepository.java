package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //Task06
    List<Author> findAllByFirstNameEndingWith(String pattern);

    //Task08
    List<Author> findAuthorsByLastNameIsStartingWith(String letter);
    //Task10





}
