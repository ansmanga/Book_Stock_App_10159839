package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Library implements Stock{

    static int counter = 0;
    HashMap<Integer, Book> bookMap = new HashMap<>();

    Connection mConnection;
    PreparedStatement pStmt = null;
    public Library()
    {

    }

    public Library(Connection mConnection)
    {
        this.mConnection = mConnection;
    }

    public void saveBook()
    {
        try {
            Statement stmt = mConnection.createStatement();
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter the book id");
            int bookId = Integer.parseInt(buff.readLine());
            System.out.println("Enter the book title");
            String bookTitle = buff.readLine();
            System.out.println("Enter the book author");
            String bookAuthor = buff.readLine();
            System.out.println("Enter the book type");
            String bookType = buff.readLine();
            System.out.println("Enter the book cost");
            float bookCost = Float.parseFloat(buff.readLine());
            System.out.println("Enter the date at which book added in library");
            String dateString = buff.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date bookAddedDate = dateFormat.parse(dateString);
            Book b = new Book(bookId, bookAuthor, bookTitle, bookType, bookCost, bookAddedDate);

            pStmt = mConnection.prepareStatement("insert into book_info values(?,?,?,?,?,?)");
            pStmt.setInt(1, b.getBookID());
            pStmt.setString(2,  b.getBookTitle());
            pStmt.setString(3, b.getBookAuthor());
            pStmt.setString(4,b.getBookType());
            pStmt.setFloat(5,b.getBookCost());
            pStmt.setDate(6,b.getDateAdded());
            // METHOD FOR EXECUTING THE SQL QUERY
            pStmt.executeUpdate();


        } catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // This is just an example, you might want to handle it differently based on your requirements
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateBook() {

        System.out.println("Enter the details you want to update");

        try {
            System.out.println("Enter the book id you want to update");
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            int bookId = Integer.parseInt(buff.readLine());
            String findquery = "Select * from book_info where book_id = ?";
            pStmt = mConnection.prepareStatement(findquery);
            pStmt.setInt(1,bookId);
            ResultSet resultSet = pStmt.executeQuery();

            boolean bookFound = false;
            StringBuilder updateQuery = new StringBuilder("UPDATE book_info SET ");
            while (resultSet.next())
            {
                bookFound = true;
                int foundBookId = resultSet.getInt("book_id");
                String foundBookTitle = resultSet.getString("book_title");
                String foundBookAuthor = resultSet.getString("book_author");
                String foundBookType = resultSet.getString("book_type");
                float foundBookCost = resultSet.getFloat("book_cost");
                Date foundDateAdded = resultSet.getDate("book_date_added");

                System.out.println("Enter the fields you want to update (comma-separated: title,author,type,cost,dateadded):");
                String[] fieldsToUpdate = buff.readLine().split(",");
                String bookTitle = null, bookAuthor = null, bookType = null;
                float bookCost = 0;
                Date bookAddedDate = null;
                java.sql.Date sqlDateAdded = null;
                for (String field : fieldsToUpdate) {

                    if(field.equals("title"))
                    {
                        System.out.println("Enter the title you want to update");
                        bookTitle = buff.readLine();
                        updateQuery.append("book_title = ?, ");
                    }
                    else if(field.equals("author"))
                    {
                        System.out.println("Enter the author you want to update");
                        bookAuthor = buff.readLine();
                        updateQuery.append("book_author = ?, ");
                    }
                    else if(field.equals("type"))
                    {
                        System.out.println("Enter the book type you want to update");
                        bookType = buff.readLine();
                        updateQuery.append("book_type = ?, ");
                    }
                    else if(field.equals("cost"))
                    {
                        System.out.println("Enter the cost you want to update");
                        bookCost = Float.parseFloat(buff.readLine());
                        updateQuery.append("book_cost = ?, ");
                    }
                    else if(field.equals("dateadded"))
                    {
                        System.out.println("Enter the dateadded you want to update");
                        String dateString = buff.readLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        bookAddedDate = dateFormat.parse(dateString);
                        sqlDateAdded = new java.sql.Date(bookAddedDate.getTime());
                        updateQuery.append("book_date_added = ?, ");
                    }

                }


                updateQuery.setLength(updateQuery.length() - 2);
                updateQuery.append(" where book_id = ?");
                pStmt = mConnection.prepareStatement(updateQuery.toString());
                int parameterIndex = 1;
                for(String field : fieldsToUpdate)
                {
                    if(field.equals("title"))
                    {
                        pStmt.setString(parameterIndex++,bookTitle);
                    }
                    else if(field.equals("author"))
                    {
                        pStmt.setString(parameterIndex++,bookAuthor);
                    }
                    else if(field.equals("type"))
                    {
                        pStmt.setString(parameterIndex++,bookType);
                    }
                    else if(field.equals("cost"))
                    {
                        pStmt.setFloat(parameterIndex++,bookCost);
                    }
                    else if(field.equals("dateadded"))
                    {
                        pStmt.setDate(parameterIndex++, sqlDateAdded);
                    }
                }
                pStmt.setInt(parameterIndex, foundBookId);
                pStmt.executeUpdate();

                System.out.println("Book Updated Successfully");

            }

            if(!bookFound)
            {
                System.out.println("The book is not present which you want to update");
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeBook() {
        System.out.println("Enter the book id which you want to remove");

        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the book id");
            int bookId = Integer.parseInt(buff.readLine());
            String deleteQuery = "DELETE FROM book_info WHERE book_id = ?";
            pStmt = mConnection.prepareStatement(deleteQuery);
            pStmt.setInt(1, bookId);
            int numberOfRowsAffected = pStmt.executeUpdate();
            if(numberOfRowsAffected != 0)
            {
                System.out.println("Book with ID " + bookId + " removed successfully.");
            }
            else
            {
                System.out.println("Book with this Id is not found");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void findBook() {
        System.out.println("Find book according to bookTitle, bookCost, bookType");
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the bookTitle");
            String bookTitle = buff.readLine();
            System.out.println("Enter the Book cost");
            float bookCost = Float.parseFloat(buff.readLine());
            System.out.println("Enter the Book type");
            String bookType = buff.readLine();
            String findQuery = "SELECT * FROM book_info WHERE book_title = ? AND book_cost = ? AND book_type = ?";
            pStmt = mConnection.prepareStatement(findQuery);
            pStmt.setString(1, bookTitle);
            pStmt.setFloat(2, bookCost);
            pStmt.setString(3, bookType);
            ResultSet resultSet = pStmt.executeQuery();
            boolean bookFound = false;

            while (resultSet.next()) {
                bookFound = true;
                int foundBookId = resultSet.getInt("book_id");
                String foundBookTitle = resultSet.getString("book_title");
                String foundBookAuthor = resultSet.getString("book_author");
                System.out.println("Found Book - ID: " + foundBookId + ", Title: " + foundBookTitle + ", Author: " + foundBookAuthor);
            }

            if (!bookFound) {
                System.out.println("No books found matching the specified criteria.");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void findBookOnCost() {

        System.out.println("Find book between the start cost and end cost");

        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the start cost from where to found");
            float startCost = Float.parseFloat(buff.readLine());
            System.out.println("Enter the end cost upto which to found");
            float endCost = Float.parseFloat(buff.readLine());
            String findQuery = "SELECT * from book_info where book_cost BETWEEN ? AND ?";
            pStmt = mConnection.prepareStatement(findQuery);
            pStmt.setFloat(1, startCost);
            pStmt.setFloat(2, endCost);

            ResultSet resultSet = pStmt.executeQuery();
            boolean bookFound = false;

            while (resultSet.next()) {
                bookFound = true;
                int foundBookId = resultSet.getInt("book_id");
                float foundBookCost = resultSet.getFloat("book_cost");
                System.out.println("Found Book - ID: " + foundBookId + ", Cost: " + foundBookCost);
            }

            if (!bookFound) {
                System.out.println("No books found matching the specified criteria.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cheapestBook() {

        System.out.println("Find the cheapest book");

        try {
            String findQuery = "SELECT book_id, book_cost FROM book_info WHERE book_cost = (SELECT MIN(book_cost) FROM book_info)";
            pStmt = mConnection.prepareStatement(findQuery);
            ResultSet resultSet = pStmt.executeQuery();

            boolean bookFound = false;

            while (resultSet.next()) {
                bookFound = true;
                int foundBookId = resultSet.getInt("book_id");
                float foundBookCost = resultSet.getFloat("book_cost");
                System.out.println("Found Book - ID: " + foundBookId + ", Cost: " + foundBookCost);
            }

            if (!bookFound) {
                System.out.println("No books are available");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void costliestBook() {

        System.out.println("Find the costliest book");

        try {
            String findQuery = "SELECT book_id, book_cost FROM book_info WHERE book_cost = (SELECT MAX(book_cost) FROM book_info)";
            pStmt = mConnection.prepareStatement(findQuery);
            ResultSet resultSet = pStmt.executeQuery();

            boolean bookFound = false;

            while (resultSet.next()) {
                bookFound = true;
                int foundBookId = resultSet.getInt("book_id");
                float foundBookCost = resultSet.getFloat("book_cost");
                System.out.println("Found Book - ID: " + foundBookId + ", Cost: " + foundBookCost);
            }

            if (!bookFound) {
                System.out.println("No books are available");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void removeOldBooks()
    {
        System.out.println("Remove the books which are 2 years older");

        try {
            String removeQuery = "delete from book_info where TIMESTAMPDIFF(year,book_date_added,CURDATE())>2";
            pStmt = mConnection.prepareStatement(removeQuery);
            pStmt = mConnection.prepareStatement(removeQuery);

            int rowsAffected = pStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " books removed.");
            } else {
                System.out.println("No books were found that are 2 years or older.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
