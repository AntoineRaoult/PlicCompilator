package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Soustraction extends Expression{
    private Expression e1,e2;

    public Soustraction(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    void verifier() throws ErreurSemantique {
        e1.verifier();
        e2.verifier();
        if(!e1.getType().equals("entier") || !e2.getType().equals("entier")) throw new ErreurSemantique("soustraction de deux valeurs non-entieres");
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "    " + "#soustraction de " + e1 + " et " + e2 + "\n";

        // a := i - b ;
        //calcul de i dans $v0
        res += e1.toMips();

        //Empiler $v0
        res += "    " + "sw $v0, 0($sp)" + "\n";
        res += "    " + "sub $sp, $sp, 4" + "\n";

        //calcul de b dans $v0
        res += e2.toMips();

        //Depiler $v0
        res += "    " + "add $sp, $sp, 4" + "\n";
        res += "    " + "lw $v1, 0($sp)" + "\n";

        //v0 <- $v1 +$v0
        res += "    " + "sub $v0, $v1, $v0" + "\n";

        return res;
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String toString() {
        return e1 + " - " + e2;
    }
}
