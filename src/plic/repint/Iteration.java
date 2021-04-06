package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Iteration extends Instruction{

    private static int compteurIterations = 0;

    protected String labelAvant, labelApres;

    protected Bloc bloc;

    public Iteration(Bloc bloc) {
        this.bloc = bloc;
        this.labelAvant = "iteration" + compteurIterations;
        this.labelApres = "endIteration" + compteurIterations;
        compteurIterations++;
    }

    @Override
    void verifier() throws ErreurSemantique {
        bloc.verifier();
    }
}
