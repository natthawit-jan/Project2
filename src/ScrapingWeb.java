
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;


/**
 * Example program to list links from a URL.
 */
public class ScrapingWeb {




    private DB database = new DB();
    private Document doc;
    private Elements titleCourse;
    private Elements eachTable;
    private HashMap<Integer, List<String>> chosen;
    private ArrayList<String> keepTime;


    public ScrapingWeb(String url) throws IOException {
        chosen = new HashMap<>();
        keepTime = new ArrayList<>();


        this.doc = Jsoup.connect(url).maxBodySize(0).get();
        print("Fetching %s...", url);

        // get each title of the courses such as  ------- General Education Courses > English Communication > 200+ Level Courses ---------
        titleCourse = doc.select("h4");
        titleCourse.remove(0);

        // get Each table
        eachTable = doc.select("table");

        // For each table we get its row
    }

    public void start() {
        String title = doc.title();
        print("The title is %s", title);

        for (int i = 0; i < eachTable.size(); i++) {
//            System.out.println(titleCourse.get(i).text());
            Elements tr_inEachRow = eachTable.get(i).select("tbody").select("tr");
            Elements td_inEachRow = tr_inEachRow.select("td");
//            System.out.printf(" Table %d has %d rows \n", i + 1, tr_inEachRow.size());
            int index = 0;

            // push everything into the database.
            for (Element ele : tr_inEachRow) {
                pushToDB(td_inEachRow, index);
                index++;
            }
        }


    }

    private void pushToDB(Elements td, int index) {
        String subject = td.get(1+10*index).text();
        String section = td.get(3+10*index).text();
        String time = td.get(5+10*index).text();
        String instructor = td.get(7+10*index).text();

        database.insertDatabase(subject, section, time, instructor);

//        System.out.printf("%s %s %s %s \n", subject, section, time, instructor);


    }

    public void deleteSelected(List<String> toDelete){


        for (String ele : toDelete){
            if (chosen.size() < Integer.parseInt(ele)){

                System.out.println("You only have  " +chosen.size() + " subjects to delete \n");

            }
            else {
                chosen.remove(Integer.parseInt(ele));
                System.out.println("- Successfully deleted -");
            }
        }

    }

    public void save(List<String>  s ){
        if (exceedsLimit()){
            System.out.println(" You are not longer allowed to add any course into the list \n");

        }
        else{


        for (String sub : s){

            int int_ = Integer.parseInt(sub);
            int isClashBecase = clashWithType(int_);
            if (isClashBecase != 0){
                System.out.println("We can't allow adding because " + reason(isClashBecase));
            }else {

                List<String> toPush = new ArrayList<>();
                chosen.put(chosen.size()+1 , new ArrayList<String>(Arrays.asList(database.getRsSubject().get(int_),database.getRsSection().get(int_),
                        database.getRsTime().get(int_), database.getRsTeacher().get(int_))));
                keepTime.add(database.getRsTime().get(int_));

                System.out.println("Successfully added >> " + database.getRsSubject().get(int_) + "\n");


                // Warning the user that there are more than 5 courses in thier bracket

            }
            }
        }
    }

    private boolean exceedsLimit(){
        if (chosen.size() == 5){
            return true;
        }
        return false;
    }

    private int clashWithType(int i){
        if (chosen.containsKey(i)){
            return 1;
        }
        // check time comflict.
        // for each time in the data
        for ( String eachtime : keepTime){
            if (eachtime.equals(database.getRsTime().get(i))){
                return 2;
            }



        }


        return 0;

    }


    public Map<Integer, List<String>> getChosen() {
        return chosen;
    }

    private StringBuilder makeLine(){
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            line.append("_");
        }

        return line;

    }

    public String reason(int type){
        if (type == 1){
            return "you already add this into your list.\n";
        }
        else if (type == 2){
            return "there is a time conflict in your list.\n";
        }
        return "Nothing";
    }

    private static StringBuilder createhead(Elements s) {
        StringBuilder sb = new StringBuilder();
        for (Element f : s) {
            sb.append(f.text());
            sb.append("\t");

        }
        return sb;
    }


    public  void searchSubject(String subjectToSearch){

        database.DBSearchBySubject(subjectToSearch);
    }
    public void theRest(List<String> sub){
        database.getTheRest(sub);
    }

    public void close(){
        database.closeConnection();
    }




    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }


}