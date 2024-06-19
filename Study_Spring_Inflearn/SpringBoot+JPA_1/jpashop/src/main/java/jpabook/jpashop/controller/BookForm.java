package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public Book createBook() {
        Book book = new Book();
        book.setName(this.getName());
        book.setPrice(this.getPrice());
        book.setStockQuantity(this.getStockQuantity());
        book.setAuthor(this.getAuthor());
        book.setIsbn(this.getIsbn());
        return book;
    }
}
