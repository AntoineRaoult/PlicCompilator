package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Idf extends Acces {

    public Idf(String nom) throws ErreurSemantique {
        super(nom);
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public String toMips() {
        return "    " + "lw $v0, " + this.symbole.getDeplacement() + "($s7)" + "\n";
    }

    @Override
    public String getAdresse() throws ErreurSemantique {
        return "    " + "la $a0, " + this.getDeplacement() + "($s7)" + "\n";
    }
}
