package bookshopsystemapp.service;

        import bookshopsystemapp.domain.entities.Author;
        import bookshopsystemapp.repository.AuthorRepository;
        import bookshopsystemapp.util.FileUtil;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.io.IOException;
        import java.util.List;
        import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String AUHTORS_FILE_PAHT = "C:\\Projects\\softuni\\Databases Frameworks - Hibernate & Spring Data\\06.Spring Data Intro-Exercises\\bookshopdemo\\src\\main\\resources\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        //check is author table contain rows
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent=this.fileUtil.getFileContent(AUHTORS_FILE_PAHT);
        for (String line:authorFileContent) {

            String[] names=line.split("\\s+");
            Author author =new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);

        }


    }

    @Override
    public List<String> getAllAuthorsByBooksNumber() {
        List<Author> authors=this.authorRepository.findAllByOrderByBooksDesc();
        return authors.stream().map(a->String.format("%s %s - %d",a.getFirstName(),a.getLastName(),a.getBooks().size())).collect(Collectors.toList());
    }



}
