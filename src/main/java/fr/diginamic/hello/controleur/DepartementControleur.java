package fr.diginamic.hello.controleur;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.service.DepartementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    private final DepartementService departementService;

    public DepartementControleur(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public List<Departement> getAll() {
        return departementService.getAll();
    }

    @PostMapping
    public void create(@RequestBody Departement departement) {
        departementService.insert(departement);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Departement departement) {
        departementService.update(id, departement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        departementService.delete(id);
    }
}
