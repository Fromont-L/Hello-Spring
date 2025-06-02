package fr.diginamic.hello.service;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.entities.Ville;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    private final VilleDao villeDao;

    public VilleService(VilleDao villeDao) {
        this.villeDao = villeDao;
    }

    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    public Ville extractVille(int idVille) {
        return villeDao.findById(idVille);
    }

    public Ville extractVille(String nom) {
        return villeDao.findByNom(nom);
    }

    public List<Ville> insertVille(Ville ville) {
        villeDao.insert(ville);
        return villeDao.findAll();
    }

    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        villeDao.update(idVille, villeModifiee);
        return villeDao.findAll();
    }

    public List<Ville> supprimerVille(int idVille) {
        villeDao.delete(idVille);
        return villeDao.findAll();
    }
}
