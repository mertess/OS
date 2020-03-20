import java.util.ArrayList;

public class MemManagementUnit {
    //таблица страниц
    private ArrayList<Page> tablePages;
    //таблица номеров страничных блоков физической памяти
    private ArrayList<Page> tablePageFrames;
    private int countPageFrames;
    private int index;

    public MemManagementUnit(int memRAM, int sizeOfPage){
        tablePages = new ArrayList<Page>();
        tablePageFrames = new ArrayList<Page>();
        countPageFrames = memRAM/sizeOfPage;

        for(int i = 0; i <(memRAM * 2)/sizeOfPage; i++){
            Page page = new Page(false, false);
            page.setPage_frame_number(Integer.MAX_VALUE);
            tablePages.add(page);
        }
        index = 0;
    }

    public void inputNumberOfPage(int pageIndex){
        //произошло обращение к виртуальному адресному пространству страницы с номером pageIndex
        Page page = tablePages.get(pageIndex);
        //ошибка отсутствия страницы
        if(!page.isPresence_absence()){
            if(tablePageFrames.size() < countPageFrames){
                page.setPresence_absence(true);
                page.setR(true);
                tablePageFrames.add(page);
                int indexOfPageFrames = tablePageFrames.indexOf(page);
                page.setPage_frame_number(indexOfPageFrames);
            }else if(tablePageFrames.size() == countPageFrames){
                clockAlgorithm(page);
            }
        }else{
            page.setR(true);
        }
        printPageFrames();
        printPages();
    }

    private void clockAlgorithm(Page page){
        for (;;) {
            if(index == tablePageFrames.size()){
                index = 0;
            }
            if(tablePageFrames.get(index).isR()){
                tablePageFrames.get(index).setR(false);
                index++;
            }else{
                tablePageFrames.get(index).setPresence_absence(false);
                tablePageFrames.get(index).setPage_frame_number(Integer.MAX_VALUE);
                tablePageFrames.remove(index);
                tablePageFrames.add(index, page);
                page.setPage_frame_number(index);
                page.setR(true);
                page.setPresence_absence(true);
                System.out.println("Выселение страницы с номером " + index);
                index++;
                return;
            }
        }
    }

    public int getSizeTablePages(){
        return tablePages.size();
    }

    public void printPageFrames(){
        int i = 0;
        System.out.println("Таблица страничных блоков \n" + "| i  R  P_A |");
        for (Page current_page: tablePageFrames) {
            System.out.println("| " + i + " " + current_page.isR() + " " + current_page.isPresence_absence() + " |");
            i++;
        }
        System.out.println();
    }

    private void printPages(){
        System.out.println("Таблица страниц \n" + "| i   R    P_A   IndexFrame |");
        for(int i = 0; i<tablePages.size(); i++){
            System.out.println("| " + i + " " + tablePages.get(i).isR() + " "
                    + tablePages.get(i).isPresence_absence() + " " + tablePages.get(i).getPage_frame_number() + " |");
        }
        System.out.println();
    }
}
