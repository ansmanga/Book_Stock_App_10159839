package org.example;

public interface Stock {



    public void saveBook();

    public void updateBook();

    public void removeBook();

    public void findBook();

    public void findBookOnCost();

    public void cheapestBook();

    public void costliestBook();

    public boolean isBookPresent(int bookId);

    public void removeOldBooks();

    public void printBooks();

}
