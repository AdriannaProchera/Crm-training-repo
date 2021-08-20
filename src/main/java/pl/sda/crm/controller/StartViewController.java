package pl.sda.crm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.sda.crm.CrmApplication;
import pl.sda.crm.service.PersonCustomerRegistration;
import pl.sda.crm.service.RegisterPersonForm;
import pl.sda.crm.util.HibernateUtil;

import java.io.IOException;

public class StartViewController {

    @FXML
    private Text customerId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField pesel;

    @FXML
    private Text errorMessage;

    private final PersonCustomerRegistration registration;
    private final SceneLoader sceneLoader;

    public StartViewController() {
        this.registration = new PersonCustomerRegistration(HibernateUtil.getSessionFactory());
        this.sceneLoader = new SceneLoader();
    }

    @FXML
    public void onPersonSave(ActionEvent event) {
        // 0. Wyczyść poprzedni customerId
        customerId.setText("");

        // 1. wyciągniecie danych z kontrolek
        final var firstNameText = firstName.getText();
        final var lastNameText = lastName.getText();
        final var peselText = pesel.getText();

        // 2. zbudowanie obiektu RegisterPersonForm
        final var form = new RegisterPersonForm(firstNameText, lastNameText, peselText);

        // 3. wywołanie registerPerson
        try {
            final var registeredCustomerId = registration.registerPerson(form);
            // 4. wyświetlenie ID zarejestrowanego klienta
            customerId.setText(registeredCustomerId.getId().toString());
            errorMessage.setText("");
        } catch (Exception ex) {
            errorMessage.setText(ex.getMessage());
        }
    }

    @FXML
    public void displayCustomerList(ActionEvent event) throws IOException {
        sceneLoader.loadOnEvent("customer-list-view.fxml", event);
    }
}