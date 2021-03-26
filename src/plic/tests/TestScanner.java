package plic.tests;

import plic.analyse.AnalyseurLexical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestScanner {
    public static void main(String[] args) throws Exception {
        File file = new File("src/plic/sources/sourceTrois.plic");
        AnalyseurLexical al = new AnalyseurLexical(file);

        String read = al.next();
        while(!read.equals("EOF")) {
            System.out.println(read);
            read = al.next();
        }
    }
}
