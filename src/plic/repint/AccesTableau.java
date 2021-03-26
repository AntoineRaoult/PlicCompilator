package plic.repint;

public class AccesTableau extends Acces{

    private int index;

    public AccesTableau(String nom, int index) {
        super(nom);
        this.index = index;
    }

    @Override
    public String toMips() {
        return "lw $v0, " + this.symbole.getDeplacement() + (index * -4 ) + "($s7)";
    }
}
