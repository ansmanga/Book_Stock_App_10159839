package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Main {

    BufferedReader buff;
    InputStreamReader isr;

    int selectOperation;

    public Main()
    {
        if (isr == null)
            isr = new InputStreamReader(System.in);
        if (buff == null)
            buff = new BufferedReader(isr);
    }
    public static void main(String[] args) throws SQLException {

        MySqlConnection mqc = new MySqlConnection();
        Connection mConnection = mqc.getmConnection();

        Main obj = new Main();

        System.out.println("Welcome to library!!!!");

        Library lb = new Library(mConnection);

        while(true)
        {
                System.out.println("Please select the operation to perform\n1. Add a book\n2. Update a book\n3. Remove a book\n4. Find a book\n5. Find a book on cost\n6. Find a cheapest book\n7. Find the costliest book\n8. Remove books which are 2 years older in library");

                try {
                    obj.selectOperation = Integer.parseInt(obj.buff.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(obj.selectOperation == 1)
                {

                    lb.saveBook();
                    System.out.println("Book is saved in the Library");

                }

                else if(obj.selectOperation == 2)
                {

                    lb.updateBook();
                }

                else if(obj.selectOperation == 3)
                {
                    lb.removeBook();
                }

                else if(obj.selectOperation == 4)
                {

                    lb.findBook();

                }

                else if(obj.selectOperation == 5)
                {
                    lb.findBookOnCost();
                }

                else if(obj.selectOperation == 6)
                {
                    lb.cheapestBook();
                }

                else if(obj.selectOperation == 7)
                {
                    lb.costliestBook();
                }

                else if(obj.selectOperation == 8)
                {
                    lb.removeOldBooks();
                }

                System.out.println("Do you want to continue the operation");
                System.out.println("Please select the operation:\n1. Yes\n2. No ");

                int selectChoice = 0;
                try {
                    selectChoice = Integer.parseInt(obj.buff.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (selectChoice == 2) {
                    break;
                }

        }
        System.out.println("Connection closed with the database");
        mConnection.close();

    }

}