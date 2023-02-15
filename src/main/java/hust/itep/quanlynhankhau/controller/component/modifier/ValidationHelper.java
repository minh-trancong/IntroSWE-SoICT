package hust.itep.quanlynhankhau.controller.component.modifier;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static io.github.palexdev.materialfx.validation.Validated.INVALID_PSEUDO_CLASS;

public class ValidationHelper {

    public static Constraint constraintBuild(MFXTextField textField, String message, Callable<Boolean> callable) {
        return Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage(message)
                .setCondition(Bindings.createBooleanBinding(callable, textField.textProperty()))
                .get();
    }

    public static void setValidatorListener(MFXTextField textField) {

        textField.getValidator().validProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                VBox parent = (VBox) textField.getParent();
                parent.getChildren().removeIf(node -> node instanceof Label);
                textField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        }));

        textField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraintLists = textField.validate();

                if (!constraintLists.isEmpty()) {
                    VBox parent = (VBox) textField.getParent();
                    parent.getChildren().removeIf(node -> node instanceof Label);

                    Label label = new Label();
                    label.setTextFill(Paint.valueOf("red"));
                    label.setFont(Font.font(12));
                    label.setText(constraintLists.get(0).getMessage());
                    parent.getChildren().add(label);
                    textField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                }
            }
        });
    }


}
