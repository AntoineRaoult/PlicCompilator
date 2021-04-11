package plic.repint;

import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSemantique;

import java.util.ArrayList;

public class Bloc {

    private Bloc parent;

    private ArrayList<Declaration> declarations;

    private ArrayList<Instruction> instructions;

    public Bloc(Bloc parent) {
        this.parent = parent;
        this.declarations = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public void ajouterDeclaration(Declaration d) throws DoubleDeclaration {
        TDS.getInstance().ajouter(this, d.getEntree(),d.getSymbole());
        this.declarations.add(d);
    }

    public void ajouterInstruction(Instruction i) {
        this.instructions.add(i);
    }

    public void verifier() throws ErreurSemantique {
        for (Instruction instruction : instructions) {
            instruction.verifier();
        }
    }

    public boolean possede(Entree e) {
        for (Declaration declaration : declarations) {
            if(declaration.getEntree().equals(e)) return true;
        }
        return false;
    }

    public boolean peutAcceder(Entree e) {
        if(this.possede(e)) return true;
        else {
            if(this.parent == null) return false;
            else {
                return this.parent.peutAcceder(e);
            }
        }
    }

    public Bloc getParent() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Bloc : \n");
        for (Instruction instruction : instructions) {
            res.append(instruction.toString());
        }
        return res.substring(0,res.length()-1);
    }

    public String toMips() throws ErreurSemantique {
        StringBuilder res = new StringBuilder();
        for (Instruction instruction : instructions) {
            res.append(instruction.toMips());
        }
        return res.toString();
    }
}
