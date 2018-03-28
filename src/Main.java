import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    ArrayList<String> choosen = new ArrayList<>();
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, InterruptedException {

        Class.forName("org.sqlite.JDBC");




        ScrapingWeb webScraping = new ScrapingWeb("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=18");

        // print all schedule and push all data to my DB
        System.out.println("Retriving DATABASE .......");
        webScraping.start();
        System.out.println("Database Successfully Retrived");



        // we ask user what subject do they want ?



        //try printing all distinct values
        webScraping.allDistinctSchedule();
        Scanner reader = new Scanner(System.in);

        while (reader.hasNext()){
            String sub = reader.nextLine();
            System.out.println("____________________________________SEARCH RESULT OF  \"" + sub + "\"  ___________________________________________-");
            webScraping.searchSubject(sub);
            System.out.printf("\n\n--------------------------------------------------------------------------------------------------------\n");

            if (sub.split(" ")[0].equals("choose")){

                //do something TODO
                save(sub.split(" "));

            }
            else{
                System.out.println(" Type in the number of subject that appears in front of the subject name to get more infomation \n (Eg. 4 5 56");
                sub = reader.nextLine();

                System.out.println("____________________HERE ARE THE LIST OF TIME AND TEACHERS ____________________________________");

                webScraping.theRest(sub.split(" "));
                }
            }

    }

    public static void save(String[] sub){
        //start from index 1
        for (int i = 1; i < sub.length ; i++) {




        }


    }
}
