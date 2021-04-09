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
        if(!e1.getType().equals("entier") || !e2.getType().equals("entier")) throw new ErreurSemantique("addition de deux valeurs non-entieres");
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "";

        // a := i + b ;
        //calcul de i dans $v0
        res += e1.toMips();

        //Empiler $v0
        res += "    " + "#on empile $v0" +"\n";
        res += "    " + "sw $v0, 0($sp)" + "\n";
        res += "    " + "sub $sp, $sp, 4" + "\n";

        //calcul de b dans $v0
        res += "    " + "#calcul de b dans $v0" +"\n";
        res += e2.toMips();

        //Depiler $v0
        res += "    " + "#on depile $v0" +"\n";
        res += "    " + "add $sp, $sp, 4" + "\n";
        res += "    " + "lw $v1, 0($sp)" + "\n";

        //v0 <- $v1 +$v0
        res += "    " + "#on calcule a + b dans $v0" +"\n";
        res += "    " + "add $v0, $v0, $v1" + "\n";

        return res;
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String toString() {
        return e1 + " + " + e2;
    }
}
