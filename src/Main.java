import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    ScrapingWeb webScraping;
    Scanner sc;

    public Main(String url) throws IOException {
        webScraping = new ScrapingWeb(url);
        sc = new Scanner(System.in);
        System.out.println("Retriving DATABASE .......");


        webScraping.start();
        System.out.println("Database Successfully Retrived");

        System.out.println();
        System.out.println();




    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, InterruptedException {

        Class.forName("org.sqlite.JDBC");
        Main io = new Main("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=18");
        boolean breakSurvey = false;
        Scanner scmain = new Scanner(System.in);

        while (breakSurvey == false){
            String i = io.beginQuestion();
            if (i.equals("x")) break;
            else if (i.equals(" ")) {
                System.out.println("Do you want to do something with this ? \n\n" +
                        "available commands are\n" +
                        "See  ( to see the course details  e.g See 343 432 343 \n" +
                        "Add ( to add course(s) you're interested in e.g  Add 343 234 123 \n" +
                        "View (to view your choosen courses) " +
                        "No   ( to exit the program");
                String[] ans = scmain.nextLine().split(" ");
                ArrayList<String> ansArray = new ArrayList<>(Arrays.asList(ans));
                String command = ansArray.get(0).toLowerCase();
                ansArray.remove(0);

                if (command.equals("no".toLowerCase())) {
                    System.out.println("Thank you");
                    breakSurvey = true;
                } else if (command.equals("see".toLowerCase())) {
                    io.sectionData(ansArray);

                } else if (command.equals("add".toLowerCase())) {
                    io.webScraping.save(ansArray);
                } else if (command.equals("view".toLowerCase())) {
                    int index = 1;
                    for (String g : io.webScraping.getChoosen()) {
                        System.out.println(index + "." + g);
                        index++;
                    }

                }
            }








            }
//            else if (sub.split(" ")[0].equals("choose")){
//
//                //do something TODO
//                webScraping.save(sub.split(" ")[1]);
//
//            } else if (sub.toLowerCase().equals("view table")){
//                System.out.println(Arrays.toString(webScraping.getChoosen().toArray()));
//            }
//            else if (sub.toLowerCase().equals("no")){
//
//                }
    }
    public String beginQuestion(){

        System.out.println("Type in to search for any opened course next term \n" +
                "Enter to see more options \n" +
                "Type 'X' to exit. ");
        String searchTypeIn = sc.nextLine();
        if (searchTypeIn.toLowerCase().equals("X".toLowerCase())){
            return "x";
        }
        else if (searchTypeIn.isEmpty()){
            return " ";
        }

        System.out.println("____________________________________SEARCH RESULT OF  \"" + searchTypeIn + "\"  ___________________________________________-");
        webScraping.searchSubject(searchTypeIn);
        System.out.printf("\n\n--------------------------------------------------------------------------------------------------------\n");

        return "ok";

    }

    public void sectionData(List<String> from) {


//        System.out.println(" Type in the number of subject that appears in front of the subject name to get more infomation \n (Eg. 4 5 56");

//

//
        System.out.println("____________________HERE ARE THE LIST OF TIME AND TEACHERS ____________________________________");
//
        webScraping.theRest(from);
    }











        }




