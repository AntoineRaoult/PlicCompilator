package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Pour extends Iteration {

    private Idf idf;
    private Expression e1, e2;

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
        if (e1.getType().equals("booleen")) throw new ErreurSemantique("expression entiere attendure pour e1");
        if (e2.getType().equals("booleen")) throw new ErreurSemantique("expression entiere attendure pour e2");
    }

    @Override
    String toMips() throws ErreurSemantique {
        String res = "    " + "#Pour " + this.idf.toString() + "\n";

        //valeur de départ dans l'idf
        res += e1.toMips();
        res += idf.getAdresse();
        res += "    " + "sw $v0, ($a0)" + "\n";

        //Label avant
        res += this.labelAvant + ":" + "\n";

        //On place la variable d'iteration dans $v1
        res += idf.toMips();
        res += "    " + "move $v1, $v0" + "\n";

        //On place l'expression d'arrivee + 1 dans $v0
        res += e2.toMips();
        res += "    " + "add $v0, $v0, 1" + "\n";

        //Test si fin de boucle
        res += "    " + "beq $v0, $v1, " + this.labelApres + "\n";

        //Execution du code du pour
        res += bloc.toMips();

        //On place la valeur de la var d'iteration dans $v0 et son adresse dans $a0
        res += idf.getAdresse();
        res += idf.toMips();

        //On ajoute 1 (chaque tour de boucle) et on save
        res += "    " + "add $v0, $v0, 1" + "\n";
        res += "    " + "sw $v0, ($a0)" + "\n";

        //On remonte au label pour refaire le test
        res += "    " + "b " + this.labelAvant + "\n";

        //Label apres
        res += this.labelApres + ":" + "\n";

        //On enleve 1 a la var d'iteration car on a du aller à +1 pour le test
        res += idf.getAdresse();
        res += idf.toMips();
        res += "    " + "sub $v0, $v0, 1" + "\n";
        res += "    " + "sw $v0, ($a0)" + "\n";

        return res;
    }
}
