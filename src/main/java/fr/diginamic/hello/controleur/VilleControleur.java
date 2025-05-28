package fr.diginamic.hello.controleur;

import fr.diginamic.hello.entities.ModeCreation;
import fr.diginamic.hello.entities.Ville;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Optional<Ville> ville = villes.stream().filter(v -> v.getId() == id).findFirst();

        return ville.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<String> ajouterVille(@Validated(ModeCreation.class) @Valid @RequestBody Ville nouvelleVille, BindingResult result) {
        boolean existe = villes.stream().anyMatch(v -> v.getNom().equalsIgnoreCase(nouvelleVille.getNom()));
        if (result.hasErrors()) {
            String message = result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(",\n"));
            return ResponseEntity.badRequest().body(message);
        }
        if (existe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ville est déjà existante");
        }

        villes.add(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    @Autowired
    private Validator validator;

    @PutMapping
    public ResponseEntity<String> modifierVille(@Validated(ModeCreation.class) @RequestBody Ville majVille, BindingResult result) {
       if (result.hasErrors()) {
           String message = result.getFieldErrors().stream().map(e -> e.getField() + " " + e.getDefaultMessage()).collect(Collectors.joining(", "));
           return ResponseEntity.badRequest().body("Erreurs de validation : " + message);
       }

       if (result.hasErrors()) {
           String message = result.getFieldErrors().stream().map(error -> error.getField() + " : " + error.getDefaultMessage()).collect(Collectors.joining(",\n"));
           return ResponseEntity.badRequest().body("Erreurs de validation : \n" + message);
       }

       for (Ville ville : villes) {
           if (ville.getId() == majVille.getId()) {
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
