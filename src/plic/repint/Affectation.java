package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Affectation extends Instruction {
    private Acces acces;
    private Expression expression;

    public Affectation(Acces acces, Expression expression) {
        this.acces = acces;
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        acces.verifier();
        expression.verifier();
    }

    @Override
    public String toString() {
        String res = "    Affectation : " + acces + " := " + expression + "\n";
        return res;
    }

    @Override
    String toMips() {
        String res = "    #Affectation.toMips() " + this.toString() + "\n";
        res += "    la $a0, " + acces.getAdresse() +"\n";
        res += "    " + expression.toMips() + "\n";
        res += "    sw $v0, ($a0)\n";
        res += "\n";
        return res;
    }
}
