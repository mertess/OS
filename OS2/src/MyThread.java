public class MyThread {
    private ThreadState state;
    private int workTime;
    private String threadName;
    private int leftTime;

    public MyThread(){
        state = ThreadState.isReady;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getLeftTime(){ return leftTime; }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ThreadState getState() {
        return state;
    }

    //workTime - квант выделенный под поток
    public void setWorkTime(int workTime) {
        this.workTime = workTime;
        this.leftTime = (int)(Math.random() * (workTime + 40));
    }

    public void setState(ThreadState state){
        System.out.print(threadName + ": " + this.state + " => ");
        this.state = state;
        System.out.println(state);
    }

    /*
     * Проверка оставшегося времени работы текущего потока,
     * если его время работы меньше чем выделенынй квант времени на поток,
     * то статус потока переходит в завершенный, иначе идет пересчет оставшегося времени выполнения
     */
    public void Work(){
        //System.out.println(threadName + " lefttime = " + leftTime);
        this.leftTime -= workTime;
        if(leftTime > 0)
            setState(ThreadState.isReady);
        else
            setState(ThreadState.isFinished);
    }
}
