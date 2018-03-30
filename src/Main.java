import java.io.IOException;
import java.sql.SQLException;
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
        System.out.println("Database Successfully Retrived\n\n");






    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, InterruptedException {




        Class.forName("org.sqlite.JDBC");

        // INITIALISE the Main class with URL that I want to scrape. In this case  is muic open section next term

        Main io = new Main("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=18");


        // this is just to check the activty of uses that would break the program and exist ( e. user type x to exit the program.
        boolean breakSurvey = false;

        // local variable for scanner
        Scanner scmain = new Scanner(System.in);



        while (breakSurvey == false) { // LOOP UNTIL THE USER TYPE X TO EXIT
            String i = io.beginQuestion();  // begin with a question


            if (i.equals("x")) break; // if the user wants to exit right away they can do so by typing x
            else if (i.equals(" ")) { // if user press enter (without typing anything. the program brings them to see more options.

               // options offered
                System.out.println("Do you want to do something with this ? \n\n" +
                        "available commands are\n" +
                        "See  ( to see the course details  e.g See 343 432 343 \n" +
                        "Add ( to add course(s) you're interested in e.g  Add 343 234 123 \n" +
                        "View (to view your choosen courses) " +
                        "X   ( to exit the program) \n" +
                        "Press Enter  ( go back to search section ) \n");

                // since the input we receive has a command in front. I want to get that command in  order to process next
                String[] ans = scmain.nextLine().split(" ");


                // this list is just to keep whatever variable "ans" is
                // get the command
                String command = io.processCommand(ans);


                // After I get a command, I don't need the first index anymore. I care only whatever after the command.
                List<String> afterCommand = io.getAfterCommand(ans);


                if (command.equals("x".toLowerCase())) {
                    System.out.println("Thank you");
                    breakSurvey = true;
                } else if (command.equals("see".toLowerCase())) {
                    io.sectionData(afterCommand);

                } else if (command.equals("add".toLowerCase())) {
                    io.webScraping.save(afterCommand);
                    System.out.println(" Do you want to delete any of these courses  ? \n");
                    String[] answer = scmain.nextLine().split(" ");
                    String yORn = io.processCommand(answer);




                } else if (command.equals("view".toLowerCase())) {
                    int index = 1;
                    for (String g : io.webScraping.getChosen().values()) {
                        System.out.println(index + "." + g);
                        index++;
                    }

                }
            }
        }
        io.webScraping.close();
        System.out.println("Connection is closed. Thank you for using our service");




    }

    private String processCommand(String[] s){
        if (s.length ==1){
            return s[0];
        }
        else{
            ArrayList<String> ansArray = new ArrayList<>(Arrays.asList(s));

            // this list is just to keep whatever variable "ans" is
            // get the command
            return ansArray.get(0).toLowerCase();


        }

    }
    private List<String> getAfterCommand(String[] s ){
        List<String> rt = new ArrayList<>();
        for (int i = 1; i < s.length; i++) {
            rt.add(s[i]);


        }
        return rt;


    }
    public String beginQuestion(){

        System.out.println("Type in anything to search for opened courses next term \n" +
                "Press enter to see more options \n" +
                "Type 'X' to exit. \n\n" );
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

        System.out.println("____________________HERE ARE THE LIST OF TIME AND TEACHERS ____________________________________");

        webScraping.theRest(from);
        System.out.println("________________________________________________________________________________________________________\n");
    }











        }




