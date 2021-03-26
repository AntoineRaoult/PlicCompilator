package plic.tests;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import plic.exceptions.DoubleDeclaration;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

public class TestTDS {

    @Test (expected = DoubleDeclaration.class)
    public void test() throws DoubleDeclaration {
        TDS tds = TDS.getInstance();
        assertEquals(0,tds.getCptDepl());
        tds.ajouter(new Entree("entree1"),new Symbole("entier"));
        assertEquals(-4,tds.getCptDepl());
        tds.ajouter(new Entree("entree2"),new Symbole("entier"));
        assertEquals(-8,tds.getCptDepl());
        tds.ajouter(new Entree("entree"),new Symbole("double"));
        tds.ajouter(new Entree("entree"),new Symbole("double"));
    }
}
