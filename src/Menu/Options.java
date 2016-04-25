package Menu;

public class Options {

    public double volume; //VolumeSlider value
    public boolean mute;
    private static double DEFAULT_VOLUME = 50.0; // Scale to 1-100 probably

    /**
     * Constructor
     */
    public Options() {
        volume = DEFAULT_VOLUME;
		mute=false;
    }



    public void setVolume(double newVolume){
        this.volume=newVolume;
    }

    public double getVolume(){
        return this.volume;
    }

    public boolean isMute(){
        return mute;
    }
}
