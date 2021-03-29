package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Bloc;
import plic.repint.TDS;

import java.io.File;

public class Plic {

    public Plic(String fichier) throws Exception {
        String[] split = fichier.split("\\.");
        if(!split[split.length-1].equals("plic")) throw new Exception("Suffixe incorrect");
        File file = new File(fichier);
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
        Bloc bloc = as.analyse();
        System.out.println(TDS.getInstance());
        System.out.println(bloc);
        System.out.println();
        System.out.println(bloc.toMips());
        bloc.verifier();
    }

    public static void main(String[] args) {
        try {
            new Plic("src/plic/sources/tableau.plic");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
