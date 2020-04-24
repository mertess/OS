package sample;

import java.util.LinkedList;

public class File {
    private String file_name;
    private LinkedList<Integer> linkedList;
    private int countBlock;

    public File(int size, int blockSize){
        countBlock = size/blockSize;
        this.linkedList = new LinkedList<Integer>();
    }

    public LinkedList<Integer> getLinkedList() {
        return linkedList;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getCountBlocks(){
        return countBlock;
    }

    @Override
    public String toString(){
        return file_name;
    }
}
