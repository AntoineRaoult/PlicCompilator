package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Lire extends Instruction{

    private Idf idf;

    public Lire(Idf idf) {
        this.idf = idf;
    }

    @Override
    void verifier() throws ErreurSemantique {

    }

    @Override
    String toMips() {
        return null;
    }

    @Override
    public String toString() {
        return "    " + "Lire : " + idf + "\n";
    }
}
