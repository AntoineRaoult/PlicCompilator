package plic.repint;

import plic.exceptions.ErreurSemantique;

public class NegationBooleen extends Expression{
    private Expression expression;

    public NegationBooleen(Expression expression) {
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        expression.verifier();
        if(!expression.getType().equals("booleen")) throw new ErreurSemantique("une negation de booleen doit etre sur un booleen");
    }

    @Override
    String toMips() {
        String res = expression.toMips();
        res += "    " + "xor $v0, $v0, 1" + "\n";
        return res;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public String toString() {
        return "non " + expression;
    }
}
