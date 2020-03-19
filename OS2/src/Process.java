import java.util.List;

public class Process {
    private ProcessState state;
    private RunTimeSystem runTimeSystem;
    private String processName;
    private int workTime;
    private int threadsTime;

    //workTime - квант времени процесса
    public Process(int processTime, String processName){
        this.state = ProcessState.isReady;
        this.workTime = processTime;
        this.processName = processName;
        this.runTimeSystem = new RunTimeSystem(processName, processTime);
        calcThreadsTime();
    }

    public void calcThreadsTime(){
        this.threadsTime = 0;
        List<MyThread> threadList = runTimeSystem.getThreads();
        for(int i = 0; i<threadList.size(); i++){
            threadsTime += threadList.get(i).getLeftTime();
        }
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state){
        System.out.print(processName + ": " + this.state + " => ");
        this.state = state;
        System.out.println(state);
    }

    public void Work(){
        runTimeSystem.RunThreads();
        threadsTime -= workTime;
        if(threadsTime > 0)
            setState(ProcessState.isReady);
        else{
            runTimeSystem.RunThreads();
            setState(ProcessState.isFinished);
        }
    }

    public String getProcessName() {
        return processName;
    }

    public int getThreadsTime() {
        return threadsTime;
    }
}
