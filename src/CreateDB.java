

import java.sql.*;
import java.util.ArrayList;

public class CreateDB {

    private Statement statement;
    private int id = -1;
    public Connection connection;

    public CreateDB() {

        connection = null;
        try {
            //create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:muicspace.db");

            // Create statement to nbe executed soon

            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Note: just for this example , we want a clear start;
            // if the table exists already we want to delete it
            statement.executeUpdate("DROP TABLE IF EXISTS schedule");

//            statement.executeUpdate("DROP TABLE IF EXISTS major");


            //Now, create the table. name it "student"
            statement.executeUpdate("CREATE TABLE schedule (subject_ TEXT, section_ TEXT, time_ TEXT , instructor_ TEXT )");

            //another table is for storing major id with their names
//            statement.executeUpdate("CREATE TABLE major (major_id INTEGER, major_name TEXT)");
        } catch (SQLException e) {
            // If the error message is out of memory
            // it probably means no database file is found
            System.out.println(e.getMessage());
        }
    }


    public void insertDatabase(String subject, String section_, String time_, String instructor) throws SQLException {
//        id++;
//        System.out.println(id);

        try {

            String sql= "INSERT INTO schedule VALUES(?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, subject);
            ps.setString(2, section_);
            ps.setString(3, time_);
            ps.setString(4, instructor);

            int val = ps.executeUpdate();





        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


//
