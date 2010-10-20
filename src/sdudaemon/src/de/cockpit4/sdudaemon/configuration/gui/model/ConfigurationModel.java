package de.cockpit4.sdudaemon.configuration.gui.model;

import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.ModelChangeListener;
import de.cockpit4.sdudaemon.configuration.gui.model.eventhandling.Observable;
import java.util.ArrayList;

/**
 *
 * @author kneo
 */
public class ConfigurationModel implements Observable{
	private String configPath;
	private String libraryPath;
	private boolean loggerEnabled;
	private String loggerPath;
	private String statePath;
	private String tempPath;

	private ArrayList<ModelChangeListener> changeListeners;

	private byte foundLibraries;
	public ConfigurationModel(String configPath) {
		this.configPath = configPath;

		changeListeners = new ArrayList<ModelChangeListener>();
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

	public void fireChangeEvent() {
		for(ModelChangeListener i : changeListeners){
			i.onChange();
		}
	}

	public void addListener(ModelChangeListener listener) {
		this.changeListeners.add(listener);
	}

	public void removeListener(ModelChangeListener listener) {
		this.changeListeners.remove(listener);
	}
}
