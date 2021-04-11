package plic.repint;

public class Declaration {
    private Entree entree;
    private Symbole symbole;

    public Declaration(Entree entree, Symbole symbole) {
        this.entree = entree;
        this.symbole = symbole;
    }

    public Entree getEntree() {
        return entree;
    }

    public Symbole getSymbole() {
        return symbole;
    }
}
