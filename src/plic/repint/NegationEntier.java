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
    }

    @Override
    String toMips() {
        String res = expression.toMips();
        res += "    " + "neg $v0, $v0" + "\n";
        return res;
    }

    @Override
    public String getType() {
        return "entier";
    }
}
