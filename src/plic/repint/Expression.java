package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Expression {
    abstract void verifier() throws ErreurSemantique;
    abstract String toMips();
}
