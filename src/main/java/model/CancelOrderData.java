package model;

public class CancelOrderData {
    private String track;

    public CancelOrderData(String track) {
        this.track = track;
    }

    public CancelOrderData() {
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
