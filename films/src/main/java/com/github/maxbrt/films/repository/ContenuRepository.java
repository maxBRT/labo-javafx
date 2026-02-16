package com.github.maxbrt.films.repository;

import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.ProgressionSeries;
import com.github.maxbrt.films.service.DatabaseService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class ContenuRepository {

    public ObservableList<Contenu> findAll() {
        ObservableList<Contenu> list = FXCollections.observableArrayList();

        String sql = """
                    SELECT c.*,
                           g.nom AS genre,
                           p.id AS progression_id,
                           p.contenu_id,
                           p.saisons_totales, p.saisons_vues,
                           p.episodes_totaux, p.episodes_vus
                    FROM contenus c
                    LEFT JOIN genres g ON c.genre_id = g.id
                    LEFT JOIN progression_series p ON c.id = p.contenu_id
                    ORDER BY c.date_ajout DESC
                """;

        try (Connection conn = DatabaseService.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contenu contenu = mapRowToContenu(rs);

                if ("SERIE".equals(rs.getString("type"))) {
                    ProgressionSeries prog = new ProgressionSeries(
                            rs.getInt("progression_id"),
                            rs.getInt("contenu_id"),
                            rs.getInt("saisons_totales"),
                            rs.getInt("saisons_vues"),
                            rs.getInt("episodes_totaux"),
                            rs.getInt("episodes_vus"));
                    contenu.setProgressionSeries(prog);
                }

                list.add(contenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<Contenu> findById(int id) {
        String sql = """
                SELECT c.*,
                       g.nom AS genre,
                       p.id AS progression_id,
                       p.contenu_id,
                       p.saisons_totales, p.saisons_vues,
                       p.episodes_totaux, p.episodes_vus
                FROM contenus c
                LEFT JOIN genres g ON c.genre_id = g.id
                LEFT JOIN progression_series p ON c.id = p.contenu_id
                WHERE c.id = ?
                """;

        try (Connection conn = DatabaseService.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contenu contenu = mapRowToContenu(rs);

                if ("SERIE".equals(rs.getString("type"))) {
                    ProgressionSeries prog = new ProgressionSeries(
                            rs.getInt("progression_id"),
                            rs.getInt("contenu_id"),
                            rs.getInt("saisons_totales"),
                            rs.getInt("saisons_vues"),
                            rs.getInt("episodes_totaux"),
                            rs.getInt("episodes_vus"));
                    contenu.setProgressionSeries(prog);
                }
                return Optional.of(contenu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Contenu mapRowToContenu(ResultSet rs) throws SQLException {
        return new Contenu(
                rs.getInt("id"),
                rs.getString("titre"),
                rs.getString("type"),
                rs.getInt("annee_sortie"),
                rs.getString("realisateur"),
                rs.getString("synopsis"),
                rs.getString("image_url"),
                rs.getInt("genre_id"),
                rs.getString("statut"),
                rs.getInt("note"),
                rs.getBoolean("watchlist"),
                rs.getTimestamp("date_ajout").toLocalDateTime(),
                rs.getString("genre"),
                null);
    }
}
