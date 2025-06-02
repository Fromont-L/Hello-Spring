package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    public Ville findById(int id) {
        return em.find(Ville.class, id);
    }

    public Ville findByNom(String nom) {
        try {
            return em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void insert(Ville ville) {
        em.persist(ville);
    }

    @Transactional
    public void update(int id, Ville villeModifiee) {
        Ville ville = em.find(Ville.class, id);
        if (ville != null) {
            ville.setNom(villeModifiee.getNom());
            ville.setPopulation(villeModifiee.getPopulation());
            ville.setDepartement(villeModifiee.getDepartement());
        }
    }

    @Transactional
    public void delete(int id) {
        Ville ville = em.find(Ville.class, id);
        if (ville != null) {
            em.remove(ville);
        }
    }

    public List<Ville> trouverTopVilleDepartement(int idDepartement, int n) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :id ORDER BY v.nbHabitants DESC", Ville.class)
                .setParameter("id", idDepartement)
                .setMaxResults(n)
                .getResultList();
    }

    public List<Ville> trouverRangePopulationDansDepartement(int idDepartement, int min, int max) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.departement.id = :id AND v.nbHabitants BETWEEN :min AND :max ORDER BY v.nbHabitants DESC", Ville.class)
                .setParameter("id", idDepartement)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }
}
