package Menu;

public class Options {

    private double volume; //sliders value
    //private Slider volumeSlider; //maybe on MenuView
    private boolean mute;
    private static double DEFAULT_VOLUME = 50.0; // Scale to 1-100 probably

    /**
     * Constructor
     */
    public Options() {
        volume = DEFAULT_VOLUME;
		mute=false;
        //volumeSlider=new Slider("Volume", DEFAULT_VOLUME, 0, 100,1); //maybe on MenuView
    }

    public void muteVolume(){
        this.volume=0;
        mute=true;
    }

    public void setVolume(double newVolume){
        this.volume=newVolume;
    }

    public double getVolume(){
        return volume;
    }

}
