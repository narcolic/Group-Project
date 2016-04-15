package Menu;

public class Options {

    public double volume; //VolumeSlider value
    //private Slider volumeSlider; //maybe on MenuView
    public boolean mute;
    private static double DEFAULT_VOLUME = 50.0; // Scale to 1-100 probably

    /**
     * Constructor
     */
    public Options() {
        volume = DEFAULT_VOLUME;
		mute=false;
        //volumeSlider=new Slider("Volume", DEFAULT_VOLUME, 0, 100,1); //maybe on MenuView
    }



    public void setVolume(double newVolume){
        this.volume=newVolume;
    }

    public double getVolume(){
        return volume;
    }

}
