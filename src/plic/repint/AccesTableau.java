package plic.repint;

import plic.exceptions.ErreurSemantique;

public class AccesTableau extends Acces {

    private Idf idf;
    private Expression expression;

    public AccesTableau(Idf idf, Expression expression) throws ErreurSemantique {
        super(idf.nom);
        this.idf = idf;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return idf.nom + " [ " + expression + " ] ";
    }

    @Override
    public String toMips() throws ErreurSemantique {
        String res = "";
        //On met l'adresse de notre AccesTableau dans $a0
        res += this.getAdresse();
        res += "    " + "lw $v0, ($a0)" + "\n";
        return res;
    }

    @Override
    public String getAdresse() throws ErreurSemantique {
        String res = "    " + "#on met l'adresse de " + this.toString() + "dans $a0" + "\n";
        //place la valeur de l'expression (index pour le tableau) dans $v0
        res += "    " + "#valeur de l'expression dans $v0" + "\n";
        res += expression.toMips();

        //adresse de la base du tableau dans $a0
        res += "    " + "#adresse de la base du tableau" + "\n";
        res += idf.getAdresse();

        res += "    " + "#on place la taille du tableau dans $t0 pour comparaison" + "\n";
        res += "    " + "li $t0, " + idf.symbole.getTaille() + "\n";
        res += "    " + "bge $v0, $t0, erreur" + "\n";
        //on multiplie l'index par -4 dans $v0 pour avoir le déplacement relatif au début du tableau
        res += "    " + "#on bouge $a0 de -4 * index" + "\n";
        res += "    " + "li $t1, -4" + "\n";
        res += "    " + "mul $v0, $v0, $t1" + "\n";
        //on ajoute à $a0 le déplacement dans le tableau
        res += "    " + "add $a0, $v0, $a0" + "\n";
        return res;
    }
}
