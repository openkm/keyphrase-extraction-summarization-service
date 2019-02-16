package com.openkm.util;

/*-
 * #%L
 * kea-summarization
 * %%
 * Copyright (C) 2018 - 2019 OpenKM Knowledge Management System S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class FileUtils {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Returns the name of the file without the extension.
     */
    public static String getFileName(String file) {
        int idx = file.lastIndexOf(".");
        String ret = idx >= 0 ? file.substring(0, idx) : file;
        return ret;
    }

    /**
     * Returns the filename extension.
     */
    public static String getFileExtension(String file) {
        int idx = file.lastIndexOf(".");
        String ret = idx >= 0 ? file.substring(idx + 1) : "";
        return ret;
    }

    /**
     * Get parent node.
     */
    public static String getParent(String file) {
        int lastSlash = file.lastIndexOf(File.separator);
        String ret = (lastSlash > 0) ? file.substring(0, lastSlash) : File.separator;
        return ret;
    }

    /**
     * Creates a temporal and unique directory
     *
     * @throws IOException If something fails.
     */
    public static File createTempDir() throws IOException {
        File tmpFile = File.createTempFile("okm", null);

        if (!tmpFile.delete())
            throw new IOException();
        if (!tmpFile.mkdir())
            throw new IOException();
        return tmpFile;
    }

    /**
     * Create temp file
     */
    public static File createTempFile() throws IOException {
        return File.createTempFile("okm", ".tmp");
    }

    /**
     * Create temp file
     */
    public static File createTempFile(String ext) throws IOException {
        return File.createTempFile("okm", "." + ext);
    }

    /**
     * Wrapper for FileUtils.deleteQuietly
     *
     * @param file File or directory to be deleted.
     */
    public static boolean deleteQuietly(File file) {
        return org.apache.commons.io.FileUtils.deleteQuietly(file);
    }

    /**
     * Wrapper for FileUtils.cleanDirectory
     *
     * @param dir File or directory to be deleted.
     */
    public static void cleanDirectory(File dir) throws IOException {
        org.apache.commons.io.FileUtils.cleanDirectory(dir);
    }

    /**
     * Wrapper for FileUtils.listFiles
     *
     * @param dir File or directory to be listed.
     */
    @SuppressWarnings("unchecked")
    public static Collection<File> listFiles(File dir, String[] extensions, boolean recursive) {
        return org.apache.commons.io.FileUtils.listFiles(dir, extensions, recursive);
    }

    /**
     * Wrapper for FileUtils.readFileToByteArray
     *
     * @param file File or directory to be deleted.
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    /**
     * Wrapper for FileUtils.writeStringToFile
     *
     * @param file the file to write
     * @param data the content to write to the file
     */
    public static void writeStringToFile(File file, String data) throws IOException {
        org.apache.commons.io.FileUtils.writeStringToFile(file, data);
    }

    /**
     * Wrapper for FileUtils.readFileToString
     *
     * @param file the file to read, must not be <code>null</code>
     */
    public static String readFileToString(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file);
    }

    /**
     * Delete directory if empty
     */
    public static void deleteEmpty(File file) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            }
        }
    }

    /**
     * Count files and directories from a selected directory.
     */
    public static int countFiles(File dir) {
        File[] found = dir.listFiles();
        int ret = 0;

        if (found != null) {
            for (int i = 0; i < found.length; i++) {
                if (found[i].isDirectory()) {
                    ret += countFiles(found[i]);
                }

                ret++;
            }
        }

        return ret;
    }

    /**
     * Copy InputStream to File.
     */
    public static void copy(InputStream input, File output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        IOUtils.copy(input, fos);
        fos.flush();
        fos.close();
    }

    /**
     * Copy Reader to File.
     */
    public static void copy(Reader input, File output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        IOUtils.copy(input, fos);
        fos.flush();
        fos.close();
    }

    /**
     * Copy File to OutputStream
     */
    public static void copy(File input, OutputStream output) throws IOException {
        FileInputStream fis = new FileInputStream(input);
        IOUtils.copy(fis, output);
        fis.close();
    }

    /**
     * Copy File to File
     */
    public static void copy(File input, File output) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(input, output);
    }

    /**
     * Check if file exists
     */
    public static boolean existFile(String file) {
        return existFile(new File(file));
    }

    /**
     * Check if file exists
     */
    public static boolean existFile(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Check if directory exists
     */
    public static boolean existDirectory(String dir) {
        return existDirectory(new File(dir));
    }

    /**
     * Check if directory exists
     */
    public static boolean existDirectory(File dir) {
        return dir.exists() && dir.isDirectory();
    }

    /**
     * Remove reserved characters from filename
     * <p>
     * https://msdn.microsoft.com/en-us/library/aa365247
     */
    public static String toValidFilename(String filename) {
        return filename.replaceAll("[\\\\/:\"*?<>|]+", "");
    }

    /**
     * Returns the name of files with the same name
     */
    public static String getFileNameNotDuplicatedInDestinationPath(String pathFile, String name, String ext) {
        File file = new File(pathFile + File.separator + name + "." + ext);
        int count = 1;

        while (file.exists()) {
            file = new File(pathFile + File.separator + name + "(" + count + ")." + ext);
            count++;
        }

        return file.getName();
    }
}
