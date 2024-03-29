package com.merion.spring.book.service;

import com.merion.spring.base.exception.ResourceNotFoundException;
import com.merion.spring.book.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    static List<BookEntity> bookStorage = new ArrayList<>();

    public BookService() {
        fillStorage();
    }

    public void fillStorage() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            book.setTitle("Book #" + random.nextInt(100, 999));
            book.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore" +
                    " veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur" +
                    " magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non" +
                    " numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit" +
                    " laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum" +
                    " qui dolorem eum fugiat quo voluptas nulla pariatur?");
            bookStorage.add(book);
        }
    }

    public List<BookEntity> all() {
        return bookStorage;
    }

    public Optional<BookEntity> byId(Integer id) {
        return bookStorage.stream().filter((book -> book.getId().equals(id))).findFirst();
    }

    public BookEntity create(String title, String description) {
        BookEntity book = new BookEntity();
        book.setId(bookStorage.size());
        book.setTitle(title);
        book.setDescription(description);
        bookStorage.add(book);
        return book;
    }

    public Optional<BookEntity> edit(BookEntity book) {
        Optional<BookEntity> oldBookOptional = byId(book.getId());
        if (oldBookOptional.isEmpty()) {
            return Optional.empty();
        }
        BookEntity oldBookEntity = oldBookOptional.get();
        oldBookEntity.setTitle(book.getTitle());
        oldBookEntity.setDescription(book.getDescription());
        return Optional.of(oldBookEntity);
    }

    public Boolean delete(Integer id) {
        Optional<BookEntity> book = byId(id);
        if (book.isEmpty()) {
            return false;
        }
        bookStorage.remove(book.get());
        return true;
    }

    public Optional<BookEntity> editPart(Integer id, Map<String, String> fields) {
        Optional<BookEntity> optionalBookEntity = byId(id);
        if (optionalBookEntity.isEmpty()) return Optional.empty();
        BookEntity book = optionalBookEntity.get();
        for (String key : fields.keySet()) {
            switch (key) {
                case "title" -> book.setTitle(fields.get(key));
                case "description" -> book.setDescription(fields.get(key));
            }
        }
        return Optional.of(book);
    }
}
