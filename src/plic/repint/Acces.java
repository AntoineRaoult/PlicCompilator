package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Acces extends Expression {
    protected String nom;
    protected Symbole symbole;
    protected int adresse;

    public Acces(String nom) {
        this.nom = nom;
        this.symbole = TDS.getInstance().identifier(new Entree(nom));
    }

    @Override
    void verifier() throws ErreurSemantique {
        if (this.symbole == null) throw new ErreurSemantique("IDF non existant : " + nom);
    }

    public String getAdresse() {
        return this.adresse + "($s7)";
    }

    public String getType() {
        return this.symbole.getType();
    }

    public abstract String toMips();
}
