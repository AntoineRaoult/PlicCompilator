import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSemantique;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Programme;
import plic.repint.TDS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PlicTests {

    public static int compteurMips;

    public static void main (String[] args) {
        try {
            System.out.println("----------------------------------------");
            System.out.println("ERREUR ACCOLADE OUVRANTE");
            System.out.println("attendu : { attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurAccolade.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR ACCOLADE FERMANTE");
            System.out.println("attendu : } attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurAccolade2.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR AFFECTATION");
            System.out.println("attendu : := attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurAffectation.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR DEBORDEMENT");
            System.out.println("attendu : ERREUR: débordement du tableau");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurDebordement.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR EXTENSION");
            System.out.println("attendu : Suffixe incorrect");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurExtension.txt");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR FICHIER INEXISTANT");
            System.out.println("attendu : Fichier source inexistant");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurExtension.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR LEXICALE");
            System.out.println("attendu : %: Caractère inconnu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurLex.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR LEXICALE");
            System.out.println("attendu : $: Caractère inconnu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurLex2.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR MANQUE INSTRUCTION");
            System.out.println("attendu : instruction attendue");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurManqueInstruction.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR NOM PROGRAMME INVALIDE");
            System.out.println("attendu : idf attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurNomProg.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR NOM PROGRAMME INVALIDE");
            System.out.println("attendu : idf attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurNomProg2.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR ; COLLÉ");
            System.out.println("attendu : ;: Caractère inconnu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurPointVirgule.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR ; MANQUANT");
            System.out.println("attendu : ; attendu ");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurPointVirgule2.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR SEMANTIQUE");
            System.out.println("attendu : variable m non déclarée");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurSemantique1.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR DECLARATION APRES INSTRUCTION");
            System.out.println("attendu : := attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurDeclaration.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR DOUBLE DECLARATION");
            System.out.println("attendu : variable existe deja");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurDoubleDeclaration.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR TAILLE TABLEAU NULLE");
            System.out.println("attendu : ??");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurTableau.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("ERREUR OPERATEURS SANS PARENTHESES");
            System.out.println("attendu : ; attendu");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/erreurOperateurs.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE ENTIERS (1 variable)");
            System.out.println("attendu : 2");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide1.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE ENTIERS (plusieurs variables)");
            System.out.println("attendu : 2 55 34 666");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide2.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE TABLEAU");
            System.out.println("attendu : 3 5 3 3 5");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide3_tableau.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE TABLEAU IMBRICATION");
            System.out.println("attendu : 3");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide4_tableau.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE TABLEAU");
            System.out.println("attendu : 1 2 2");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide5_tableau.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE MULTIPLICATION");
            System.out.println("attendu : 35 24");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide6_mult.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE SOMME");
            System.out.println("attendu : 12 10");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide7_somme.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE DIFFERENCE");
            System.out.println("attendu : -9 -5 2 15");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide8_difference.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE PLUSIEURS OPERATEURS");
            System.out.println("attendu : 50 -2 25 14");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide9_operateurs.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE OPERATIONS DANS TABLEAU");
            System.out.println("attendu : 5 1 7 9");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide10_operateursTableau.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE OPERATEURS LOGIQUES");
            System.out.println("attendu : fichier mips");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide11_operateursLogiques.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE CONDITIONNELLE");
            System.out.println("attendu : fichier mips");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide12_si.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE TANT QUE");
            System.out.println("attendu : fichier mips");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide13_tantque.plic");
            TDS.resetTDS();
            Thread.sleep(100);

            System.out.println("----------------------------------------");
            System.out.println("VALIDE POUR");
            System.out.println("attendu : fichier mips");
            System.out.print("obtenu : ");
            new PlicTests("src/plic/sources/valide14_pour.plic");
            TDS.resetTDS();
            Thread.sleep(100);

        } catch (IndexOutOfBoundsException | InterruptedException e) {
            System.err.println("ERREUR: Fichier source absent");
        }
    }

    public PlicTests(String nomFichier) {
        try {
            File file = new File(nomFichier);

            String filename = file.getName();
            String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
            String name = filename.substring(0, filename.lastIndexOf("."));
            if (!extension.equals("plic")) {
                throw new ErreurSyntaxique("Suffixe incorrect");
            }

            // Créer l’analyseur syntaxique
            AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
            // Analyse syntaxique du texte source
            Programme prog = as.analyse();
            prog.verifier();
            String code = prog.toMips();
//            System.out.println(code);
            System.out.println(name + ".asm \n");
            PrintWriter writer = new PrintWriter("src/mips/" + name + ".asm", "UTF-8");
            writer.println(code);
            writer.close();

            // on écrit le mips dans un fichier filename.mips

        } catch (ErreurSyntaxique e) {
            System.err.println("ERREUR: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERREUR: Fichier source inexistant");
        } catch (DoubleDeclaration e) {
            System.err.println("ERREUR: " + e.getMessage());
        } catch (ErreurSemantique e) {
            System.err.println("ERREUR: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}