package me.iris.runcleaner;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class RunCleaner implements ModInitializer {
	/**
	 * Run Cleaner logger
	 */
	public static final Logger LOGGER = LogManager.getLogger("runcleaner");

	/**
	 * Current directory
	 */
	public static final String USER_DIR = System.getProperty("user.dir");


	/**
	 * Folder where all minecraft worlds are saved
	 */
	public static final File SAVES_DIR = new File(USER_DIR + File.separator + "saves" + File.separator);

	@Override
	public void onInitialize() {
		// Print current directory
		LOGGER.info("User directory: " + USER_DIR);
		if (!USER_DIR.contains(".minecraft"))
			LOGGER.warn("Current directory isn't .minecraft, if this is a development environment then you can ignore this.");

		// Print where we will scan for worlds to delete
		LOGGER.info("Saves will be searched for in: " + SAVES_DIR.getAbsolutePath());
	}
}