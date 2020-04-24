package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class FileManager {
    private TreeView<Object> treeView;
    private SelectionModel<TreeItem<Object>> selectionModel;
    private Image imageNode = new Image(new FileInputStream("src\\res\\4.png"));
    private HashMap<Directory, TreeItem<Object>> refTable;
    private ObservableList<Directory> directories;

    public FileManager(TreeView<Object> treeView, String root, ObservableList<Directory> directoryArrayList) throws FileNotFoundException {
        this.treeView = treeView;
        this.treeView.setRoot(new TreeItem<Object>(root));
        this.refTable = new HashMap<>();
        this.directories = directoryArrayList;
        selectionModel = treeView.getSelectionModel();
    }

    public <T> void addFile(T file){
        TreeItem<Object> treeItem = selectionModel.getSelectedItem();
        if(treeItem != null){
            if(treeItem.getValue().getClass().getSimpleName().equals("Directory")
                    || treeItem.getValue().getClass().getSimpleName().equals("String")){
                if(file instanceof Directory){
                    treeItem.getChildren().add(new TreeItem<Object>(file, new ImageView(imageNode)));
                    refTable.put((Directory) file, selectionModel.getSelectedItem());
                    directories.add((Directory) file);
                }else{
                    treeItem.getChildren().add(new TreeItem<Object>(file));
                    for(Directory dir : directories){
                        if(dir.getDir_name().equals(selectionModel.getSelectedItem().getValue().toString())){
                            dir.getFiles().add((File)file);
                        }
                    }
                }
            }
        }
    }

    public void remove(){
        TreeItem<Object> treeItem = selectionModel.getSelectedItem();
        if(treeItem != null){
            if(!treeItem.getValue().getClass().getSimpleName().equals("String")){
                treeItem.getParent().getChildren().remove(treeItem);
            }
        }
    }

    public <T> void replace(T dir){
        if(dir != null && selectionModel.getSelectedItem() != null
                && !dir.toString().equals(treeView.getRoot().getValue().toString())){
            for(TreeItem<Object> item : refTable.get(dir).getChildren()){
                if(item.getValue().toString().equals(dir.toString())){
                    item.getChildren().add(new TreeItem<Object>(selectionModel.getSelectedItem().getValue()));
                }
            }
            selectionModel.getSelectedItem().getParent().getChildren().remove(selectionModel.getSelectedItem());
        }else if(dir != null &&  selectionModel.getSelectedItem() != null
                && dir.toString().equals(treeView.getRoot().getValue().toString())){
            treeView.getRoot().getChildren().add(new TreeItem<Object>(selectionModel.getSelectedItem().getValue()));
            selectionModel.getSelectedItem().getParent().getChildren().remove(selectionModel.getSelectedItem());
        }
    }
}
