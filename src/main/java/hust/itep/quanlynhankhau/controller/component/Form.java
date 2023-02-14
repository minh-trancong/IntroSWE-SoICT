package hust.itep.quanlynhankhau.controller.component;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXTooltip;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.util.List;

import static io.github.palexdev.materialfx.validation.Validated.INVALID_PSEUDO_CLASS;

public class Form {
    private Button submitButton;
    private ArrayList<MFXTextField> textFields = new ArrayList<>();

    private boolean validate() {

        boolean ret = true;
        for (MFXTextField textField : textFields) {
            if (!textField.getValidator().validProperty().get()) {
                ret = false;
                System.out.println(textField.getText());
                textField.requestFocus();
            }
        }
        submitButton.requestFocus();

        return ret;
    }

    public void setOnSubmit(EventHandler<? super MouseEvent> eventHandler) {
        submitButton.setOnMouseClicked(e -> {
            if (!validate()) {
                return;
            }
            eventHandler.handle(e);
        });
    }

    public Form(MFXButton submitButton) {
        this.submitButton = submitButton;
    }

    public void setSubmitButton(MFXButton submitButton) {
        this.submitButton = submitButton;
    }

    public void addTextField(MFXTextField textField) {
        textFields.add(textField);
    }

    public void addTextField(ArrayList<MFXTextField> textFields) {
        textFields.addAll(textFields);
    }

    public ArrayList<MFXTextField> getTextFields() {
        return textFields;
    }
}