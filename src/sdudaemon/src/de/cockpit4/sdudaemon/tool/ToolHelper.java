/*
Copyright (c) 2011 cockpit4, Kevin Krüger

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package de.cockpit4.sdudaemon.tool;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Helper class containing several reusable functions
 * @author kneo
 */
public class ToolHelper {

    /** Determine if a path subtree is writable to the current user.
     * @param filename File to check (actual file or directory)
     * @return true if user has write access to a parent directory of the path, so the required subdirectory may be created. False if there is no write privilege to the user.
     */
    public static boolean installPathWritable(File filename) { //check a path if a parent directory is writable
        File parent = filename;
        do {
            if (parent.exists()) {
                if (parent.canWrite()) {
                    return true;
                }
            }
            parent = parent.getParentFile();
        } while (parent != null); //stop if the root appears
        return false;
    }

    /**Generalized quicksort algorithm using Sortable arrays
     * @param numbers sortable elements
     * @param low lower index
     * @param high higher index
     */
    public static void quicksort(Sortable[] numbers, int low, int high) {
        int i = low, j = high;
        Sortable pivot = numbers[(low + high) / 2];

        while (i <= j) {

            while (numbers[i].lesserThan(pivot)) {
                i++;
            }

            while (numbers[j].greaterThan(pivot)) {
                j--;
            }

            if (i <= j) {
                numbers[i].swap(numbers[j]);
                i++;
                j--;
            }
        }

        if (low < j) {
            quicksort(numbers, low, j);
        }
        if (i < high) {
            quicksort(numbers, i, high);
        }
    }

    /**Returns the hexadecimal string representation of a by
     * @param c byte to transform
     * @return representation of this byte. Like : AB or 3C
     */
    public static String byteToHex(byte c) {
        int l = (c & 0x0f);
        int h = (c & 0xf0) >> 4;
        char H;
        char L;

        H = h < 0x0A ? (char) (0x30 + h) : (char) (0x41 + (h - 0x0A));
        L = l < 0x0A ? (char) (0x30 + l) : (char) (0x41 + (l - 0x0A));
        return "" + H + L;
    }

    public static byte[] md5sum(byte[] data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(data);
            return md5.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ToolHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static String bytesToHexString(byte[] data) {
        String checksum = "";
        if (data != null) {
            for (byte t : data) {
                checksum += ToolHelper.byteToHex(t);
            }
        }
        return checksum;
    }
}
