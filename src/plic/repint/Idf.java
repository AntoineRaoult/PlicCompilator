package plic.repint;

public class Idf extends Acces {

    public Idf(String nom) {
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
        return "lw $v0, " + this.symbole.getDeplacement() + "($s7)";
    }
}
