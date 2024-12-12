package rip.diamond.moddedbukkit.util;

import lombok.experimental.UtilityClass;

import java.io.*;

@UtilityClass
public class FileUtil {

    private static final int BUFFER_SIZE = 4096;

    public static void extractFile(InputStream from, File to) {
        if (from == null) {
            throw new NullPointerException("Cannot get from (to = " + to.getName() + ")");
        }

        // Ensure the parent directory exists
        to.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(to); BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER_SIZE)) {
            byte[] data = new byte[BUFFER_SIZE];
            int count;
            while ((count = from.read(data, 0, BUFFER_SIZE)) != -1) {
                dest.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
