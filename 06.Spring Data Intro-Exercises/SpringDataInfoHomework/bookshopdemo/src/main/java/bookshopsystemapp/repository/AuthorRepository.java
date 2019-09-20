package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import net.bytebuddy.description.type.TypeDefinition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //3. Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.
    @Query(value = "SELECT a From bookshopsystemapp.domain.entities.Author as a ORDER BY size(a.books) DESC ")
    List<Author> findAllByOrderByBooksDesc();

    Author findAuthorByLastName(String name);


}
