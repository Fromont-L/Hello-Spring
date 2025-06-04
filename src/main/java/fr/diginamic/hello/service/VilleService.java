package fr.diginamic.hello.service;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.exceptions.VilleException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    private final VilleDao villeDao;

    private void validerVille(Ville ville) throws FunctionalException {
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            throw new FunctionalException("Le nom de la ville doit contenir au moins 2 lettres");
        }
        if (ville.getPopulation() == null || ville.getPopulation() < 10) {
            throw new FunctionalException("La ville doit avoir au moins 10 habitants");
        }
        if (ville.getDepartement() == null || ville.getDepartement().getCode() == null
                || ville.getDepartement().getCode().length() != 2) {
            throw new FunctionalException("Le code du département doit contenir exactement 2 caractères");
        }

        List<Ville> toutesLesVilles = villeDao.findAll();
        boolean existeDeja = toutesLesVilles.stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom())
                        && v.getDepartement().getCode().equalsIgnoreCase(ville.getDepartement().getCode())
                        && (ville.getId() == null || !ville.getId().equals(v.getId())));

        if (existeDeja) {
            throw new FunctionalException("Une ville avec ce nom existe déjà dans ce département");
        }
    }


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

    public List<Ville> insertVille(Ville ville) throws VilleException, FunctionalException {
        validerVille(ville);
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
