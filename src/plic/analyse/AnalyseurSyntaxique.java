package plic.analyse;

import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSemantique;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.*;

import java.io.File;

public class AnalyseurSyntaxique {

    private Bloc currentBloc;

    private AnalyseurLexical analex;
    private String uniteCourante;

    public AnalyseurSyntaxique(File file) throws Exception {
        this.analex = new AnalyseurLexical(file);
    }

    public Programme analyse() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        this.nextUniteCourant();
        Programme res = this.analyseProg();
        if (!this.uniteCourante.equals("EOF")) throw new ErreurSyntaxique("EOF attendu");
        return res;
    }

    private Programme analyseProg() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        analyseTerminal("programme");
        if (!this.estIdf()) throw new ErreurSyntaxique("idf attendu");
        this.nextUniteCourant();
        return new Programme(this.analyseBloc());
    }

    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        Bloc bloc = new Bloc(this.currentBloc);
        this.currentBloc = bloc;
        this.analyseTerminal("{");
        while (uniteCourante.equals("entier") || uniteCourante.equals("tableau")) {
            bloc.ajouterDeclaration(analyseDeclaration());
        }
        bloc.ajouterInstruction(this.analyseInstruction());
        while (this.estIdf() || this.uniteCourante.equals("ecrire")) {
            bloc.ajouterInstruction(this.analyseInstruction());
        }
        this.analyseTerminal("}");
        this.currentBloc = bloc.getParent();
        return bloc;
    }

    //TYPE idf
    private Declaration analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        String type = this.analyseType();
        Declaration decla;
        if (type.equals("entier")) {
            if (!this.estIdf()) throw new ErreurSyntaxique("idf attendu");
            decla = new Declaration(new Entree(this.uniteCourante), new Symbole(type));
        } else if (type.equals("tableau")) {
            this.analyseTerminal("[");
            if (!this.estCsteEntiere())
                throw new ErreurSyntaxique("cstEntiere attendue");
            int val = Integer.parseInt(this.uniteCourante);
            if (val <= 0)
                throw new ErreurSyntaxique("la taille d'un tableau ne peut pas être nulle ou négative");
            this.nextUniteCourant();
            this.analyseTerminal("]");
            decla = new Declaration(new Entree(this.uniteCourante), new Symbole(type, val));
        } else {
            throw new ErreurSyntaxique("entier ou tableau attendu"); //normalement impossible d'arriver là
        }
        this.nextUniteCourant();
        this.analyseTerminal(";");
        return decla;
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

    private Instruction analyseInstruction() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        Instruction res;
        //ecrire
        if (this.uniteCourante.equals("ecrire")) {
            res = this.analyseEcrire();
        } else if (this.uniteCourante.equals("si")) {
            res = this.analyseCondition();
        } else if (this.uniteCourante.equals("pour") || this.uniteCourante.equals("tantque")) {
            res = this.analyseIteration();
        } else if (this.uniteCourante.equals("lire")) {
            res = this.analyseLire();
        } else if (this.estIdf()) {
            res = this.analyseAffectation();
        } else {
            throw new ErreurSyntaxique("instruction attendue");
        }
        return res;
    }

    private Lire analyseLire() throws ErreurSyntaxique, ErreurSemantique {
        this.analyseTerminal("lire");
        if (this.estIdf()) {
            Lire res = new Lire(new Idf(this.uniteCourante));
            this.nextUniteCourant();
            this.analyseTerminal(";");
            return res;
        } else {
            throw new ErreurSyntaxique("idf attendu");
        }
    }

    private Condition analyseCondition() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        this.analyseTerminal("si");
        this.analyseTerminal("(");
        Expression condition = this.analyseExpression();
        this.analyseTerminal(")");
        this.analyseTerminal("alors");
        Bloc alors = this.analyseBloc();
        if (this.uniteCourante.equals("sinon")) {
            this.analyseTerminal("sinon");
            Bloc sinon = this.analyseBloc();
            return new Condition(condition, alors, sinon);
        } else {
            return new Condition(condition, alors);
        }
    }

    private Iteration analyseIteration() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        Iteration res;
        if (this.uniteCourante.equals("pour")) {
            this.analyseTerminal("pour");
            if (this.estIdf()) {
                Idf idf = new Idf(this.uniteCourante);
                this.nextUniteCourant();
                this.analyseTerminal("dans");
                Expression e1 = this.analyseExpression();
                this.analyseTerminal("..");
                Expression e2 = this.analyseExpression();
                this.analyseTerminal("repeter");
                Bloc bloc = this.analyseBloc();
                res = new Pour(idf,e1,e2,bloc);
            } else {
                throw new ErreurSyntaxique("idf attendu");
            }
        } else if (this.uniteCourante.equals("tantque")) {
            this.analyseTerminal("tantque");
            this.analyseTerminal("(");
            Expression condition = this.analyseExpression();
            this.analyseTerminal(")");
            this.analyseTerminal("repeter");
            Bloc bloc = this.analyseBloc();
            res = new Tantque(condition,bloc);
        } else {
            throw new ErreurSyntaxique("mot-cle d'iteration inconnu");
        }
        return res;
    }

    private Ecrire analyseEcrire() throws ErreurSyntaxique, ErreurSemantique {
        this.analyseTerminal("ecrire");
        Expression res = this.analyseExpression();
        this.analyseTerminal(";");
        return new Ecrire(res);
    }

    //Operande || Operande Operateur Operande
    private Expression analyseExpression() throws ErreurSyntaxique, ErreurSemantique {
        Expression expression = this.analyseOperande();
        if (!this.uniteCourante.equals(";") && !this.uniteCourante.equals("]") && !this.uniteCourante.equals("..") && !this.uniteCourante.equals("repeter") && !this.uniteCourante.equals("}")) {
            String operateur = this.analyseOperateur();
            Expression operande = this.analyseOperande();
            switch (operateur) {
                case "+":
                    expression = new Somme(expression, operande);
                    break;
                case "-":
                    expression = new Soustraction(expression, operande);
                    break;
                case "*":
                    expression = new Multiplication(expression, operande);
                    break;
                default:
                    expression = new Comparaison(expression, operande, operateur);
                    break;
            }
        }
        return expression;
    }

    private String analyseOperateur() throws ErreurSyntaxique {
        if (!this.uniteCourante.equals("+") && !this.uniteCourante.equals("-") && !this.uniteCourante.equals("*")
                && !this.uniteCourante.equals("et") && !this.uniteCourante.equals("ou") && !this.uniteCourante.equals("<")
                && !this.uniteCourante.equals(">") && !this.uniteCourante.equals("=") && !this.uniteCourante.equals("#")
                && !this.uniteCourante.equals("<=") && !this.uniteCourante.equals(">="))
            throw new ErreurSyntaxique("operateur inconnu");
        else {
            String res = this.uniteCourante;
            this.nextUniteCourant();
            return res;
        }
    }

    // csteEntiere || IDF
    private Expression analyseOperande() throws ErreurSyntaxique, ErreurSemantique {
        if (this.estCsteEntiere()) {
            Nombre nombre = new Nombre(Integer.parseInt(this.uniteCourante));
            this.nextUniteCourant();
            return nombre;
        } else if (this.estIdf()) {
            return this.analyseAcces();
        } else if (this.uniteCourante.equals("-")) {
            this.analyseTerminal("-");
            return new NegationEntier(this.analyseExpression());
        } else if (this.uniteCourante.equals("non")) {
            this.analyseTerminal("non");
            return new NegationBooleen(this.analyseExpression());
        } else if (this.uniteCourante.equals("(")) {
            this.analyseTerminal("(");
            Expression res = this.analyseExpression();
            this.analyseTerminal(")");
            return res;
        } else {
            throw new ErreurSyntaxique("operande attendu");
        }
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique, ErreurSemantique {
        if (estIdf()) {
            Acces acces = this.analyseAcces();
            this.analyseTerminal(":=");
            Expression expression = this.analyseExpression();
            this.analyseTerminal(";");
            return new Affectation(acces, expression);
        } else {
            throw new ErreurSyntaxique("idf attendu");
        }
    }

    private Acces analyseAcces() throws ErreurSyntaxique, ErreurSemantique {
        String nom = this.uniteCourante;
        String type = TDS.getInstance().identifier(new Entree(nom)).getType();
        Idf idf = new Idf(this.uniteCourante);
        if(!this.currentBloc.peutAcceder(new Entree(idf.getNom()))) throw new ErreurSemantique("la variable " + idf.getNom() + " n'est pas accessible depuis ce bloc");
        if (type.equals("entier")) {
            this.nextUniteCourant();
            return idf;
        } else if (type.equals("tableau")) {
            this.nextUniteCourant();
            this.analyseTerminal("[");
            AccesTableau tableau = new AccesTableau(idf, this.analyseExpression());
            this.analyseTerminal("]");
            return tableau;
        } else {
            throw new ErreurSyntaxique("type inconnu");
        }
    }

    private void analyseTerminal(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        this.nextUniteCourant();
    }

    private boolean estIdf() {
        if (uniteCourante.equals("EOF") || uniteCourante.equals("non")) return false;
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