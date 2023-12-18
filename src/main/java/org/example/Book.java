package org.example;
import java.util.Date;
public class Book {

    int bookID;
    String bookTitle, bookAuthor, bookType;
    float bookCost;
    java.sql.Date dateAdded;

    public Book()
    {

    }

    public Book(int bookID, String bookAuthor, String bookTitle, String bookType, float bookCost, Date dateAdded)
    {
        this.bookID = bookID;
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.bookType = bookType;
        this.bookCost = bookCost;
        this.dateAdded = new java.sql.Date(dateAdded.getTime());
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookCost(float bookCost) {
        this.bookCost = bookCost;
    }

    public  float getBookCost() {
        return bookCost;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookType() {
        return bookType;
    }

    public void setDateAdded(java.sql.Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    public java.sql.Date getDateAdded() {
        return (java.sql.Date) dateAdded;
    }

}
