package plic.repint;

import java.util.Objects;

public class Symbole {
    private String type;
    private int deplacement;
    private int taille;

    public Symbole(String type) {
        this.type = type;
        this.taille = 1;
    }

    public Symbole(String type, int taille) {
        this.type = type;
        this.taille = taille;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public String getType() {
        return type;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbole symbole = (Symbole) o;
        return deplacement == symbole.deplacement && Objects.equals(type, symbole.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, deplacement);
    }
}
