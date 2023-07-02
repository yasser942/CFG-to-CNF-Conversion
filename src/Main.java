import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ReadInput readInput= new ReadInput();
        CFG_CNF converter= new CFG_CNF();
        converter.assignInputToConverter(readInput.read());
        converter.storeInMap();




        System.out.println("------ CFG Form ------");
        converter.printLanguage();
        System.out.println("------------------------------------------");
        System.out.println("\n1- Remove Epsilon :");
        //-------------------------------------------------------
        for (int i = 0; i < readInput.getLine_count(); i++) {
            converter.eliminateEpsilon();
        }

        converter.printLanguage();
        //-------------------------------------------------------
        converter.removeDuplicateKeyValue();
        //-------------------------------------------------------
        System.out.println("3- Remove Single Variable in Every Production: ");

        for (int i = 0; i < readInput.getLine_count(); i++) {
            converter.eliminateSingleVariable();
        }

        converter.printLanguage();
        //-------------------------------------------------------
        converter.assignVariable();
        //-------------------------------------------------------
        System.out.println("5- Assign  two terminals to a new variable: ");

        for (int i = 0; i < readInput.getLine_count(); i++) {
            converter.removeThreeTerminal();
        }
        converter.printLanguage();
        System.out.println("------ CNF Form ------");
        converter.printLanguage();

        //-------------------------------------------------------



    }
}
