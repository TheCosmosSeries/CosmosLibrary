package com.tcn.cosmoslibrary.runtime.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tcn.cosmoslibrary.common.util.CosmosUtil;

/**
 * @author TheCosmicNebula_
 */
@SuppressWarnings({ "unused" })
public class CosmosConsoleManager {
	
	private enum LEVEL {
		PRINT(-1, "print", "PRINT", "Print", " [PRINT] ", Level.INFO),
		INFO(0, "info", "INFO", "Info", " [INFO] ", Level.INFO),
		DEBUG(1, "debug", "DEBUG", "Debug", " [DEBUG] ", Level.DEBUG),
		STARTUP(2, "startup", "STARTUP", "Startup", " [STARTUP] ", Level.INFO),
		SHUTDOWN(2, "shutdown", "SHUTDOWN", "Shutdown", " [SHUTDOWN] ", Level.INFO),
		WARNING(3, "warning", "WARNING", "Warning", " [WARNING] ", Level.WARN),
		FATAL(4, "fatal", "FATAL", "Fatal", " [FATAL] ", Level.FATAL),
		
		DEBUG_WARNING(5, "debug_warning", "DEBUG_WARN", "Debug Warning", " [DEBUG WARN] ", Level.WARN);
		
		private int index;
		private String simpleName;
		private String capName;
		private String displayName;
		private String consoleName;
		private Level logLevel;
		
		private LEVEL(int indexIn, String simpleNameIn, String capNameIn, String displayNameIn, String consoleNameIn, Level levelIn) {
			this.index = indexIn;
			this.simpleName = simpleNameIn;
			this.capName = capNameIn;
			this.displayName = displayNameIn;
			this.consoleName = consoleNameIn;
			this.logLevel = levelIn;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getSimpleName() {
			return this.simpleName;
		}
		
		public String getCapName() {
			return this.capName;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public String getConsoleName() {
			return this.consoleName;
		}
		
		public Level getLevel() {
			return this.logLevel;
		}
	}

	private String modId;
	private boolean debugEnabled;
	private boolean infoEnabled;

	public CosmosConsoleManager(String modId) {
		this(modId, true);
	}
	
	public CosmosConsoleManager(String modId, boolean enabledIn) {
		this(modId, enabledIn, enabledIn);
	}
	
	public CosmosConsoleManager(String modId, boolean debugEnabledIn, boolean infoEnabledIn) {
		this.modId = modId;
		this.debugEnabled = debugEnabledIn;
		this.infoEnabled = infoEnabledIn;
	}
	
	public void updateDebugEnabled(boolean valueIn) {
		this.debugEnabled = valueIn;
	}

	public void updateInfoEnabled(boolean valueIn) {
		this.infoEnabled = valueIn;
	}
	
	public void print(Object object) {
		this.printObjectString(LEVEL.PRINT, object);
	}
	
	public void info(Object object) {
		this.printObjectString(LEVEL.INFO, object);
	}

	public void debug(Object object) {
		this.printObjectString(LEVEL.DEBUG, object);
	}

	public void debugWarn(Object object) {
		this.printObjectString(LEVEL.DEBUG_WARNING, object);
	}

	public void startup(Object object) {
		this.printObjectString(LEVEL.STARTUP, object);
	}

	public void shutdown(Object object) {
		this.printObjectString(LEVEL.SHUTDOWN, object);
	}

	public void warning(Object object) {
		this.warning(object, null);
	}

	public void warning(Object object, Throwable e) {
		this.printObjectString(LEVEL.WARNING, object, e);
	}

	public void fatal(Object object) {
		this.fatal(object, null);
	}

	public void fatal(Object object, Throwable e) {
		this.printObjectString(LEVEL.FATAL, object, e);
	}

	private void printString(LEVEL level, String message) {
		this.printRaw(level, message, null);
	}
	
	private void printObjectString(LEVEL level, Object object) {
		this.printRaw(level, object.toString(), null);
	}

	private void printObjectString(LEVEL level, Object object, Throwable e) {
		this.printRaw(level, object.toString(), e);
	}
	
	public void printRaw(LEVEL level, Object object, Throwable t) {
		if (!this.debugEnabled && level.equals(LEVEL.DEBUG) || !this.infoEnabled && level.equals(LEVEL.INFO)) {
			return;
		}
		
		System.out.println("[" + CosmosUtil.getTimeHMS() + "] [cosmos-thread/" + level.getCapName() + "] [" + this.modId + "] [" + this.getSimpleCallerCallerClassName() + "] [" + this.getSimpleCallerClassName() + "]: " + object);
		
		if (t != null) {
			t.printStackTrace();
		}
	}
	
	/**
	 * Gets the current time.
	 * @return The current time in the format: [HH-mm-ss]
	 */
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		return dtf.format(now).replace("/", "-").replace(" ", " | ");
	}
	
	/**
	 * Method to access the current class.
	 * @return String [full.class.name]
	 */
	public String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(CosmosConsoleManager.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName().replace("_", "\\.");
			}
		}
		
		return null;
	}
	
	/**
	 * Method to return the simple class name of the current class.
	 * @return String [simpleclassname]
	 */
	public String getSimpleCallerClassName() {
		String c = getCallerClassName();
		String[] split = c.split("\\.");
		int last = (split.length - 1);
		return split[last];
	}

	/**
	 * Method to return the class name of the class calling the method;
	 * @return String [full.class.name]
	 */
	public String getCallerCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		String callerClassName = null;
		
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (!ste.getClassName().equals(CosmosConsoleManager.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
				if (callerClassName == null) {
					callerClassName = ste.getClassName().replace("_", "\\.");
				} else if (!callerClassName.equals(ste.getClassName())) {
					return ste.getClassName().replace("_", "\\.");
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Method to return the simple class name of the class calling the method.
	 * @return String [simpleclassname]
	 */
	public String getSimpleCallerCallerClassName() {
		String c = getCallerCallerClassName();
		String[] split = c.split("\\.");
		int last = (split.length - 1);
		return split[last];
	}
}