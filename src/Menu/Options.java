package Menu;

import java.math.BigDecimal;

public class Options {

    double volume;
    private double volumePercent;
    boolean mute;

    public Options() {
        volume = 50.0;
        volumePercent = 0.5;
        mute = false;
    }

    void setVolume(double newVolume) {

        this.volume = new BigDecimal(newVolume)
                .setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    double getVolume() {
        return this.volume;
    }

    boolean isMute() {
        return mute;
    }

    void changeMuteState() {
        this.mute = !isMute();
    }

    private void setVolumePercent(double newVolume) {
        this.volumePercent = newVolume / 100;
    }

    double getVolumePercent() {
        setVolumePercent(volume);
        return this.volumePercent;
    }
}
