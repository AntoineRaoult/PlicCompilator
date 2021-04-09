package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Instruction {
    abstract void verifier() throws ErreurSemantique;
    abstract String toMips() throws ErreurSemantique;
}
