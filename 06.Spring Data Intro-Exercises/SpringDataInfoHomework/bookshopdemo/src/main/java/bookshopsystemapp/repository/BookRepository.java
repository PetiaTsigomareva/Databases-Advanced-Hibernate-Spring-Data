package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //1. Get all books after the year 2000. Print only their titles.
    List<Book> findAllByReleaseDateAfter(LocalDate data);
    //2. Get all authors with at least one book with release date before 1990. Print their first name and last name.
    Set<Book> findAllByReleaseDateBefore(LocalDate date);

    //3. Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count

    //4. Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies.
    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);


}
