package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        List<Book> books = service.findAll(author);

        if (books.size() > limit) {
            books = books.subList(0, limit);
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable Long id) {

        Book book = service.findById(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {

        Book saved = service.create(book);

        URI location = URI.create("/api/books/" + saved.id());

        return ResponseEntity
                .created(location)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @RequestBody Book book) {

        Book updated = service.update(id, book);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}