package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.ArrayList;

public class Bloc {
    private ArrayList<Instruction> instructions;

    public Bloc() {
        this.instructions = new ArrayList<>();
    }

    public void ajouter(Instruction i) {
        this.instructions.add(i);
    }

    public void verifier() throws ErreurSemantique {
        for (Instruction instruction : instructions) {
            instruction.verifier();
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Bloc : \n");
        for (Instruction instruction : instructions) {
            res.append(instruction.toString());
        }
        return res.substring(0,res.length()-1);
    }

    public String toMips() {
        StringBuilder res = new StringBuilder(".text\nmain :\n    move $s7, $sp\n    add $sp, $sp, " + TDS.getInstance().getCptDepl()).append("\n\n");
        for (Instruction instruction : instructions) {
            res.append(instruction.toMips());
        }
        res.append("end :\n    li $v0, 10\n    syscall");
        return res.toString();
    }
}
