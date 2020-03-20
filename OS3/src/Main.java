import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MemManagementUnit mmU = new MemManagementUnit(2048, 256);
        int page_index;
        Scanner scanner = new Scanner(System.in);
        /*
         * Моделирование выполнения процесса :
         * обращение к виртуальному адресному пространству входящему в страницу с указанным номером
         */
        while(true){
            System.out.println("Введите индекс страницы в таблице страниц :");
            page_index = scanner.nextInt();
            if (page_index >= 0 && page_index < mmU.getSizeTablePages()){
                mmU.inputNumberOfPage(page_index);
            }
        }
    }
}
