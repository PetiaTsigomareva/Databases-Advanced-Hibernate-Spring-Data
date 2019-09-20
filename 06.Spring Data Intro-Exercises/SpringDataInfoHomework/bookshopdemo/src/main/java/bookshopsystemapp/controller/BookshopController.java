package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Scanner;

@Controller
public class BookshopController implements CommandLineRunner {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public BookshopController(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }
/* Start Project:
  1. In classes AuthorServiceImpl,BookServiceImpl,CategoryServiceImpl - change file paths for the author.txt, books.txt, category.txt
  2. For testing queries enter number from 1 to 4
*
* */
    @Override
    public void run(String... args) throws IOException {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();
        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()) {
            case 1:
                System.out.println("---------------------Query01-------------------------------------");
                this.bookService.getAllBooksTitlesAfter().stream().forEach(b -> System.out.println(b));
                break;
            case 2:
                System.out.println("---------------------Query02-------------------------------------");
                this.bookService.getAllAuthorsByBookBefore().stream().forEach(b -> System.out.println(b));
                break;
            case 3:
                System.out.println("---------------------Query03-------------------------------------");
                this.authorService.getAllAuthorsByBooksNumber().stream().forEach(a -> System.out.println(a));
                break;
            case 4:
                System.out.println("---------------------Query04-------------------------------------");
                this.bookService.getAllBooksByAuthorName().stream().forEach(a -> System.out.println(a));

                break;
            default:
                break;

        }


    }
}
