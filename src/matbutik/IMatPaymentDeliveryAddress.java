package matbutik;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Text;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatPaymentDeliveryAddress extends IMatController implements Initializable {
    private IMatDataHandler dataHandler;
    private Customer customer;

    @FXML private TextField deliveryFirstNameTextField;
    @FXML private TextField deliveryAfterNameTextField;
    @FXML private TextField deliveryAdressTextField;
    @FXML private TextField deliveryPostCodeTextField;
    @FXML private TextField deliveryPostAdressTextField;
    @FXML private GridPane deliveryMainGridPane;
    @FXML private Label errorLabel;
    @FXML private Label firstNameErrorLabel;
    @FXML private Label lastNameErrorLabel;
    @FXML private Label postCodeErrorLabel;
    @FXML private Label postAdressErrorLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        dataHandler = IMatDataHandler.getInstance();
        customer = dataHandler.getCustomer();
        clearTextFieldsError();
        deliveryInit();
        deliveryPopulateTextField();
        setNumberLabels();
    }

    private void deliveryInit() {
        deliveryFirstNameTextField.focusedProperty().addListener(new TextFieldListener(deliveryFirstNameTextField));
        deliveryAfterNameTextField.focusedProperty().addListener(new TextFieldListener(deliveryAfterNameTextField));
        deliveryAdressTextField.focusedProperty().addListener(new TextFieldListener(deliveryAdressTextField));
        deliveryPostCodeTextField.focusedProperty().addListener(new TextFieldListener(deliveryPostCodeTextField));
        deliveryPostAdressTextField.focusedProperty().addListener(new TextFieldListener(deliveryPostAdressTextField));
        deliveryPopulateTextField();
    }

    private void deliveryPopulateTextField() {
        deliveryFirstNameTextField.setText(customer.getFirstName());
        deliveryAfterNameTextField.setText(customer.getLastName());
        deliveryAdressTextField.setText(customer.getAddress());
        deliveryPostCodeTextField.setText(customer.getPostCode());
        deliveryPostAdressTextField.setText(customer.getPostAddress());
    }

    private void setEmptyTextFieldsErrors() {
        for (Node node : deliveryMainGridPane.getChildren()) {
            if (node instanceof TextField) {
                if (((TextField) node).getText().equals("")) {
                    node.setStyle("-fx-text-box-border: red;");
                }
            }
        }
    }

    private void clearTextFieldsErrorBorders() {
        for (Node node : deliveryMainGridPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setStyle(null);
            }
        }
    }

    private void showTextFieldsError() {
        if (deliveryFirstNameTextField.getText().matches(".*\\d+.*")) {
            firstNameErrorLabel.setVisible(true);
            deliveryFirstNameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (deliveryAfterNameTextField.getText().matches(".*\\d+.*")) {
            lastNameErrorLabel.setVisible(true);
            deliveryAfterNameTextField.setStyle("-fx-text-box-border: red;");
        }
        if (deliveryPostAdressTextField.getText().matches(".*\\d+.*")) {
            postAdressErrorLabel.setVisible(true);
            deliveryPostAdressTextField.setStyle("-fx-text-box-border: red;");
        }
        if (!deliveryPostCodeTextField.getText().matches("[0-9]+")) {
            postCodeErrorLabel.setVisible(true);
            deliveryPostCodeTextField.setStyle("-fx-text-box-border: red;");
        }
    }

    private void clearTextFieldsError() {
        firstNameErrorLabel.setVisible(false);
        lastNameErrorLabel.setVisible(false);
        postAdressErrorLabel.setVisible(false);
        postCodeErrorLabel.setVisible(false);
        errorLabel.setVisible(false);
        clearTextFieldsErrorBorders();
    }

    @FXML private void deliveryAddressOnAction() {
        clearTextFieldsError();
        if (deliveryFirstNameTextField.getText().equals("") ||
                deliveryAfterNameTextField.getText().equals("") ||
                deliveryAdressTextField.getText().equals("") ||
                deliveryPostAdressTextField.getText().equals("") ||
                deliveryPostCodeTextField.getText().equals("")) {
            errorLabel.setVisible(true);
            setEmptyTextFieldsErrors();
            return;
        }
        showTextFieldsError();

        if (firstNameErrorLabel.isVisible() || lastNameErrorLabel.isVisible() || postAdressErrorLabel.isVisible() || postCodeErrorLabel.isVisible()) {
                return;
            }
            customer.setFirstName(deliveryFirstNameTextField.getText());
            customer.setLastName(deliveryAfterNameTextField.getText());
            customer.setAddress(deliveryAdressTextField.getText());
            customer.setPostAddress(deliveryPostAdressTextField.getText());
            customer.setPostCode(deliveryPostCodeTextField.getText());
            navigationHandler.toDelivery();
    }


}
