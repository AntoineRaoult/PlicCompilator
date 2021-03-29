package plic.repint;

import plic.exceptions.DoubleDeclaration;

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

    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (this.map.containsKey(e)) throw new DoubleDeclaration("Entree déjà existante");
        else {
            s.setDeplacement(this.cptDepl);
            this.cptDepl -= s.getTaille() * 4;
            this.map.put(e, s);
        }
    }

    public Symbole identifier(Entree e) {
        for (Entree entree : this.map.keySet()) {
            if (entree.equals(e)) return this.map.get(entree);
        }
        return null;
    }

    public String toString() {
        String res = "Declarations : \n";
        for (Entree entree : this.map.keySet()) {
            res += "    " + entree + " : " +  this.identifier(entree).getType() + "\n";
        }
        return res;
    }

    public int getCptDepl() {
        return cptDepl;
    }
}
