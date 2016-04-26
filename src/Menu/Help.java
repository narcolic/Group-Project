package Menu;

public class Help {

	private String[] textHelp; // stores text help instructions
	private String[] imageLoc; // stores image help instructions
	private int slideNumber; // index of current instruction

	/**
	 * Constructor
	 */
	public Help() {
		textHelp = new String[5];
		imageLoc = new String[5];
		slideNumber = 0;
	}

	/**
	 * Get the current text help instruction
	 * @return textHelp The current text help instruction
	 */
	public String getCurrentText() {
		return this.textHelp[this.slideNumber];
	}

	/**
	 * Get the current image help instruction
	 * @return imageLoc The location of the image
	 */
	public String getCurrentImageLocation() {
		return this.imageLoc[this.slideNumber];
	}

	/**
	 * Goes to next help instruction
	 */
	void nextSlide() {
		if (isNextAvailable()) {
			this.slideNumber++; // increment index
		}
	}

	/**
	 * Check to see if there is a next help instruction
	 * @return slideNumber Index within the array length
	 */
	boolean isNextAvailable() {
		return this.slideNumber < (textHelp.length - 1);
	}

	/**
	 * Check to see if there is a previous help instruction
	 * @return slideNumber Index pointing to instruction within array
	 */
	boolean isPreviousAvailable() {
		return slideNumber >= 1;
	}

	/**
	 * Go to previous help instruction
	 */
	void previousSlide() {
		if (isPreviousAvailable()) {
			this.slideNumber--; // decrement index
		}
	}

	/**
	 * Initialise the text help instructions
	 */
	void fillTextHelpArray() {
		textHelp = new String[]{"sadasdsadas1",
				"asdasdasdsad2",
				"asdasdasdad3",
				"dsadasdsad4",
				"asdasdas5"};
	}

	/**
	 * Initialise the help image url's 
	 */
	void fillImageLocArray() {
		imageLoc = new String[]{"/Menu/Images/dog1.png",
				"/Menu/Images/dog.png",
				"/Menu/Images/dog1.png",
				"/Menu/Images/dog.png",
				"/Menu/Images/dog1.png"};
	}
}
