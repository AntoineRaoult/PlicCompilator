package plic.repint;

import plic.exceptions.ErreurSemantique;

public class NegationEntier extends Expression{

    private Expression expression;

    public NegationEntier(Expression expression) {
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        expression.verifier();
        if(!expression.getType().equals("entier")) throw new ErreurSemantique("une negation d'entier doit etre sur un entier");
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = expression.toMips();
        res += "    " + "neg $v0, $v0" + "\n";
        return res;
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String toString() {
        return "- " + expression;
    }
}
