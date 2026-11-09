package timer;

public class CountdownTimer implements Runnable{
    private int secondsRemaining;
    private TimerListener listener;
    private volatile boolean running;
    private volatile boolean paused;
    private Thread timerThread;
    public CountdownTimer(int totalSeconds, TimerListener listener){
        secondsRemaining = totalSeconds;
        this.listener = listener;
        paused = false;
        running = false;
    }
    public void start(){
        running = true;
        paused = false;
        timerThread = new Thread(this);
        timerThread.start();
    }
    public synchronized void pause(){
        paused = true;
    }
    public synchronized void resume(){
        paused = false;
        notifyAll();
    }
    public synchronized void stop(){
        paused = false;
        running = false;
        notifyAll();
    }
    public int getSecondsRemaining(){
        return secondsRemaining;
    }
    public boolean isPaused(){
        return paused;
    }
    @Override
    public void run() {
    // 1. The loop keeps going as long as we are running AND time is > 0
        while (running && secondsRemaining > 0) {
        
            try {
            // 2. Handle Pausing: If paused, lock the thread and wait
                synchronized (this) {
                    while (paused && running) {
                        wait(); // This puts the thread to sleep until resume() calls notifyAll()
                    }
                }
            
            // 3. If someone stopped the exam while we were paused, break out of the loop
            if (!running) break;

            // 4. Pause the thread for exactly 1 second (1000 milliseconds)
            Thread.sleep(1000);

            // 5. Subtract 1 from secondsRemaining
            secondsRemaining--;


            // 6. Notify the listener that a second has passed
            if (listener != null) {
                listener.onTick(secondsRemaining);
            }

            // 7. Check if time is up
            if (secondsRemaining <= 0) {
                running = false;
                if (listener != null) {
                    listener.onTimeUp();
                }
            }

        } catch (InterruptedException e) {
            // If stop() interrupts the thread while it's sleeping, exit the loop cleanly
            break;
        }
    }
}
}