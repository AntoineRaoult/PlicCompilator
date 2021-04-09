package plic.analyse;

import plic.exceptions.ErreurSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyseurLexical {

    private Scanner scanner;

    public AnalyseurLexical(File file) throws Exception {
        try {
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new ErreurSyntaxique("Fichier source absent");
        }
    }

    public String next() {
        if(scanner.hasNext()) {
            String read = scanner.next();
            if(read.contains("//")) {
                if(scanner.hasNextLine()) scanner.nextLine();
                else return "EOF";
                return this.next();
            } else {
                return read;
            }
        } else return "EOF";
    }
}
