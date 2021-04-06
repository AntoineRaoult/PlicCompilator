package plic.repint;

import plic.exceptions.ErreurSemantique;

public class Programme {

    private Bloc bloc;

    public Programme(Bloc bloc) {
        this.bloc = bloc;
    }

    public void verifier() throws ErreurSemantique {
        bloc.verifier();
    }

    public String toMips() {
        StringBuilder res = new StringBuilder(".data\n    erreurDebordement: .asciiz \"ERREUR: debordement tableau\"\n    retourLigne: .asciiz \"\\n\"\n.text\nmain :\n    move $s7, $sp\n    add $sp, $sp, " + TDS.getInstance().getCptDepl()).append("\n\n");
        res.append(bloc.toMips());
        res.append("b apreserreur\nerreur :\n    li $v0, 4\n    la $a0, erreurDebordement\n    syscall\napreserreur:\n\n");
        res.append("end :\n    li $v0, 10\n    syscall");
        return res.toString();
    }

    @Override
    public String toString() {
        return bloc.toString() + "\n";
    }
}
