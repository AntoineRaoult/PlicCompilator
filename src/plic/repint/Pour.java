package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Pour extends Iteration {

    private Idf idf;
    private Expression e1,e2;

    public Pour(Idf idf, Expression e1, Expression e2, Bloc bloc) {
        super(bloc);
        this.idf = idf;
        this.e1 = e1;
        this.e2 = e2;
    }


    @Override
    void verifier() throws ErreurSemantique {
        super.verifier();
        idf.verifier();
        e1.verifier();
        e2.verifier();
        if(e1.getType().equals("booleen")) throw new ErreurSemantique("expression entiere attendure pour e1");
        if(e2.getType().equals("booleen")) throw new ErreurSemantique("expression entiere attendure pour e2");
    }

    @Override
    String toMips() {
        return null;
    }
}
