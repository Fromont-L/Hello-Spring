package fr.diginamic.hello.controleur;

import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleRepository villeRepository;

    // url : http://localhost:8080/villes
    @GetMapping
    public List<Ville> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "1001") int size) {
        return villeRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    // url : http://localhost:8080/villes/nom/Na
    @GetMapping("/nom/{prefix}")
    public List<Ville> getVillesByNomPrefix(@PathVariable String prefix) {
        return villeRepository.findByNomStartingWith(prefix);
    }

    // url : http://localhost:8080/villes/population/sup/1000000
    @GetMapping("/population/sup/{min}")
    public List<Ville> getVillesByPopulationMin(@PathVariable int min) {
        return villeRepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
    }

    // url : http://localhost:8080/villes/population/between?min=1000&max=5000
    @GetMapping("/population/between")
    public List<Ville> getVillesByPopulationBetween(@RequestParam int min,
                                                    @RequestParam int max) {
        return villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
    }

    // url : http://localhost:8080/villes/departement/11/population/sup/1234
    @GetMapping("/departement/{depId}/population/sup/{min}")
    public List<Ville> getVillesByDepartementAndMinPop(@PathVariable Integer depId,
                                                       @PathVariable int min) {
        return villeRepository.findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(depId, min);
    }

    // url : http://localhost:8080/villes/departement/11/population/between?min=11111&max=66666
    @GetMapping("/departement/{depId}/population/between")
    public List<Ville> getVillesByDepartementAndPopBetween(@PathVariable Integer depId,
                                                           @RequestParam int min,
                                                           @RequestParam int max) {
        return villeRepository.findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(depId, min, max);
    }

    // url : http://localhost:8080/villes/departement/1/top
    @GetMapping("/departement/{depId}/top")
    public List<Ville> getTopNVillesByDepartement(@PathVariable Integer depId,
                                                  @RequestParam(defaultValue = "5") int n) {
        return villeRepository.findTopNVillesByDepartementId(depId, PageRequest.of(0, n));
    }
}
