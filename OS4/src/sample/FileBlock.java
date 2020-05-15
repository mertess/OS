package sample;

public class FileBlock {
    private FileBlock next;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public FileBlock getNext() {
        return next;
    }

    public void setNext(FileBlock next) {
        this.next = next;
    }
}
