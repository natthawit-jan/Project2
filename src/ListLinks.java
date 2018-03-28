
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;


/**
 * Example program to list links from a URL.
 */
public class ListLinks {


    private CreateDB database = new CreateDB();
    private Document doc;
    private Elements titleCourse;
    private Elements eachTable;


    public ListLinks(String url) throws IOException {

        this.doc = Jsoup.connect(url).maxBodySize(0).get();
        print("Fetching %s...", url);

        // get each title of the courses such as  ------- General Education Courses > English Communication > 200+ Level Courses ---------
        titleCourse = doc.select("h4");
        titleCourse.remove(0);

        // get Each table
        eachTable = doc.select("table");

        // For each table we get its row
    }


    public void printAllSchedule() throws IOException, SQLException, InterruptedException {


        String title = doc.title();
        print("The title is %s", title);


        StringBuilder line = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            line.append("_");

        }


        for (int i = 0; i < eachTable.size(); i++) {
            System.out.println(titleCourse.get(i).text());

            Elements tr_inEachRow = eachTable.get(i).select("tbody").select("tr");

            Elements td_inEachRow = tr_inEachRow.select("td");


            System.out.printf(" Table %d has %d rows \n", i + 1, tr_inEachRow.size());
            int index = 0;
            for (Element ele : tr_inEachRow) {



//                System.out.println(ele.text());

                pushToDB(td_inEachRow, index);
                index++;


            }
            System.out.println();
            System.out.println(line.toString());
        }
        if (database.connection != null) database.connection.close();
    }


    private void pushToDB(Elements td, int index) throws SQLException {



        String subject = td.get(1+10*index).text();
        String section = td.get(3+10*index).text();
        String time = td.get(5+10*index).text();
        String instructor = td.get(7+10*index).text();


        database.insertDatabase(subject, section, time, instructor);

        System.out.printf("%s %s %s %s \n", subject, section, time, instructor);


    }

    private static StringBuilder createhead(Elements s) {
        StringBuilder sb = new StringBuilder();
        for (Element f : s) {
            sb.append(f.text());
            sb.append("\t");

        }
        return sb;
    }


    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }


}