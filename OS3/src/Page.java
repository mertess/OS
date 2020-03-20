public class Page {
    //false - отсутствует в памяти, true - присутствует
    private boolean presence_absence;
    private int page_frame_number;

    //false - не использовалось, true - использовалось
    private boolean r;

    public Page(boolean presence_absence, boolean r){
        this.r = r;
        this.presence_absence = presence_absence;
    }

    public boolean isPresence_absence() {
        return presence_absence;
    }

    public int getPage_frame_number() {
        return page_frame_number;
    }

    public void setPage_frame_number(int page_frame_number) {
        this.page_frame_number = page_frame_number;
    }

    public boolean isR() {
        return r;
    }

    public void setPresence_absence(boolean presence_absence) {
        this.presence_absence = presence_absence;
    }


    public void setR(boolean r) {
        this.r = r;
    }
}
