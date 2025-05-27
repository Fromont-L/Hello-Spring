package fr.diginamic.hello.controleur;

import fr.diginamic.hello.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("villes")
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>();

    public VilleControleur() {
        villes.add(new Ville("Thézan-des-Corbières", 560));
        villes.add(new Ville("Villerouge la Crémade", 140));
        villes.add(new Ville("Montquc", 1241));
        villes.add(new Ville("Anus", 130));
        villes.add(new Ville("Mouais", 356));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
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

}
