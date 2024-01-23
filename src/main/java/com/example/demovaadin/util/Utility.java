package com.example.demovaadin.util;

import java.awt.image.BufferedImage;
import java.beans.FeatureDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Utility
 */
public final class Utility {


    public static String generateOtp() {
        Random random = new Random();
        int num = random.nextInt(90000) + 10000; // generate a random number between 10000 and 99999
        return String.valueOf(num);
    }

    // used by BeanUtils to ignore null values
    // goal is to copy fields of one object into another, but only those that aren't null
    // sering kupakai wkt write ke inbound
    // https://stackoverflow.com/questions/41125384/copy-non-null-properties-from-one-object-to-another-using-beanutils-or-similar
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static String random(String[] array) {
        int idx = new Random().nextInt(array.length);
        String random = (array[idx]);
        return random;
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

    public static boolean isEmpty(String val) {
        if (val == null)
            return true;
        if (val.trim().length() < 1)
            return true;
        return false;
    }

    public static boolean isEmpty(Object[] val) {
        if (val == null)
            return true;
        if (val.length < 1)
            return true;
        return false;
    }

    public static boolean isEmpty(Collection list) {
        if (list == null)
            return true;
        if (list.size() < 1)
            return true;
        return false;
    }

    public static void printList(List<?> list) {
        System.out.println(Arrays.toString(list.toArray()));
    }

    public static String NVL(String value, String defaultVal) {
        return value == null ? defaultVal : value;
    }

    public static BigDecimal NVL(BigDecimal value, BigDecimal defaultVal) {
        return value == null ? defaultVal : value;
    }

    public static BigDecimal NVL(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static Long NVL(Long value, Long defaultVal) {
        return value == null ? defaultVal : value;
    }

    public static ByteArrayInputStream compressPicture(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);

        // The important part: Create in-memory stream
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(compressed);

        // NOTE: The rest of the code is just a cleaned up version of your code

        // Obtain writer for JPEG format
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

        // Configure JPEG compression: 70% quality
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.7f);

        // Set your in-memory stream as the output
        jpgWriter.setOutput(outputStream);

        // Write image as JPEG w/configured settings to the in-memory stream
        // (the IIOImage is just an aggregator object, allowing you to associate
        // thumbnails and metadata to the image, it "does" nothing)
        jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

        // Dispose the writer to free resources
        jpgWriter.dispose();

        // Get data for further processing...
        byte[] jpegData = compressed.toByteArray();

        outputStream.close();
        compressed.close();

        return new ByteArrayInputStream(jpegData);

        // return outputStream;
    }

    public static void copyFile(String from, String to) throws IOException {

        // Path copied = Paths.get("src/test/resources/copiedWithNio.txt");
        Path copied = Paths.get(to);
        Path originalPath = Paths.get(from);
        // Path originalPath = original.toPath();

        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
    }

    public static LocalDateTime changeTime(LocalDateTime src, int hour, int minute, int seconds) {
        return src.withHour(hour).withMinute(minute).withSecond(seconds).withNano(0);
    }

    public static String includePath(String path, char sChar) {
        if (path.charAt(path.length() - 1) != sChar) {
            path += File.separator;
        }
        return path;
    }

    public static String includePath(String path) {
        if (path.charAt(path.length() - 1) != '/') {
            path += File.separator;
        }
        return path;
    }

    public static String leftPad(int n, int padding) {
        return String.format("%0" + padding + "d", n);
    }

    public static String leftPad(long n, int padding) {
        return String.format("%0" + padding + "d", n);
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static String getValueFromSysInfo(String sysInfo, String key) {
        if (isEmpty(sysInfo))
            return null;
        String[] split = sysInfo.split(",", -1);
        for (String s : split) {
            String[] split2 = s.split("=", -1);
            if (split2[0].equals(key)) {
                return split2[1];
            }
        }
        return null;
    }

    public static String getFirstTwoChars(String fullName) {

        if (isEmpty(fullName))
            return "?";
        String firstTwoChars = fullName.substring(0, 1);
        String words[] = fullName.split(" ", -1);
        for (String word : words) {
            if (word.length() > 1) {
                if (!isAlpha(word)) {
                    continue;
                } else {
                    firstTwoChars = word.substring(0, 2);
                    break;
                }
            }
        }
        return firstTwoChars;
    }

    /**
     * @param ac amount collected
     * @param ambc amount to be collected
     */
    public static double getPercentage(Double ac, Double ambc) {
        double x = (ac / ambc) * 100;
        return Math.floor(x * 100) / 100;
    }

    public static String getPrettyPercentage(Double value) {
        if (value % 1 == 0)
            return "" + (int) Math.round(value);
        return String.valueOf(value.doubleValue());
    }

    /**
     * @return no decimal places when needed
     */
    public static String getPrettyPercentage(Double ac, Double ambc) {
        if (ac == null || ambc == null || ambc == 0.0)
            return "0";
        double x = getPercentage(ac, ambc);
        return getPrettyPercentage(x);
    }

    public static int parseInt(String value, int defaultVal) {
        int _val = defaultVal;
        try {
            _val = Integer.parseInt(value);
        } catch (Exception e) {
        }
        return _val;
    }

    public static String getFileExtension(String file) {
        int lastIndexOf = file.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return file.substring(lastIndexOf);
    }

    public static String getFilenameWithoutExtension(String file) {
        int lastIndexOf = file.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return file;
        }
        return file.substring(0, lastIndexOf);
    }
}
