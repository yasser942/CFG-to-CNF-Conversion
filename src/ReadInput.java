import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadInput {


    private  int line_count;



    public int getLine_count() {
        return line_count;
    }



    public String read() throws FileNotFoundException {

         line_count =0;
        String final_string;


        File file = new File("src/CFG.txt");
        Scanner sc = new Scanner(file);
        sc.nextLine();

        ArrayList<String> strList = new ArrayList<>();
        while (sc.hasNextLine()) {
            strList.add(sc.next());
        }

        line_count=strList.size();
        final_string=strList.get(0)+"\n";
        for(int i=1;i<line_count-1;i++)
            final_string+=strList.get(i)+"\n";

        final_string+=strList.get(line_count-1);
        sc.close();


        return final_string;



    }

   }
