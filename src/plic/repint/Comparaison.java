package plic.repint;

import plic.exceptions.ErreurSemantique;
import plic.exceptions.ErreurSyntaxique;

public class Comparaison extends Expression {

    private Expression e1, e2;

    private String typeComparaison;

    public Comparaison(Expression e1, Expression e2, String typeComparaison) {
        this.e1 = e1;
        this.e2 = e2;
        this.typeComparaison = typeComparaison;
    }

    @Override
    void verifier() throws ErreurSemantique {
        this.e1.verifier();
        this.e2.verifier();
        if(typeComparaison.equals("et") || typeComparaison.equals("ou")) {
            if(this.e1.getType().equals("entier") || this.e2.getType().equals("entier")) throw new ErreurSemantique("et / ou avec un membre entier");
        } else {
            if(this.e1.getType().equals("booleen") || this.e2.getType().equals("booleen")) throw new ErreurSemantique("comparaison d'entiers avec un membre booleen");
        }
    }

    @Override
    String toMips() {
        String res = "";

        //calcul de e1 dans $v0
        res += e1.toMips();

        //Empiler $v0
        res += "    " + "#on empile $v0" +"\n";
        res += "    " + "sw $v0, 0($sp)" + "\n";
        res += "    " + "sub $sp, $sp, 4" + "\n";

        //calcul de e2 dans $v0
        res += "    " + "#calcul de b dans $v0" +"\n";
        res += e2.toMips();

        //Depiler $v0 dans $v1
        res += "    " + "#on depile $v0" +"\n";
        res += "    " + "add $sp, $sp, 4" + "\n";
        res += "    " + "lw $v1, 0($sp)" + "\n";
        //on a e1 dans $v1 et e2 dans $v0

        res += "    " + getMipsComparaison() + " $v0, $v0, $v1" + "\n";

        return res;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    private String getMipsComparaison() {
        String res;
        switch (this.typeComparaison) {
            case "=":
                res = "seq";
                break;
            case "#":
                res = "sne";
                break;
            case "<":
                res = "slt";
                break;
            case "<=":
                res = "sle";
                break;
            case ">":
                res = "sgt";
                break;
            case ">=":
                res = "sge";
                break;
            case "ou":
                res = "or";
                break;
            case "et":
                res = "and";
                break;
            default:
                //normalement impossible d'arriver ici
                res = "ERREUR";
                break;
        }
        return res;
    }
}
