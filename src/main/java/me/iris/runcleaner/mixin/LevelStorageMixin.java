package me.iris.runcleaner.mixin;

import me.iris.runcleaner.RunCleaner;
import me.iris.runcleaner.utilities.StringUtil;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Mixin(LevelStorage.class)
public abstract class LevelStorageMixin {
    @Inject(
            method = "createSession",
            at = @At("HEAD")
    )
    public void createSession(String directoryName, CallbackInfoReturnable<LevelStorage.Session> info) throws IOException {
        RunCleaner.LOGGER.info("Preparing to delete old runs...");

        // Get files in saves directory
        final File[] files = RunCleaner.SAVES_DIR.listFiles((dir, name) -> name.contains("New World") || name.contains("Random Speedrun") && !name.equals(directoryName));

        // Check if any saves were found
        if (files == null || files.length == 0) {
            RunCleaner.LOGGER.error("Failed to get saves. (If there are none you can ignore this)");
            return;
        }

        // Sort files by number in name
        final List<File> sortedFiles = new ArrayList<>(List.of(files));
        sortedFiles.sort(Comparator.comparingInt(file -> {
            // Remove letters from string (Only leaving numbers)
            final String numberStr = StringUtil.removeLetters(file.getName());

            // Get the number from string & return it
            return numberStr.isEmpty() ? 0 : Integer.parseInt(numberStr);
        }));

        // Reverse list
        Collections.reverse(sortedFiles);

        // Remove first 5 directories
        if (sortedFiles.size() < 5) return;
        final List<File> directoriesToDelete = sortedFiles.subList(5, sortedFiles.size());

        // Delete directories
        if (directoriesToDelete.isEmpty()) return;
        for (File dir : directoriesToDelete) {
            FileUtils.deleteDirectory(dir);
            RunCleaner.LOGGER.info("Deleted save: " + dir.getAbsolutePath());
        }
    }
}
