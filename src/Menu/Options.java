package Menu;

import java.math.BigDecimal;

public class Options {

    private double volume; // current volume
    private double volumePercent; // current volume percentage
    private boolean mute; // mute volume

    /**
     * Constructor
     */
    Options() {
        volume = 50.0; // initial volume set at 50
        volumePercent = 0.5; // initial volume percentage
        mute = false; // initially volume is played
    }

    /**
     * Set the volume 
     * @param newVolume Change current volume value
     */
    void setVolume(double newVolume) {

        this.volume = new BigDecimal(newVolume)
                .setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * Get the current volume
     * @return volume Current volume value
     */
    double getVolume() {
        return this.volume;
    }

    /**
     * Returns whether the volume is mute
     * @return mute Current mute state
     */
    boolean isMute() {
        return mute;
    }

    /**
     * Change mute state to the opposite to current state
     */
    void changeMuteState() {
        this.mute = !isMute();
    }

    /**
     * Set the volume percentage
     * @param newVolume New volume percentage
     */
    private void setVolumePercent(double newVolume) {
        this.volumePercent = newVolume / 100;
    }

    /**
     * Get the volume percentage for current volume
     * @return volumePercent Percentage value for volume
     */
    double getVolumePercent() {
        setVolumePercent(volume);
        return this.volumePercent;
    }

}
