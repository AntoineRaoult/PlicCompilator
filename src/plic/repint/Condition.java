package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Condition extends Instruction {

    private static int compteurCondition = 0;

    private String labelTrue, labelEnd;

    private Expression condition;
    private Bloc alors, sinon;

    public Condition(Expression condition, Bloc alors, Bloc sinon) {
        this.condition = condition;
        this.alors = alors;
        this.sinon = sinon;

        this.labelTrue = "conditionTrue" + compteurCondition;
        this.labelEnd = "conditionEnd" + compteurCondition;
        compteurCondition++;
    }

    public Condition(Expression condition, Bloc alors) {
        this.condition = condition;
        this.alors = alors;
        this.sinon = null;
    }

    @Override
    void verifier() throws ErreurSemantique {
        condition.verifier();
        alors.verifier();
        sinon.verifier();
        if (condition.getType().equals("entier")) throw new ErreurSemantique("expression booleene attendue dans un if");
    }

    @Override
    String toMips() {
        String res = "    " + "#Si " + this.condition.toString() + "\n";
        res += condition.toMips();
        res += "    " + "beq $v0, 1, " + this.labelTrue + "\n";
        if (sinon != null) res += sinon.toMips();
        res += "    " + "b " + this.labelEnd + "\n";
        res += this.labelTrue + ":" + "\n";
        res += alors.toMips();
        res += this.labelEnd + ":" + "\n\n";
        return res;
    }

    @Override
    public String toString() {
        String res = "    Si " + condition;
        res += " alors\n" + alors;
        if (sinon != null) res += "\n    sinon\n" + sinon + "\n";
        return res;
    }
}
