package fr.diginamic.hello.service;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    public List<Departement> getAll() {
        return departementRepository.findAll();
    }

    public Departement getById(int id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département introuvable avec id " + id));
    }

    public Departement getByCode(String code) {
        return departementRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Département introuvable avec code " + code));
    }

    public void insert(Departement departement) {
        departementRepository.save(departement);
    }

    public void update(int id, Departement modif) {
        Departement existing = getById(id);
        existing.setNom(modif.getNom());
        existing.setCode(modif.getCode());
        departementRepository.save(existing);
    }

    public void delete(int id) {
        departementRepository.deleteById(id);
    }
}
