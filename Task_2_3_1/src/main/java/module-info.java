module ru.nsu.kozoliy {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.kozoliy to javafx.fxml;
    exports ru.nsu.kozoliy;
}