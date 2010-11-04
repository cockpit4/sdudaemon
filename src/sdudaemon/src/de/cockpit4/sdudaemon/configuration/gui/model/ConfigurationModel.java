package de.cockpit4.sdudaemon.configuration.gui.model;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.AbstractObservableModel;

/**
 *
 * @author kneo
 */
public class ConfigurationModel extends AbstractObservableModel{
	private String configPath = "";
	private String libraryPath= "";
	private boolean loggerEnabled = false;
	private String loggerPath = "";
	private String statePath = "";
	private String tempPath = "";


	private byte foundLibraries;
	public ConfigurationModel(String configPath) {
		this.configPath = configPath;
	}


	public void setFoundLibraries(byte foundLibraries) {
		this.foundLibraries = foundLibraries;
	}

	public byte getFoundLibraries() {
		return foundLibraries;
	}

	public String getConfigPath() {
		return configPath;
	}

	public String getLibraryPath() {
		return libraryPath;
	}

	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
		this.fireChangeEvent();
	}

	public boolean isLoggerEnabled() {
		return loggerEnabled;
	}

	public void setLoggerEnabled(boolean loggerEnabled) {
		this.loggerEnabled = loggerEnabled;
		this.fireChangeEvent();
	}

	public String getLoggerPath() {
		return loggerPath;
	}

	public void setLoggerPath(String loggerPath) {
		this.loggerPath = loggerPath;
		this.fireChangeEvent();
	}

	public String getStatePath() {
		return statePath;
	}

	public void setStatePath(String statePath) {
		this.statePath = statePath;
		this.fireChangeEvent();
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
		this.fireChangeEvent();
	}


}
