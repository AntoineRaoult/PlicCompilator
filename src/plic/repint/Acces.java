package plic.repint;

import plic.exceptions.ErreurSemantique;

public abstract class Acces extends Expression {
    protected String nom;
    protected Symbole symbole;
    protected int adresse;

    public Acces(String nom) throws ErreurSemantique {
        this.nom = nom;
        this.symbole = TDS.getInstance().identifier(new Entree(nom));
    }

    @Override
    void verifier() throws ErreurSemantique {
        if (this.symbole == null) throw new ErreurSemantique("IDF non existant : " + nom);
    }

    @Override
    public String getType() {
        return this.symbole.getType();
    }

    public abstract String toMips() throws ErreurSemantique;

    public String getDeplacement() throws ErreurSemantique {
        return String.valueOf(TDS.getInstance().identifier(new Entree(nom)).getDeplacement());
    }

    public abstract String getAdresse() throws ErreurSemantique;
}
