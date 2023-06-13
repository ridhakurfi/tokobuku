package com.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Users;
import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;
import com.bookStore.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookController {

	@Autowired
	private BookService service;

	@Autowired
	private MyBookListService myBookService;

	@Autowired
	private UserService uService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}

	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book> list = service.getAllBook();
//		ModelAndView m=new ModelAndView();
//		m.setViewName("bookList");
//		m.addObject("book", list);
		return new ModelAndView("bookList", "book", list);
	}

	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		return "redirect:/available_books";
	}

	@GetMapping("/my_books")
	public String getMyBooks(Model model) {
		List<MyBookList> list = myBookService.getAllMyBooks();
		model.addAttribute("book", list);
		return "myBooks";
	}

	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b = service.getBookById(id);
		MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/my_books";
	}

	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {
		Book b = service.getBookById(id);
		model.addAttribute("book", b);
		return "bookEdit";
	}

	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}

	@PostMapping("/loginVal")
	public String loginval(@ModelAttribute Users us, HttpSession session) {

		boolean f = uService.validateUsername(us.getUsername());
		boolean g = uService.validatePassword(us.getPassword());
		if (f && g) {
			session.setAttribute("msg", "Login Succesful");
			return "redirect:/home";
		} else {
			session.setAttribute("msg", "Login Failed, Please Check Username/Pass");
			return "redirect:/login";
		}

	}

}
