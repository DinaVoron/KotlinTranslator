import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java_cup.runtime.*;

public class Main {

    public static void main(String[] args) throws Exception{
        String content = readFileAsString("src/input.txt");

        Lexer lexer = new Lexer(content);

        Parser parser = new Parser(lexer);
        try {
            parser.parse();
            System.out.println(parser.toString());
        } catch (Exception e) {
            System.out.println("Произошла ошибка в ходе работы парсера...");
            return;
        }

        CodeGenerator cg = new CodeGenerator();
        System.out.println(cg.generate(parser.program));

    }

    public static String readFileAsString(String filePath) throws IOException {
        String res = "";
        String cur;
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader (new FileReader(filePath));
        while ((cur = reader.readLine()) != null) {
            res += cur + "®";
        }
        return res;
    }
}



