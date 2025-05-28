package fr.diginamic.hello.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {
    @Min(value = 1, groups = ModeModification.class)
    private int id;
    @NotNull
    @Size(min = 2, groups = {ModeCreation.class, ModeCreation.class})
    private String nom;
    @Min(value = 1, groups = {ModeCreation.class, ModeCreation.class})
    private int nbHabitants;

    public Ville(int id, String nom, int nbHabitants) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }
}
