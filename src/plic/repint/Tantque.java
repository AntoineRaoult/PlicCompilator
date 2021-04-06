package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Tantque extends Iteration {

    private Expression condition;

    public Tantque(Expression condition, Bloc bloc) {
        super(bloc);
        this.condition = condition;
    }

    @Override
    void verifier() throws ErreurSemantique {
        super.verifier();
        condition.verifier();
    }

    @Override
    String toMips() {
        String res = "    " + "#Tantque " + this.condition.toString() + "\n";
        res += this.labelAvant + ":" + "\n";
        res += this.condition.toMips();
        res += "    " + "bne $v0, 1, " + this.labelApres + "\n\n";

        res += bloc.toMips();
        res += "    " + "b " + this.labelAvant + "\n\n";

        res += this.labelApres + ":" + "\n\n";

        return res;
    }
}
