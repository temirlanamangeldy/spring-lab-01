package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        if (author == null || author.isBlank()) {
            return repository.findAll();
        }

        return repository.findAll().stream()
                .filter(book ->
                        book.author().equalsIgnoreCase(author))
                .toList();
    }

    public Book findById(Long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(
                new Book(
                        null,
                        book.title(),
                        book.author(),
                        book.year()
                )
        );
    }

    public Book update(Long id, Book book) {
        if (repository.findById(id) == null) {
            return null;
        }

        return repository.save(
                new Book(
                        id,
                        book.title(),
                        book.author(),
                        book.year()
                )
        );
    }

    public boolean delete(Long id) {
        if (repository.findById(id) == null) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}