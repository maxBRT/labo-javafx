package com.github.maxbrt.films.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/*
 * So at first I only had one controller that handled both tab. While it was "Simpler" it became spaghetti really fast lol!
 * Now I have one controller per tab, and this main controller is just responsible for switching between tabs and passing data between them.
*/
public class MainController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab formTab;

    @FXML
    private Tab listTab;

    @FXML
    private ListController listViewController;
    @FXML
    private FormController formViewController;

    @FXML
    private DiscoverController discoverViewController;

    @FXML
    public void initialize() {
        // When the list requests an edit, populate the form and switch to its tab
        listViewController.setOnEditRequest(contenu -> {
            formViewController.editContenu(contenu);
            formTab.setText("Modifier");
            tabPane.getSelectionModel().select(formTab);
        });

        // When the form saves, reload the list and reset the tab title
        formViewController.setOnSaveComplete(() -> {
            listViewController.loadContenus();
            formTab.setText(formViewController.getFormTabTitle());
            tabPane.getSelectionModel().select(0);
        });

        // When the form cancel is pressed, switch back to the list tab
        formViewController.setOnCancel(() -> {
            formTab.setText("Ajouter");
            tabPane.getSelectionModel().select(0);
        });

        // Handle the tab switching hooks
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != formTab) {
                formTab.setText("Ajouter");
            }
            if (newTab == listTab) {
                listViewController.loadContenus();
            }
        });
    }
}
