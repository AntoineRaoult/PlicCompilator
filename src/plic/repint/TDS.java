package plic.repint;

import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSemantique;

import java.util.HashMap;

public class TDS {

    private int cptDepl;

    private static TDS instance;

    private HashMap<Entree, Symbole> map;

    private TDS() {
        this.cptDepl = 0;
        this.map = new HashMap<>();
    }

    public synchronized static TDS getInstance() {
        if (TDS.instance == null) TDS.instance = new TDS();
        return TDS.instance;
    }

    public static void resetTDS() {
        TDS.getInstance().map = new HashMap<>();
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (this.map.containsKey(e)) throw new DoubleDeclaration("Entree déjà existante");
        else {
            s.setDeplacement(this.cptDepl);
            this.cptDepl -= s.getTaille() * 4;
            this.map.put(e, s);
        }
    }

    public Symbole identifier(Entree e) throws ErreurSemantique {
        for (Entree entree : this.map.keySet()) {
            if (entree.equals(e)) return this.map.get(entree);
        }
        throw new ErreurSemantique("variable non déclarée");
    }

    public String toString() {
        String res = "Declarations : \n";
        for (Entree entree : this.map.keySet()) {
            try {
                res += "    " + entree + " : " +  this.identifier(entree).getType() + "\n";
            } catch (ErreurSemantique erreurSemantique) {
                erreurSemantique.printStackTrace();
            }
        }
        return res;
    }

    public int getCptDepl() {
        return cptDepl;
    }
}
