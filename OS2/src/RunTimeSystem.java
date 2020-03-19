import java.util.ArrayList;
import java.util.List;

public class RunTimeSystem {
    private List<MyThread> threads;

    public RunTimeSystem(String processName, int processTime) {
        int count = (int) (Math.random() * 10) + 1;
        threads = new ArrayList<MyThread>();
        for (int i = 0; i < count; i++) {
            MyThread thread = new MyThread();
            thread.setWorkTime(processTime / count);
            thread.setThreadName(processName + " " + "Thread " + (i + 1));
            threads.add(thread);
        }
    }

    public void RunThreads(){
        for (MyThread thread: threads) {
            if(thread.getState() == ThreadState.isReady){
                thread.setState(ThreadState.onAction);
                //System.out.println("threadlefttime " + thread.getLeftTime());
                thread.Work();
                //System.out.println("threadlefttime " + thread.getLeftTime());
            }
        }
    }

    public List<MyThread> getThreads() {
        return threads;
    }
}
