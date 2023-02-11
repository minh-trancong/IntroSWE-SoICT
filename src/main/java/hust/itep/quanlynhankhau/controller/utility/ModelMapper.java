package hust.itep.quanlynhankhau.controller.utility;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;

public class ModelMapper {
    private HashMap<String, Node> fields = new HashMap<>();
    public void addField(String field, Node node) {
        fields.putIfAbsent(field, node);
    }

    public void printAll() {
        for (Node node : fields.values()) {
            System.out.println(node.toString());
        }
    }

    public<T> T map(Class<T> modelClass) {
        T model = null;
        try {
            model = modelClass.getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (Field field : modelClass.getDeclaredFields()) {
            if (fields.containsKey(field.getName())) {
                field.setAccessible(true);
                Node node = fields.get(field.getName());
                String value = getValue(node);
                try {
                    field.set(model, convertToType(field.getType(), value));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return model;
    }

    private String getValue(Node node) {
        if (node instanceof TextField) {
            return ((TextField)node).getText();
        } else if (node instanceof MFXTextField) {
            return ((MFXTextField)node).getText();
        } else if (node instanceof MFXComboBox<?>) {
            return ((MFXComboBox<?>)node).getText();
        } else if (node instanceof MFXDatePicker) {
            return  ((MFXDatePicker)node).getText();
        } else {
            System.out.println(node.getClass().toString());
        }
        return null;
    }

    private Object convertToType(Class<?> type, String value) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == LocalDate.class){
           return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        return null;
    }




}
