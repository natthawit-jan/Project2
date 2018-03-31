import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Main {

    ScrapingWeb webScraping;
    Scanner sc;

    public Main(String url) throws IOException {
        webScraping = new ScrapingWeb(url);
        sc = new Scanner(System.in);
        System.out.println("Retriving DATABASE .......\n");


        webScraping.start();

        System.out.println("Database Successfully Retrived\n\n");
        System.out.println(makeLine(30).toString());

        }

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, InterruptedException {



        Class.forName("org.sqlite.JDBC");

        // INITIALISE the Main class with URL that I want to scrape. In this case  is muic open section next term

        Main io = new Main("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=18");


        while (true) { // LOOP UNTIL THE USER TYPE X TO EXIT
            io.beginQuestion();  // begin with a question

            //there must be the way to fix this.

            String[] ans = io.askAndReturnStringArray();
            String command = io.processCommand(ans);


            // If the user type in X we print a good bye phrease and exit the program.
            if (command.equals("x")) {
                io.sayGoodbye();

                return;
            }

            // if user press enter (without typing anything. the program brings them to see more options.
            else if (command.equals(" ")) {

                boolean isBreakOutFromOptions = false;
                while (!isBreakOutFromOptions) {
                    io.printOption();

                    ans = io.askAndReturnStringArray();

                    // get the command
                    command = io.processCommand(ans);

                    // After I get a command, I don't need the first index anymore. I care only whatever after the command
                    // parameter type 0 : is for saying that we call only after the command
                    List<String> afterCommand = io.getAfterCommand(ans, 0);


                    if (command.equals("x".toLowerCase())) {

                        io.sayGoodbye();

                        return;

                    } else if (command.isEmpty()) {
                        isBreakOutFromOptions=true;
                    } else if (command.equals("see".toLowerCase())) {
                        io.seeData(afterCommand);

                    } else if (command.equals("add".toLowerCase())) {
                        io.addToTable(afterCommand);

                    } else if (command.equals("view".toLowerCase())) {

                        io.printViewTable();
                        System.out.println("Do you want to delete any of these courses  ? \n");

                        ans = io.askAndReturnStringArray();
                        command = io.processCommand(ans);
                        if (command.equals("YES".toLowerCase())) {
                            System.out.println("Which one ? \n ");
                            ans = io.askAndReturnStringArray();
                            afterCommand = io.getAfterCommand(ans, 1);
                            io.deleteFromTable(afterCommand);

                        }

                    } else if (command.equals("txt".toLowerCase())) {
                        io.txtGenetrate();//end view
                    }
                }
            }


        }// end big else



    }




    private void close(){
        webScraping.close();
    }

    private void deleteFromTable(List<String> s){
        webScraping.deleteSelected(s);

    }
    private void txtGenetrate(){
        try {
            FileWriter writer = new FileWriter("YourPlan.txt");
            writer.write("Here are the list of your chosen courses. \n\n");
            int no = 1;
            for (List<String> data : webScraping.getChosen().values()){

                String[] array = asStrings(data.toArray());
                String join = String.join(" ",array);
                writer.write(no+" . "+join+"\n\n");
                no++;

            }
            writer.write("\n\n Good luck ! :)");
            writer.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public String[] asStrings(Object... objArray) {
        String[] strArray = new String[objArray.length];
        for (int i = 0; i < objArray.length; i++)
            strArray[i] = String.valueOf(objArray[i]);
        return strArray;
    }



    private void addToTable(List<String> addWhat){
        webScraping.save(addWhat);

    }

    private void printViewTable() {
        int index = 1;
        for (List<String> g : webScraping.getChosen().values()) {
            System.out.println(index + "." + g.get(0));
            index++;

        }
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
    private List<String> getAfterCommand(String[] s, int type ){
        List<String> rt = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            rt.add(s[i]);
        }
        //get only from the second index.
        if (type == 0){
        return rt.subList(1,rt.size());
        }
        // get all
        else if (type==1){
            return rt;

        }
        return null;


    }

    public String[] askAndReturnStringArray(){


        String[] ans = sc.nextLine().split(" ");
        return ans;





    }

    public String[] askAndReturnStringArray(){


        String[] ans = sc.nextLine().split(" ");
        return ans;





    }

    public void printOption(){
        System.out.println(
                "Do you want to do something with this ? \n\n" +
                "  Available commands are..\n" +
                        makeLine(20).toString()+
                "• See    ( to see the course details ) e.g See 343 432 343 \n" +
                "• Add   ( to add course(s) you're interested in ) e.g  Add 343 234 123 \n" +
                "• View  ( to view your choosen courses) \n" +
                "• Txt    ( to save your chosen courses as txt file you will also get this file when you exit the program) \n" +
                "•  X      ( to exit the program) \n" +
                "• Press Enter  ( go back to search section ) \n");

    }
    public void beginQuestion(){

        System.out.println("Type in anything to search for opened courses next term \n" +
                "Press enter to see more options \n" +
                "Type 'X' to exit. \n\n" );


    }

    private void printSeachResult(String wordToSearch){
        System.out.println("____________________________________SEARCH RESULT OF  \"" + wordToSearch + "\"  ___________________________________________-");
        webScraping.searchSubject(wordToSearch);
        System.out.println(makeLine(30).toString());


    }

    private void sayGoodbye(){
        txtGenetrate();
        close();
        System.out.println("Connection is closed. Thank you for using our service");
    }


    private StringBuilder makeLine(int howLong){
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < howLong ; i++) {
            st.append("_");

        }
        st.append("\n");
        return st;
    }

    public void seeData(List<String> from) {

        System.out.println("____________________HERE ARE THE LIST OF TIME AND TEACHERS ____________________________________");

        webScraping.theRest(from);
        System.out.println(makeLine(30).toString());
    }











        }




