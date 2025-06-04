package fr.diginamic.hello.controleur;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    // url : http://localhost:8080/villes
    @GetMapping
    public List<Ville> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "1001") int size) {
        return villeRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    // url : http://localhost:8080/villes/nom/Na
    @GetMapping("/nom/{prefix}")
    public List<Ville> getVillesByNomPrefix(@PathVariable String prefix) throws FunctionalException {
        List<Ville> villes = villeRepository.findByNomStartingWith(prefix);
        if (villes.isEmpty()) {
            throw new FunctionalException("Aucune ville dont le nom commence par " + prefix + " n’a été trouvée");
        }
        return villes;
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

    @PostMapping
    public ResponseEntity<?> createVille(@RequestBody Ville ville) {
        try {
            Integer maxId = villeRepository.findMaxId();
            if (maxId == null) maxId = 0;
            ville.setId(maxId + 1);
            Ville savedVille = villeRepository.save(ville);
            return new ResponseEntity<>(savedVille, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de la ville: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Permet de faire un export au format CSV
    @GetMapping("/export/csv")
    public void exportCsv(@RequestParam(name = "minPopulation", defaultValue = "0") int minPopulation, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=villes.csv");

        List<Ville> villes = villeRepository.findByPopulationGreaterThan(minPopulation);

        PrintWriter writer = response.getWriter();
        writer.println("Nom,Ville,Population,Code Département,Nom Département,");

        for (Ville ville : villes) {
            Departement dep = ville.getDepartement();
            writer.printf("%s,%d,%s,%s%n",
                ville.getNom(),
            ville.getPopulation(),
            dep != null ? dep.getCode() : "",
            dep != null ? dep.getNom() : ""
            );
        }

        writer.flush();
        writer.close();
    }

    // Permet de faire un export au format PDF
    @GetMapping("/export/pdf/{code}")
    public void exportPdf(@PathVariable String code, HttpServletResponse response) throws Exception {
        Departement departement = departementRepository.findByCode(code).orElseThrow(() -> new FunctionalException("Département introuvable : " + code));

        List<Ville> villes = villeRepository.findByDepartement(departement);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=departement_" + code + ".pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        document.add(new Paragraph("Département : " + departement.getNom(), titleFont));
        document.add(new Paragraph("Code : " + departement.getCode()));
        document.add(new Paragraph("Nom : " + departement.getNom()));
        document.add(new Paragraph(" "));

        for (Ville ville : villes) {
            document.add(new Paragraph("- " + ville.getNom() + " (Population : " + ville.getPopulation() + ")"));
        }

        document.close();
    }

}
