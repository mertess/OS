public class Main {

    public static void main(String[] args){
        Kernel kernel = new Kernel(200);
        while (!kernel.CheckStoppedAll()){
            kernel.RunProcesses();
        }
    }
}
