package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entities.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    // Recherche villes dont le nom commence par une chaîne donnée
    List<Ville> findByNomStartingWith(String prefix);

    // Recherche villes avec population > min, tri population desc
    List<Ville> findByPopulationGreaterThanOrderByPopulationDesc(int min);

    // Recherche villes avec population entre min et max, tri desc
    List<Ville> findByPopulationBetweenOrderByPopulationDesc(int min, int max);

    // Recherche villes d’un département avec population > min, tri desc
    List<Ville> findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(Integer departementId, int min);

    // Recherche villes d’un département avec population entre min et max, tri desc
    List<Ville> findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(Integer departementId, int min, int max);

    // Recherche top N villes les plus peuplées d’un département
    @Query("SELECT v FROM Ville v WHERE v.departement.id = :depId ORDER BY v.population DESC")
    List<Ville> findTopNVillesByDepartementId(Integer depId, Pageable pageable);
}
