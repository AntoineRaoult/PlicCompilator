package plic.analyse;

import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.*;

import java.io.File;

public class AnalyseurSyntaxique {

    private AnalyseurLexical analex;
    private String uniteCourante;

    public AnalyseurSyntaxique(File file) throws Exception {
        this.analex = new AnalyseurLexical(file);
    }

    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration {
        this.nextUniteCourant();
        Bloc res = this.analyseProg();
        if (!this.uniteCourante.equals("EOF")) throw new ErreurSyntaxique("EOF attendu");
        return res;
    }

    private Bloc analyseProg() throws ErreurSyntaxique, DoubleDeclaration {
        analyseTerminal("programme");
        if (!this.estIdf()) throw new ErreurSyntaxique("idf attendu");
        this.nextUniteCourant();
        return this.analyseBloc();
    }

    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration {
        Bloc res = new Bloc();
        this.analyseTerminal("{");
        while (uniteCourante.equals("entier") || uniteCourante.equals("tableau")) {
            analyseDeclaration();
        }
        res.ajouter(this.analyseInstruction());
        while (this.estIdf() || this.uniteCourante.equals("ecrire")) {
            res.ajouter(this.analyseInstruction());
        }
        this.analyseTerminal("}");
        return res;
    }

    //TYPE idf
    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        String type = this.analyseType();
        if (type.equals("entier")) {
            if (!this.estIdf()) throw new ErreurSyntaxique("idf attendu");
            TDS.getInstance().ajouter(new Entree(this.uniteCourante), new Symbole(type));
        } else if (type.equals("tableau")) {
            this.analyseTerminal("[");
            if (!this.estCsteEntiere())
                throw new ErreurSyntaxique("cstEntiere attendue");
            int val = Integer.parseInt(this.uniteCourante);
            if (val <= 0)
                throw new ErreurSyntaxique("la taille d'un tableau ne peut pas être nulle ou négative");
            this.nextUniteCourant();
            this.analyseTerminal("]");
            TDS.getInstance().ajouter(new Entree(this.uniteCourante),new Symbole(type, val));
        } else {
            throw new ErreurSyntaxique("entier ou tableau attendu"); //normalement impossible d'arriver là
        }
        this.nextUniteCourant();
        this.analyseTerminal(";");
    }

    //idf || tableau [ cstEntiere ]
    private String analyseType() throws ErreurSyntaxique {
        if (this.uniteCourante.equals("entier") || this.uniteCourante.equals("tableau")) {
            String type = this.uniteCourante;
            this.nextUniteCourant();
            return type;
        } else {
            throw new ErreurSyntaxique("entier ou tableau attendu");
        }
    }

    private Instruction analyseInstruction() throws ErreurSyntaxique {
        Instruction res;
        //ecrire
        if (this.uniteCourante.equals("ecrire")) {
            res = this.analyseEcrire();
            //affectation
        } else if (this.estIdf()) {
            res = this.analyseAffectation();
        } else {
            res = null;
        }
        return res;
    }

    private Ecrire analyseEcrire() throws ErreurSyntaxique {
        this.analyseTerminal("ecrire");
        Expression res = this.analyseExpression();
        return new Ecrire(res);
    }

    //Operande || Operande Operateur Operande
    private Expression analyseExpression() throws ErreurSyntaxique {
        Expression expression = this.analyseOperande();
        if (!this.uniteCourante.equals(";")) {
            this.analyseOperateur();
            this.analyseOperande();
        } else {
            this.nextUniteCourant();
        }
        return expression;
    }

    private void analyseOperateur() throws ErreurSyntaxique {
        throw new ErreurSyntaxique("pas d'operateur en plic0");
    }

    // csteEntiere || IDF
    private Expression analyseOperande() throws ErreurSyntaxique {
        if (this.estCsteEntiere()) {
            Nombre nombre = new Nombre(Integer.parseInt(this.uniteCourante));
            this.nextUniteCourant();
            return nombre;
        } else if (this.estIdf()) {
            Idf idf = new Idf(this.uniteCourante);
            this.nextUniteCourant();
            return idf;
        } else {
            throw new ErreurSyntaxique("operande attendu");
        }
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique {
        if (estIdf()) {
            String nom = this.uniteCourante;
            System.out.println(nom);
            this.nextUniteCourant();
            String type = TDS.getInstance().identifier(new Entree(nom)).getType();
            System.out.println(type);
            if(type.equals("entier")) {
                Idf idf = new Idf(this.uniteCourante);
                this.nextUniteCourant();
                this.analyseTerminal(":=");
                Expression expression = this.analyseExpression();
                return new Affectation(idf, expression);
            } else if(type.equals("tableau")) {
                this.analyseTerminal("[");
                AccesTableau tableau = new AccesTableau(nom,Integer.parseInt(this.uniteCourante));
                this.nextUniteCourant();
                this.analyseTerminal("]");
                this.analyseTerminal(":=");
                Expression expression = this.analyseExpression();
                return new Affectation(tableau,expression);
            } else {
                throw new ErreurSyntaxique("type inconnu");
            }
        } else {
            throw new ErreurSyntaxique("idf attendu");
        }
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        this.nextUniteCourant();
    }

    private boolean estIdf() {
        if (uniteCourante.equals("EOF")) return false;
        char[] lettres = uniteCourante.toCharArray();
        for (char lettre : lettres) {
            if (!(Character.isLetter(lettre))) {
                return false;
            }
        }
        return true;
    }

    private boolean estCsteEntiere() {
        char[] nombres = uniteCourante.toCharArray();
        for (char nombre : nombres) {
            if (!(Character.isDigit(nombre))) {
                return false;
            }
        }
        return true;
    }

    private void nextUniteCourant() {
        this.uniteCourante = this.analex.next();
    }
}