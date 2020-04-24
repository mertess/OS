package sample;

import java.util.ArrayList;

public class Directory {
    private String dir_name;
    private ArrayList<File> files;

    public Directory(){
        files = new ArrayList<>();
    }

    public String getDir_name() {
        return dir_name;
    }

    public void setDir_name(String dir_name) {
        this.dir_name = dir_name;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return dir_name;
    }
}
