package sample;

import javafx.scene.control.TreeItem;

public class FileSystem {
    private int[] bitArray;
    /*
     * maskArray
     * 1 = добавлен давно
     * 2 = добавлен недавно
     * 3 = выбран в ФМ
     */
    private int[] maskArray;
    private int blockSize;

    public FileSystem(int blockSize, int memorySize){
        this.blockSize = blockSize;
        this.bitArray = new int[Math.round(memorySize/blockSize)];
        this.maskArray = new int[Math.round(memorySize/blockSize)];
    }

    public int[] getBitArray(){ return bitArray; }
    public int[] getMaskArray(){ return maskArray; }

    public void putFile(File file) throws Exception{
        if(getCountAvailableBlocks() >= file.getCountBlocks()){
            int count = 0;
            System.out.println("file blocks = " + file.getCountBlocks());
            FileBlock current = file.getHeadBlock();
            for(int i = 0; i< bitArray.length; i++){
                if(count < file.getCountBlocks() && bitArray[i] == 0){
                    if (count != 0) {
                        current.setNext(new FileBlock());
                        current = current.getNext();
                    }
                    current.setIndex(i);
                    bitArray[i] = 1;
                    maskArray[i] = 2;
                    count++;
                }else if(maskArray[i] == 2){
                    maskArray[i] = 1;
                }
            }
        }else{
            throw new Exception("Недостаточно места на носителе!");
        }
    }

    public void cleanFileBlocks(File file){
        FileBlock current = file.getHeadBlock();
        while (current != null){
            bitArray[current.getIndex()] = 0;
            maskArray[current.getIndex()] = 0;
            current = current.getNext();
        }
    }

    public void fileSelected(File file){
        dropSelection();
        FileBlock current = file.getHeadBlock();
        while (current != null){
            maskArray[current.getIndex()] = 3;
            current = current.getNext();
        }
    }

    public void directorySelected(TreeItem<Object> treeItem){
        if(treeItem.getChildren() != null){
            for(TreeItem<Object> item : treeItem.getChildren()){
                if(item.getValue().getClass().getSimpleName().equals("Directory"))
                    directorySelected(item);
                else{
                    FileBlock current = ((File)item.getValue()).getHeadBlock();
                    while (current != null){
                        maskArray[current.getIndex()] = 3;
                        current = current.getNext();
                    }
                }
            }
        }
    }

    public void dropSelection(){
        for(int i = 0; i<maskArray.length; i++){
            if(maskArray[i] == 3 || maskArray[i] == 2) maskArray[i] = 1;
        }
    }

    private int getCountAvailableBlocks(){
        int count = 0;
        for(int i = 0; i<bitArray.length; i++){
            if(bitArray[i] == 0)
                count++;
        }
        return count;
    }

    public int getBlockSize() { return blockSize; }
}
