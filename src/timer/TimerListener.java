package timer;

public interface TimerListener{
    void onTick(int secondsRemaining);
    void onTimeUp();
}