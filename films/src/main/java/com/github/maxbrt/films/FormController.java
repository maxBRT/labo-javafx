package com.github.maxbrt.films;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.Genre;
import com.github.maxbrt.films.repository.ContenuRepository;
import com.github.maxbrt.films.repository.GenreRepository;
import com.github.maxbrt.films.utils.HibernateUtil;

public class FormController {

    @FXML
    private TextField titreField;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private TextField anneeSortieField;
    @FXML
    private TextField realisateurField;
    @FXML
    private TextArea synopsisField;
    @FXML
    private TextField imageUrlField;
    @FXML
    private ComboBox<String> statutField;
    @FXML
    private TextField noteField;
    @FXML
    private CheckBox watchlistField;
    @FXML
    private ComboBox<Genre> genreField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    private ContenuRepository contenuRepo;
    private GenreRepository genreRepo;
    private Contenu editingContenu;
    private Runnable onSaveComplete;
    private Runnable onCancel;

    @FXML
    public void initialize() {
        contenuRepo = new ContenuRepository(HibernateUtil.getSessionFactory(), Contenu.class);
        genreRepo = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);
        List<Genre> genres = genreRepo.findAll();

        typeField.getItems().addAll("Film", "Série");
        statutField.getItems().addAll("A voir", "En cours", "Vu");
        genreField.getItems().addAll(genres);
    }

    public void setOnSaveComplete(Runnable onSaveComplete) {
        this.onSaveComplete = onSaveComplete;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void editContenu(Contenu c) {
        editingContenu = c;

        titreField.setText(c.getTitre());
        typeField.setValue(c.getType());
        genreField.setValue(c.getGenre());
        anneeSortieField.setText(c.getAnneeSortie() > 0 ? String.valueOf(c.getAnneeSortie()) : "");
        realisateurField.setText(c.getRealisateur());
        synopsisField.setText(c.getSynopsis());
        imageUrlField.setText(c.getImageUrl());
        statutField.setValue(c.getStatut());
        noteField.setText(c.getNote() > 0 ? String.valueOf(c.getNote()) : "");
        watchlistField.setSelected(c.isWatchlist());

        saveBtn.setText("Modifier");
        cancelBtn.setVisible(true);
        cancelBtn.setManaged(true);
        messageLabel.setText("");
    }

    public String getFormTabTitle() {
        return editingContenu != null ? "Modifier" : "Ajouter";
    }

    @FXML
    private void handleSave() {
        messageLabel.setStyle("-fx-text-fill: red;");

        if (titreField.getText() == null || titreField.getText().isBlank()) {
            messageLabel.setText("Le titre est obligatoire.");
            return;
        }
        if (typeField.getValue() == null) {
            messageLabel.setText("Le type est obligatoire.");
            return;
        }

        String anneeText = anneeSortieField.getText();
        if (anneeText != null && !anneeText.isBlank()) {
            try {
                int annee = Integer.parseInt(anneeText);
                if (annee < 1888 || annee > 2030) {
                    messageLabel.setText("L'année doit être entre 1888 et 2030.");
                    return;
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("L'année de sortie doit être un nombre.");
                return;
            }
        }

        String noteText = noteField.getText();
        if (noteText != null && !noteText.isBlank()) {
            try {
                int n = Integer.parseInt(noteText);
                if (n < 0 || n > 5) {
                    messageLabel.setText("La note doit être entre 0 et 5.");
                    return;
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("La note doit être un nombre.");
                return;
            }
        }

        String url = imageUrlField.getText();
        if (url != null && !url.isBlank() && !url.startsWith("http://") && !url.startsWith("https://")) {
            messageLabel.setText("L'URL de l'image doit commencer par http:// ou https://.");
            return;
        }

        Contenu c = editingContenu != null ? editingContenu : new Contenu();
        c.setTitre(titreField.getText().trim());
        c.setType(typeField.getValue());
        c.setGenre(genreField.getValue());
        c.setRealisateur(realisateurField.getText());
        c.setSynopsis(synopsisField.getText());
        c.setImageUrl(url);
        c.setStatut(statutField.getValue());
        c.setWatchlist(watchlistField.isSelected());
        c.setAnneeSortie(anneeText != null && !anneeText.isBlank() ? Integer.parseInt(anneeText) : 0);
        c.setNote(noteText != null && !noteText.isBlank() ? Integer.parseInt(noteText) : 0);

        if (editingContenu != null) {
            contenuRepo.update(c);
            messageLabel.setText("Contenu modifié !");
        } else {
            contenuRepo.save(c);
            messageLabel.setText("Contenu enregistré !");
        }

        messageLabel.setStyle("-fx-text-fill: green;");
        clearForm();

        if (onSaveComplete != null) {
            onSaveComplete.run();
        }
    }

    @FXML
    public void handleCancel() {
        clearForm();
        if (onCancel != null) {
            onCancel.run();
        }
    }

    private void clearForm() {
        editingContenu = null;
        titreField.clear();
        typeField.setValue(null);
        genreField.setValue(null);
        anneeSortieField.clear();
        realisateurField.clear();
        synopsisField.clear();
        imageUrlField.clear();
        statutField.setValue(null);
        noteField.clear();
        watchlistField.setSelected(false);
        messageLabel.setText("");

        saveBtn.setText("Enregistrer");
        cancelBtn.setVisible(false);
        cancelBtn.setManaged(false);
    }
}
