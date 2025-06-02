package fr.diginamic.hello.service;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entities.Departement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {
    private final DepartementDao departementDao;

    public DepartementService(DepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public List<Departement> getAll() {
        return departementDao.findAll();
    }

    public Departement getById(int id) {
        return departementDao.findById(id);
    }

    public void insert(Departement departement) {
        departementDao.insert(departement);
    }

    public void update(int id, Departement departement) {
        departementDao.update(id, departement);
    }

    public void delete(int id) {
        departementDao.delete(id);
    }
}
