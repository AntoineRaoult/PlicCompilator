package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Affectation extends Instruction {
    private Acces acces;
    private Expression expression;

    public Affectation(Acces acces, Expression expression) {
        this.acces = acces;
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        acces.verifier();
        expression.verifier();
        if(expression.getType().equals("booleen")) throw new ErreurSemantique("dans une affectation, le membre de droite doit être de type entier");
    }

    @Override
    public String toString() {
        String res = "    Affectation : " + acces + " := " + expression + "\n";
        return res;
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "    #Affectation.toMips() " + this.toString();
        //Code qui calcule la valeur de l’expression dans $v0
        res += expression.toMips();

        //Empiler $v0
        res += "    " + "sw $v0, 0($sp)" + "\n";
        res += "    " + "sub $sp, $sp, 4" + "\n";

        //Code qui calcule l’adresse de l’accès dans $a0
        res += acces.getAdresse();

        //Depiler $v0
        res += "    " + "add $sp, $sp, 4" + "\n";
        res += "    " + "lw $v0, 0($sp)" + "\n";

        //Ranger $v0 à l’adresse contenue dans $a0
        res += "    " + "sw $v0, ($a0)" + "\n";

        res += "\n";
        return res;
    }
}
