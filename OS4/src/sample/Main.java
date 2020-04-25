package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    private FileSystem fileSystem;
    private ObservableList<Directory> directories;
    private Group root;
    private Rectangle[] rectangles;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Group();
        directories = FXCollections.observableArrayList();
        Directory directory = loadingStage();
        directories.add(directory);
        TreeView<Object> treeView = new TreeView<>();
        treeView.setPrefSize(200, 500);
        treeView.setRoot(new TreeItem<>(directory));
        FileManager fm = new FileManager(treeView, directories);
        draw(root);
        root.getChildren().add(treeView);

        Button buttonAddFile = new Button();
        buttonAddFile.setLayoutX(200);
        buttonAddFile.setText("Add File");
        buttonAddFile.setDisable(true);
        buttonAddFile.setOnMouseClicked(event -> {
            Group rootFormAddFile = new Group();
            Stage stage = new Stage();

            TextField textFieldFileName = new TextField();
            textFieldFileName.setLayoutX(100);
            textFieldFileName.setPrefWidth(200);

            TextField textFieldFileSize = new TextField();
            textFieldFileSize.setLayoutX(180);
            textFieldFileSize.setPrefWidth(120);
            textFieldFileSize.setLayoutY(30);

            Label labelFileName = new Label();
            labelFileName.setText("Имя файла");
            labelFileName.setLayoutX(20);
            labelFileName.setPrefWidth(100);
            labelFileName.setLayoutY(4);

            Label labelFileSize = new Label();
            labelFileSize.setText("Размер файла (кол-во блоков)");
            labelFileSize.setPrefWidth(180);
            labelFileSize.setLayoutY(34);

            Button buttonAccept = new Button();
            buttonAccept.setText("Принять");
            buttonAccept.setLayoutX(250);
            buttonAccept.setPrefWidth(50);
            buttonAccept.setLayoutY(100);

            buttonAccept.setOnMouseClicked(event1 -> {
                try{
                    File file = new File(Integer.parseInt(textFieldFileSize.getText()), fileSystem.getBlockSize());
                    file.setFile_name(textFieldFileName.getText());
                    fileSystem.putFile(file);
                    fm.addFile(file);
                    draw(root);
                    stage.close();
                }catch (Exception ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(ex.getMessage());
                    alert.setContentText("Освободите место!");
                    alert.showAndWait();
                }
            });

            rootFormAddFile.getChildren().addAll(textFieldFileName, textFieldFileSize, labelFileName,
                    labelFileSize, buttonAccept);

            Scene scene = new Scene(rootFormAddFile, 330, 150);
            stage.setScene(scene);
            stage.setTitle("Добавление нового файла");
            stage.show();
        });

        Button buttonAddCatalog = new Button();
        buttonAddCatalog.setLayoutX(260);
        buttonAddCatalog.setText("Add Catalog");
        buttonAddCatalog.setDisable(true);

        buttonAddCatalog.setOnMouseClicked(event -> {
            Group group = new Group();
            Stage stage = new Stage();

            TextField textFieldCatalogName = new TextField();
            textFieldCatalogName.setLayoutX(100);
            textFieldCatalogName.setPrefWidth(200);

            Label labelCatalogName = new Label();
            labelCatalogName.setText("Имя каталога");
            labelCatalogName.setLayoutX(20);
            labelCatalogName.setPrefWidth(100);
            labelCatalogName.setLayoutY(4);

            Button buttonAccept = new Button();
            buttonAccept.setText("Принять");
            buttonAccept.setLayoutX(240);
            buttonAccept.setLayoutY(50);

            buttonAccept.setOnMouseClicked((mouseEvent) -> {
                    Directory directory1 = new Directory();
                    directory1.setDir_name(textFieldCatalogName.getText());
                    fm.addFile(directory1);
                    stage.close();
            });

            group.getChildren().addAll(textFieldCatalogName, labelCatalogName, buttonAccept);

            Scene scene = new Scene(group, 320, 100);
            stage.setTitle("Добавление каталога");
            stage.setScene(scene);
            stage.show();
        });

        Button buttonRemoveFile = new Button();
        buttonRemoveFile.setText("Remove");
        buttonRemoveFile.setLayoutX(200);
        buttonRemoveFile.setLayoutY(25);
        buttonRemoveFile.setDisable(true);
        buttonRemoveFile.setOnMouseClicked(event -> {
            if(treeView.getSelectionModel().getSelectedItem().getValue().getClass().getSimpleName().equals("File")){
                fileSystem.cleanFileBlocks((File)treeView.getSelectionModel().getSelectedItem().getValue());
                draw(root);
            }
            fm.remove();
        });

        Button buttonReplaceFile = new Button();
        buttonReplaceFile.setText("Replace");
        buttonReplaceFile.setLayoutX(260);
        buttonReplaceFile.setLayoutY(25);
        buttonReplaceFile.setDisable(true);
        buttonReplaceFile.setOnMouseClicked(event -> {
            Group group = new Group();
            Stage stage = new Stage();

            Label labelSelect = new Label();
            labelSelect.setText("Куда");
            labelSelect.setLayoutX(15);
            labelSelect.setLayoutY(4);

            ComboBox<Directory> comboBox = new ComboBox<Directory>(directories);
            comboBox.setLayoutX(60);

            Button buttonAccept = new Button();
            buttonAccept.setText("Принять");
            buttonAccept.setLayoutX(50);
            buttonAccept.setLayoutY(30);

            buttonAccept.setOnMouseClicked(e ->{
                if(comboBox.getSelectionModel().getSelectedItem() != null){
                    Directory dir = comboBox.getSelectionModel().getSelectedItem();
                    fm.replace(dir);
                }
                stage.close();
            });

            group.getChildren().addAll(labelSelect, comboBox, buttonAccept);
            Scene scene = new Scene(group, 200, 150);
            stage.setScene(scene);
            stage.setTitle("Перемещение");
            stage.show();
        });

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.getValue().getClass().getSimpleName().equals("String")) {
                buttonRemoveFile.setDisable(true);
                buttonAddCatalog.setDisable(false);
                buttonAddFile.setDisable(false);
                buttonReplaceFile.setDisable(true);
                fileSystem.dropSelection();
                fileSystem.directorySelected(newValue);
                draw(root);
            }else if(newValue.getValue().getClass().getSimpleName().equals("Directory")){
                buttonAddFile.setDisable(false);
                buttonRemoveFile.setDisable(false);
                buttonAddCatalog.setDisable(false);
                buttonReplaceFile.setDisable(false);
                fileSystem.dropSelection();
                fileSystem.directorySelected(newValue);
                draw(root);
            }else{
                buttonRemoveFile.setDisable(false);
                buttonAddFile.setDisable(true);
                buttonAddCatalog.setDisable(true);
                buttonReplaceFile.setDisable(false);
                fileSystem.fileSelected((File)newValue.getValue());
                draw(root);
            }
        });

        root.getChildren().addAll(buttonAddFile, buttonAddCatalog, buttonRemoveFile, buttonReplaceFile);

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Моделирование файловой системы");
        primaryStage.show();
    }

    private Directory loadingStage(){
        Stage stage = new Stage();
        Directory dir = new Directory();
        Group group = new Group();

        TextField textFieldRootName = new TextField();
        textFieldRootName.setLayoutX(170);
        textFieldRootName.setLayoutY(20);

        Label labelRootName = new Label();
        labelRootName.setLayoutY(24);
        labelRootName.setText("Название дискового раздела");

        TextField textFieldRootSize = new TextField();
        textFieldRootSize.setLayoutX(170);
        textFieldRootSize.setLayoutY(43);

        Label labelRootSize = new Label();
        labelRootSize.setLayoutY(47);
        labelRootSize.setText("Размер дискового раздела");

        TextField textFieldBlockSize = new TextField();
        textFieldBlockSize.setLayoutX(170);
        textFieldBlockSize.setLayoutY(65);

        Label labelBlockSize = new Label();
        labelBlockSize.setLayoutY(69);
        labelBlockSize.setText("Размер сектора");
        
        Button buttonAccept = new Button();
        buttonAccept.setText("Принять");
        buttonAccept.setLayoutX(250);
        buttonAccept.setLayoutY(100);

        buttonAccept.setOnMouseClicked(event -> {
            dir.setDir_name(textFieldRootName.getText());
            fileSystem = new FileSystem(Integer.parseInt(textFieldBlockSize.getText()), Integer.parseInt(textFieldRootSize.getText()));
            rectangles = new Rectangle[fileSystem.getBitArray().length];
            stage.close();
        });

        group.getChildren().addAll(textFieldBlockSize, textFieldRootName, textFieldRootSize,
                labelBlockSize, labelRootName, labelRootSize, buttonAccept);
        Scene scene = new Scene(group, 350, 150);

        stage.setTitle("Настройка файловой системы");
        stage.setScene(scene);
        stage.showAndWait();
        return dir;
    }

    private void draw(Group baseGroup){
        try{
            baseGroup.getChildren().removeAll(rectangles);
        }catch (Exception ex){}
        for(int i = 0; i<fileSystem.getBitArray().length; i++){
            System.out.print(fileSystem.getBitArray()[i] + " ");
        }
        System.out.println();
        for(int i = 0; i < fileSystem.getBitArray().length; i++){
            if(rectangles[i] == null)
                rectangles[i] = new Rectangle();

            if(fileSystem.getMaskArray()[i] == 1) {
                rectangles[i].setHeight(10);
                rectangles[i].setWidth(10);
                rectangles[i].setFill(Paint.valueOf("#19ff19"));
                rectangles[i].setX(11 * (i % 50) + 300);
                rectangles[i].setY(((int) i / 50) * 11 + 100);
            }else if(fileSystem.getMaskArray()[i] == 2) {
                rectangles[i].setHeight(10);
                rectangles[i].setWidth(10);
                rectangles[i].setFill(Paint.valueOf("0000ff"));
                rectangles[i].setX(11 * (i % 50) + 300);
                rectangles[i].setY(((int) i / 50) * 11 + 100);
            }else if(fileSystem.getMaskArray()[i] == 3){
                rectangles[i].setHeight(10);
                rectangles[i].setWidth(10);
                rectangles[i].setFill(Paint.valueOf("#ff0000"));
                rectangles[i].setX(11 * (i % 50) + 300);
                rectangles[i].setY(((int) i / 50) * 11 + 100);
            }else{
                rectangles[i].setHeight(10);
                rectangles[i].setWidth(10);
                rectangles[i].setFill(Paint.valueOf("#cccccc"));
                rectangles[i].setX(11 * (i % 50) + 300);
                rectangles[i].setY( ((int)i/50) * 11 + 100);
            }
        }
        baseGroup.getChildren().addAll(rectangles);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
