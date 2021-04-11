package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Bloc;
import plic.repint.Programme;
import plic.repint.TDS;

import java.io.File;

public class Plic {

    public Plic(String fichier) throws Exception {
        String[] split = fichier.split("\\.");
        if(!split[split.length-1].equals("plic")) throw new Exception("Suffixe incorrect");
        File file = new File(fichier);
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
        Programme prog = as.analyse();
        prog.verifier();
        System.out.println(prog.toMips());
    }

    public static void main(String[] args) {
        try {
            new Plic(args[0]);
        } catch (Exception e) {
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
