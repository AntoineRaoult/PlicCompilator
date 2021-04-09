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
    public String toString() {
        return "    " + "Lire : " + idf + "\n";
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "    " + "#Lire " + this.idf.toString() + "\n";
        res += "    " + "li $v0, 5" + "\n";
        res += "    " + "syscall" + "\n";

        res += idf.getAdresse();
        res += "    " + "sw $v0, ($a0)" + "\n\n";

        return res;
    }
}
