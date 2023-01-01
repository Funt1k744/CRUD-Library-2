package ru.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.crud.models.Book;
import ru.crud.models.Person;
import ru.crud.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book updateBook, int id) {
        updateBook.setBookId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person findFio(int id) {
        return booksRepository.findById(id).get().getOwner();
    }

    public boolean takenTest(int id) {
        return booksRepository.findById(id).get().getOwner() != null;
    }

    @Transactional
    public void setOwner(Person person, int id) {
        Optional<Book> bookOptional = booksRepository.findById(id);
        Book book = bookOptional.get();
        book.setOwner(person);
        book.setTakenAt(new Date());
        booksRepository.save(book);
    }

    @Transactional
    public void deleteOwner(int id) {
        Optional<Book> bookOptional = booksRepository.findById(id);
        Book book = bookOptional.get();
        book.setOwner(null);
        book.setTakenAt(null);
        booksRepository.save(book);
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByNameStartingWith(query);
    }
}
