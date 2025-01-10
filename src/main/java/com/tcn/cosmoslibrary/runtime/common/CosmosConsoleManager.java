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
		private String simple_name;
		private String cap_name;
		private String display_name;
		private String console_name;
		private Level log_level;
		
		private LEVEL(int indexIn, String simpleNameIn, String capNameIn, String displayNameIn, String consoleNameIn, Level levelIn) {
			this.index = indexIn;
			this.simple_name = simpleNameIn;
			this.cap_name = capNameIn;
			this.display_name = displayNameIn;
			this.console_name = consoleNameIn;
			this.log_level = levelIn;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getSimpleName() {
			return this.simple_name;
		}
		
		public String getCapName() {
			return this.cap_name;
		}
		
		public String getDisplayName() {
			return this.display_name;
		}
		
		public String getConsoleName() {
			return this.console_name;
		}
		
		public Level getLevel() {
			return this.log_level;
		}
	}

	private Logger LOGGER;
	private String MOD_ID;
	private boolean debugEnabled;
	private boolean infoEnabled;

	public CosmosConsoleManager(String modId) {
		this(modId, true, true);
	}
	
	public CosmosConsoleManager(String modId, boolean debugEnabledIn, boolean infoEnabledIn) {
		this.LOGGER = LogManager.getLogger();
		this.MOD_ID = modId;
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
		this.messageString(LEVEL.PRINT, object);
	}
	
	public void info(Object object) {
		this.messageString(LEVEL.INFO, object);
	}

	public void debug(Object object) {
		this.messageString(LEVEL.DEBUG, object);
	}

	public void debugWarn(Object object) {
		this.messageString(LEVEL.DEBUG_WARNING, object);
	}

	public void startup(Object object) {
		this.messageString(LEVEL.STARTUP, object);
	}

	public void shutdown(Object object) {
		this.messageString(LEVEL.SHUTDOWN, object);
	}

	public void warning(Object object) {
		this.messageString(LEVEL.WARNING, object);
	}

	public void warning(Object object, Throwable e) {
		this.messageString(LEVEL.WARNING, object, e);
	}

	public void fatal(Object object) {
		this.messageString(LEVEL.FATAL, object);
	}

	public void fatal(Object object, Throwable e) {
		this.messageString(LEVEL.FATAL, object, e);
	}
	
	private void messageString(LEVEL level, Object object) {
		this.message(level, object.toString());
	}

	private void messageString(LEVEL level, Object object, Throwable e) {
		this.message(level, object.toString(), e);
	}
	
	private void message(LEVEL level, String message) {
		this.message(level, message, null);
	}

	public void message(LEVEL level, Object object, Throwable t) {
		if (!this.debugEnabled && level.equals(LEVEL.DEBUG) || !this.infoEnabled && level.equals(LEVEL.INFO)) {
			return;
		}
		
		System.out.println("[" + CosmosUtil.getTimeHMS() + "] [Cosmos Thread/" + level.getCapName() + "] [" + this.MOD_ID + "] [" + this.getSimpleCallerCallerClassName() + "] [" + this.getSimpleCallerClassName() + "]: " + object);
		
		if (t != null) {
			t.printStackTrace();
		}
	}
	
	/**
	 * Gets the current time.
	 * @return The current time in the format: [YYYY-MM-DD | HH-MM-SS]
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