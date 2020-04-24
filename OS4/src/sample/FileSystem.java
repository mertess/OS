package sample;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class FileSystem {
    private ObservableList<Directory> directories;
    private int[] bitArray;
    /*
     * 1 = добавлен давно
     * 2 = добавлен недавно
     * 3 = выбран в ФМ
     */
    private int[] maskArray;
    private int blockSize;

    public FileSystem(int blockSize, int memorySize, ObservableList<Directory> directories){
        this.directories = directories;
        this.blockSize = blockSize;
        this.bitArray = new int[Math.round(memorySize/blockSize)];
        this.maskArray = new int[Math.round(memorySize/blockSize)];
    }

    public ObservableList<Directory> getDirectories(){ return directories; }
    public int[] getBitArray(){ return bitArray; }
    public int[] getMaskArray(){ return maskArray; }

    public void putFile(File file){
        int count = 0;
        System.out.println("file blocks = " + file.getCountBlocks());
        for(int i = 0; i< bitArray.length; i++){
            if(count < file.getCountBlocks() && bitArray[i] == 0){
                file.getLinkedList().addLast(i);
                bitArray[i] = 1;
                maskArray[i] = 2;
                count++;
            }else if(maskArray[i] == 2){
                maskArray[i] = 1;
            }
        }
    }

    public void cleanFileBlocks(File file){
        for(int i = 0; i<file.getLinkedList().size(); i++){
            bitArray[file.getLinkedList().get(i)] = 0;
            maskArray[file.getLinkedList().get(i)] = 0;
        }
    }

    public int getBlockSize() { return blockSize; }
}
