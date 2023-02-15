package hust.itep.quanlynhankhau.controller.component.factory;

import hust.itep.quanlynhankhau.context.Context;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageFactory {

    public static Stage buildStage(String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.getIcons().add(Context.ICON);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

}
