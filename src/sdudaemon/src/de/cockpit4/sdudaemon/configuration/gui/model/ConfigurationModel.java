package de.cockpit4.sdudaemon.configuration.gui.model;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;

/**This class stores the general configuration associated to a project.
 * It implements the Actor - Observer  Model so if a value is changed a listenable change event is fired.
 * The @see ConfigurationController uses this event to store each change the user made.
 * This is helping to keep the GUI simple and stupid or ergonomic if you prefer this term.
 */
public class ConfigurationModel extends AbstractObservableModel{
	private String configPath = "";
	private String libraryPath= "";
	private boolean loggerEnabled = false;
	private String loggerPath = "";
	private String statePath = "";
	private String tempPath = "";
	private byte foundLibraries = 0;

	/** constructor of this class taking the path to the configuration file which the controller need to initialize or load a configuration file
	 * @param configPath path to configuration file to associate with this model.
	 */
	public ConfigurationModel(String configPath) {
		this.configPath = configPath;
	}

	/**This sets a byte value to inform the user which library he needs to obtain.
	 * @param foundLibraries bit mask of found libraries
	 */
	public void setFoundLibraries(byte foundLibraries) {
		this.foundLibraries = foundLibraries;
	}

	/**Gets a bit mask indicating which of them is missing in the form to the user
	 * @return bit mask of found libraries
	 */
	public byte getFoundLibraries() {
		return foundLibraries;
	}
	/**Returns the configuration path of the current configuration file
	 * @return path of current configuration file
	 */
	public String getConfigPath() {
		return configPath;
	}
	/** Gets the Path determining where to find required libraries
	 * The application then matches the name of all files against patterns to see what file is available.
	 * The Path is necessary for shell and bash script generation.
	 * @return The path holding required library
	 */
	public String getLibraryPath() {
		return libraryPath;
	}
	/**This Method sets the Library Path of required files, @see ConfigurationModel.getLibraryPath for further information
	 * @param libraryPath required libraries hide out to set
	 */
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
		this.fireChangeEvent();
	}
	/**This option is required if you want to know whats going on. After setting this option enabled the logger writes the logfiles of each application run into the LoggerPath directory
	 * @return boolean value of logging state.
	 */
	public boolean isLoggerEnabled() {
		return loggerEnabled;
	}
	/**En-/Disables the logger.
	 * @param loggerEnabled state to set
	 */
	public void setLoggerEnabled(boolean loggerEnabled) {
		this.loggerEnabled = loggerEnabled;
		this.fireChangeEvent();
	}
	/**Path wherein the application should store log files.
	 * @return String Path of log file storage
	 */
	public String getLoggerPath() {
		return loggerPath;
	}
	/**Sets the Path to store the log files in.
	 * @param loggerPath Directory storing
	 */
	public void setLoggerPath(String loggerPath) {
		this.loggerPath = loggerPath;
		this.fireChangeEvent();
	}
	/**This method returns the path where project related state files are stored.
	 * Its required to get a sort of project and problem management.
	 * If a application crashes because the JVM lags of memory its possible to restart the project with no need to begin at the start.
	 * SDUDaemon will continue the last active state.
	 * @return path where state files will be stored.
	 */
	public String getStatePath() {
		return statePath;
	}
	/**Set the state file path
	 * @param statePath directory containing project state files
	 */
	public void setStatePath(String statePath) {
		this.statePath = statePath;
		this.fireChangeEvent();
	}
	/**The Webharvest framework generates a lot of temporary files so it needs a directory to dump them.
	 * @return path where temporary files are stored
	 */
	public String getTempPath() {
		return tempPath;
	}
	/**Sets the path for temporary files
	 * @param tempPath Path to store temporary files
	 */
	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
		this.fireChangeEvent();
	}
}
