package ru.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.crud.models.Book;
import ru.crud.models.Person;
import ru.crud.repositories.BooksRepository;
import ru.crud.services.BooksService;
import ru.crud.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;

    private final BooksRepository booksRepository;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService, BooksRepository booksRepository) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.booksRepository = booksRepository;
    }

    @GetMapping()
    public String index(Model model ,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        
        if (page != null && booksPerPage != null && sortByYear != null) {
            model.addAttribute("books",booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent());
        } else if (page != null && booksPerPage != null) {
            model.addAttribute("books",booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent());
        } else if (sortByYear != null) {
            model.addAttribute("books",booksRepository.findAll(Sort.by("year")));
        } else {
            model.addAttribute("books",booksService.findAll());
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("bookTakeTest", booksService.takenTest(id));
        model.addAttribute("personFio", booksService.findFio(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        booksService.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.setOwner(person, id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeBook(@PathVariable("id") int id) {
        booksService.deleteOwner(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }

}
