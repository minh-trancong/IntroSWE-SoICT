package hust.itep.quanlynhankhau.controller.component;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.util.List;

import static io.github.palexdev.materialfx.validation.Validated.INVALID_PSEUDO_CLASS;

public class Form {
    private MFXButton submitButton;
    private ArrayList<MFXTextField> textFields = new ArrayList<>();

    public static Constraint NonEmptyConstraint(MFXTextField textField) {
        return Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setCondition(textField.textProperty().isNotEmpty())
                .get();
    }


    private boolean validateForm() {
        boolean ret = true;
        for (MFXTextField textField : textFields) {
            if (!textField.getValidator().validProperty().get()) {
                ret = false;
                textField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
            }
        }

        return ret;
    }

    public Form(MFXButton submitButton, EventHandler<? super MouseEvent> eventHandler) {
        this.submitButton = submitButton;

        this.submitButton.setOnMouseClicked(e -> {
            if (!validateForm()) {
                return;
            }

            eventHandler.handle(e);
        });
    }

    public void setSubmitButton(MFXButton submitButton) {
        this.submitButton = submitButton;
    }

    public void addTextField(MFXTextField textField, Constraint... constraints) {
        for (Constraint constraint : constraints) {
            textField.getValidator().constraint(constraint);
        }

        textField.getValidator().validProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        }));

        textField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraintLists = textField.validate();

                if (!constraintLists.isEmpty()) {
                    textField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                }
            }
        });

        textFields.add(textField);
    }
}