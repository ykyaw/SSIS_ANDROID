package iss.team1.ad.ssis_android.components.call;

public interface ICallObserverable {
    public void registerObserver(CallStateListener o);
    public void removeObserver(CallStateListener o);
    public void notifyObserver(boolean result);
}
