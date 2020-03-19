import java.util.ArrayList;
import java.util.List;

public class Kernel {
    private List<Process> processList;
    private int processLeftTime;

    public Kernel(int processTime){
        int countProcesses = (int)(Math.random()*4) + 1;
        processList = new ArrayList<Process>();
        for(int i = 0; i<countProcesses; i++){
            Process process = new Process(processTime, "Process" + (i + 1));
            processList.add(process);
        }
    }

    private void calcProcessLeftTime(){
        processLeftTime = 0;
        for (Process process: processList) {
            processLeftTime += process.getThreadsTime();
        }
    }

    public void RunProcesses(){
        for (Process process: processList) {
            if(process.getState() == ProcessState.isReady){
                process.setState(ProcessState.onAction);
                process.Work();
            }
        }
    }

    public boolean CheckStoppedAll(){
        calcProcessLeftTime();
        if(processLeftTime <= 0){
            //System.out.println("processLeftTime = " + processLeftTime);
            return true;
        }else{
            //System.out.println("processLeftTime = " + processLeftTime);
            return false;
        }
    }
}
