package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Ecrire extends Instruction {
    private Expression expression;

    public Ecrire(Expression expression) {
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        expression.verifier();
    }

    @Override
    public String toString() {
        String res = "    Ecrire : " + expression + "\n";
        return res;
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "    #Ecrire.toMips() " + this.toString();
        res += expression.toMips();  //val de l'expr dans $v0
        res += "    " + "move $a0, $v0" + "\n"; //on bouge dans $a0 pour affichage
        res += "    " + "li $v0, 1" + "\n";
        res += "    syscall\n";
        res += "    " + "li $v0, 4" + "\n";
        res += "    " + "la $a0, retourLigne" + "\n";
        res += "    " + "syscall" + "\n\n";
        return res;
    }
}
