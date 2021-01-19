package main.javafx.controllers.main;

import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import main.application.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class FatalErrorController implements Initializable {

    public Text text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       text.setText("Fatal error. " + Logger.lastFatalError);
    }
}
