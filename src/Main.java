import java.io.IOException;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, InterruptedException {

        Class.forName("org.sqlite.JDBC");


        ListLinks begin = new ListLinks("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=18");

        // print all schedule and push all data to my DB
        begin.printAllSchedule();

        // we ask user what subject do they want ?








//
//        statement.executeUpdate("INSERT INTO student VALUES(5781071, 'Yodyiam', 'Tangrodkhajorn', 2, 3.75)");
//        statement.executeUpdate("INSERT INTO student VALUES(5781021, 'Thita', 'Sang', 3, 2.24 )");

    }
}
