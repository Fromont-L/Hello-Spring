package fr.diginamic.hello.controleur;

import fr.diginamic.hello.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {
        return Arrays.asList(
                new Ville("Thézan-des-Corbières", 560),
                new Ville("Villerouge la Crémade", 140),
                new Ville("Montquc", 1241),
                new Ville("Anus", 130),
                new Ville("Mouais", 356)
        );
    }
}
