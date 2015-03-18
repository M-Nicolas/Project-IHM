package controle;

import java.util.ArrayList;

import vue.Fenetre;
import vue.Rechercher;

public class Launch {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Rechercher rechercher = new Rechercher();
        ArrayList<Fenetre> liste = new ArrayList<Fenetre>();
        liste.add(rechercher);
        
        Controller controller = new Controller(liste);
        
        rechercher.setController(controller);
    }
}
