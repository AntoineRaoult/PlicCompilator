package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Somme extends Expression{
    private Expression e1,e2;

    public Somme(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    void verifier() throws ErreurSemantique {
        e1.verifier();
        e2.verifier();
    }

    @Override
    String toMips() {
        String res = "    " + "#somme de " + e1 + " et " + e2;

        // a := i + b ;
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
        res += "    " + "add $v0, $v0, $v1" + "\n";

        return res;
    }

    @Override
    String getType() {
        return "entier";
    }
}
