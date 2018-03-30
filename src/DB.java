

import java.sql.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private Statement statement;
    private int id = -1;
    public Connection connection;

    private ArrayList<String> rsSubject;
    private ArrayList<String> rsTime;
    private ArrayList<String> rsSection;
    private ArrayList<String> rsTeacher;




    public DB() {
        rsSubject = new ArrayList<>();
        rsSection = new ArrayList<>();
        rsTime  = new ArrayList<>();
        rsTeacher = new ArrayList<>();
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


    public void insertDatabase(String subject, String section_, String time_, String instructor)  {
        try {

            rsSubject.add(subject );
            rsSection.add(section_);
            rsTime.add(time_ );
            rsTeacher.add(instructor );



            String sql = "INSERT INTO schedule VALUES(?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, subject);
            ps.setString(2, section_);
            ps.setString(3, time_);
            ps.setString(4, instructor);

            ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void DBSearchBySubject(String subject){


        for (int i = 0; i < rsSubject.size(); i++) {

            String rt = rsSubject.get(i).toLowerCase();
            if (rt.contains(subject.toLowerCase())) {
                System.out.println(i+". " +rsSubject.get(i));


            }
        }
    }

    public void firstDB(){
        System.out.println(rsSubject.get(0));
        System.out.println(rsSection.get(0));
        System.out.println(rsTime.get(0));
        System.out.println(rsTeacher.get(0));
    }

    public void getTheRest(List<String> h){
        for (String ele : h) {

            try {
                int tryParse = Integer.parseInt(ele);
                System.out.println("SUBJECT     : " + rsSubject.get(tryParse));
                System.out.println("SECTION      : " + rsSection.get(tryParse));
                System.out.println("TIME             : " + rsTime.get(tryParse));
                System.out.println("PROFESSOR  : " +rsTeacher.get(tryParse));
                System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println();

            }catch (NumberFormatException ex) {
                ex.printStackTrace();
                System.out.println("NOT A NUMBER");}
                }


        }

    public ArrayList<String> getRsSubject() {
        return rsSubject;
    }

    public ArrayList<String> getRsTime() {
        return rsTime;
    }

    public ArrayList<String> getRsSection() {
        return rsSection;
    }

    public ArrayList<String> getRsTeacher() {
        return rsTeacher;
    }

    public  void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
}



