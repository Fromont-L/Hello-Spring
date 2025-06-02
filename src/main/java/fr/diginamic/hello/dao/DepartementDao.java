package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    public List<Departement> findAll() {
        return em.createQuery("FROM Departement", Departement.class).getResultList();
    }

    public Departement findById(int id) {
        return em.find(Departement.class, id);
    }

    @Transactional
    public void insert(Departement departement) {
        em.persist(departement);
    }

    @Transactional
    public void update(int id, Departement modif) {
        Departement dep = em.find(Departement.class, id);
        if (dep != null) {
            dep.setNom(modif.getNom());
            dep.setCode(modif.getCode()); // si tu as ajouté le code dans Departement
        }
    }

    @Transactional
    public void delete(int id) {
        Departement dep = em.find(Departement.class, id);
        if (dep != null) {
            em.remove(dep);
        }
    }
}
