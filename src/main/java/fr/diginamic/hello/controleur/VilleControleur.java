package fr.diginamic.hello.controleur;

import fr.diginamic.hello.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("villes")
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>();

    public VilleControleur() {
        villes.add(new Ville(1,"Thézan-des-Corbières", 560));
        villes.add(new Ville(2,"Villerouge la Crémade", 140));
        villes.add(new Ville(3,"Montquc", 1241));
        villes.add(new Ville(4,"Anus", 130));
        villes.add(new Ville(5,"Mouais", 356));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Optional<Ville> ville = villes.stream().filter(v -> v.getId() == id).findFirst();

        return ville.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) {
        boolean existe = villes.stream().anyMatch(v -> v.getNom().equalsIgnoreCase(nouvelleVille.getNom()));
        if (existe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ville est déjà existante");
        }

        villes.add(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville majVille) {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                ville.setNom(majVille.getNom());
                ville.setNbHabitants(majVille.getNbHabitants());
                return ResponseEntity.ok("Ville modifiée avec succès");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        boolean supprimed = villes.removeIf(ville -> ville.getId() == id);
        if (supprimed) {
            return ResponseEntity.ok("Ville supprimée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
        }

    }

}
