package tn.esprit.applicatiopnpi.test;

import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.services.PoliceService;
import tn.esprit.applicatiopnpi.services.SinistreService;
import tn.esprit.applicatiopnpi.utils.MyDatabase;

public class Main {
    public static void main(String[] args) {
        MyDatabase db = MyDatabase.getInstance();
        System.out.println(db);

        // Création et ajout d'un sinistre
        Sinistre sinistre = new Sinistre(29, "Incendie Domestique", "Dégâts causés par un incendie dans une cuisine.", "image_path.jpg");
        SinistreService sinistreService = new SinistreService();
        sinistreService.add(sinistre);

        // Création et ajout d'une police associée au sinistre
        Police police = new Police(29, "Assurance Incendie", "Couverture complète contre les incendies", sinistre);
        PoliceService policeService = new PoliceService();
        policeService.add(police);

        // Affichage de tous les sinistres
        sinistreService.getAll().forEach(System.out::println);
        policeService.getAll().forEach(System.out::println);
    }
}
