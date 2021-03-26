package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Nombre extends Expression {
    private int val;

    public Nombre(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

    public int getVal() {
        return val;
    }

    @Override
    void verifier() throws ErreurSemantique {
        //juste un nombre, rien a verifier
    }

    @Override
    String toMips() {
        return "li $v0, " + String.valueOf(val);
    }
}
