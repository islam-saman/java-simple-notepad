import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class NotePad extends Application {
    private Stage primaryStage;
    private String currentOpendFIle;
    BorderPane rootPane;
    SeparatorMenuItem sep = new SeparatorMenuItem();

    @Override
    public void init() throws Exception {
        super.init();

        MenuBar mainBar = new MenuBar();

        // setting up the bar menues
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        // add menu items to the file menu
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem exitApp = new MenuItem("Exit");

        // add menu items to the edit menu
        MenuItem undo = new MenuItem("Undo");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        MenuItem delete = new MenuItem("Delete");
        MenuItem selectAll = new MenuItem("Select All");

        // add menu items to the help menu
        MenuItem aboutNotePad = new MenuItem("About notepad");

        // add button short cut
        openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        saveFile.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        exitApp.setAccelerator(KeyCombination.keyCombination("Ctrl+e"));

        // add the items to each menu
        file.getItems().addAll(newFile, openFile, saveFile, sep, exitApp);
        edit.getItems().addAll(undo, cut, copy, paste, delete, sep, selectAll);
        help.getItems().add(aboutNotePad);

        // add the menues to flowPane
        mainBar.getMenus().addAll(file, edit, help);

        // add the textarea
        TextArea userInput = new TextArea();

        rootPane = new BorderPane();
        rootPane.setTop(mainBar);
        rootPane.setCenter(userInput);

        // add functionility to the edit menu
        undo.setOnAction((event) -> {
            userInput.undo();
        });

        copy.setOnAction((event) -> {
            userInput.copy();
        });

        cut.setOnAction((event) -> {
            userInput.cut();
        });

        paste.setOnAction((event) -> {
            userInput.paste();
        });

        delete.setOnAction((event) -> {
            userInput.clear();
        });

        selectAll.setOnAction((event) -> {
            userInput.selectAll();
        });

        // make a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new ExtensionFilter("All Files", "*.*"));

        // add functionility to the open menu
        newFile.setOnAction((event) -> {

            try {
                Runtime runTime = Runtime.getRuntime();
                Process process = runTime.exec(
                        "/usr/bin/env /usr/lib/jvm/jdk1.8.0_202/bin/java -cp /home/islam/.config/Code/User/workspaceStorage/c29fd807dc306b59bea244b1929b831d/redhat.java/jdt_ws/lab_9-2_c3aee38d/bin StartPoint");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;

        });

        openFile.setOnAction((event) -> {
            currentOpendFIle = fileChooser.showOpenDialog(rootPane.getScene().getWindow()).toString();
            try {
                FileInputStream inputFile = new FileInputStream(currentOpendFIle);
                int fileSize = inputFile.available();
                byte[] fileBytes = new byte[fileSize];
                System.out.println(fileBytes);
                inputFile.read(fileBytes);
                userInput.setText(new String(fileBytes));
                primaryStage.setTitle(currentOpendFIle);
                inputFile.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        });

        saveFile.setOnAction((event) -> {

            try {
                FileWriter outFile = new FileWriter(currentOpendFIle);
                outFile.write(userInput.getText());
                outFile.close();

                Dialog<String> dialog = new Dialog<String>();
                dialog.setTitle("Saved successfully");
                dialog.setContentText("Your file have been saved successfully");
                ButtonType closeButton = new ButtonType("OK", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(closeButton);
                dialog.showAndWait();

            } catch (IOException e) {
                System.out.println(e);

            }

        });

        exitApp.setOnAction((event) -> {
            javafx.application.Platform.exit();
        });

        aboutNotePad.setOnAction((event) -> {
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("About Me");
            dialog.setContentText("The App is created by islam saman at iti");
            ButtonType closeButton = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            dialog.showAndWait();

        });
    }

    @Override
    public void start(Stage primaStage) {
        primaryStage = primaStage;

        Scene scene = new Scene(rootPane, 1200, 800);
        String customStyles = getClass().getResource("style.css").toString();
        scene.getStylesheets().add(customStyles);
        primaryStage.setTitle("For test");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // public static void main(String args[])
    // {
    // Application.launch(args);
    // }

}
