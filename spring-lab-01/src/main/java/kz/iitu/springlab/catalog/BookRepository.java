package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final ConcurrentHashMap<Long, Book> books =
            new ConcurrentHashMap<>();

    private final AtomicLong sequence = new AtomicLong(0);

    public BookRepository() {
        save(new Book(null, "Effective Java", "Joshua Bloch", 2018));
        save(new Book(null, "Clean Code", "Robert Martin", 2008));
        save(new Book(null, "Spring in Action", "Craig Walls", 2022));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public Book findById(Long id) {
        return books.get(id);
    }

    public Book save(Book book) {
        Long id = book.id();

        if (id == null) {
            id = sequence.incrementAndGet();
        }

        Book saved = new Book(
                id,
                book.title(),
                book.author(),
                book.year()
        );

        books.put(id, saved);

        return saved;
    }

    public void deleteById(Long id) {
        books.remove(id);
    }
}