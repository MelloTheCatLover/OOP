module ru.nsu.kozoliy {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.kozoliy to javafx.fxml;
    exports ru.nsu.kozoliy;
    exports ru.nsu.kozoliy.viewmodel;
    opens ru.nsu.kozoliy.viewmodel to javafx.fxml;
    exports ru.nsu.kozoliy.models;
    opens ru.nsu.kozoliy.models to javafx.fxml;
}