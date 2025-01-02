import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        getDirectory();
    }


    public static void getDirectory() {
        // get the directory to scan
        String directory = System.getenv("SystemDrive");
        // for testing we will use the user dir
        directory = System.getProperty("user.dir") + "/TestFolderFINAl";

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the directory to scan: ");
//        directory = scanner.nextLine();
        System.out.println("Current directory: " + directory);
        scanDir(directory);

    }

    //  scan all files and folders
    public static void scanDir(String directory) {
        // get all files and folders in the directory
        File files = new File(directory);
        File[] fileList = files.listFiles();
        for (File file : fileList) {
            if (file.isDirectory()) {
                scanDir(file.getAbsolutePath());
            } else {
                try {
                    String fileHash = calculateHash(file);
                    System.out.println(file.getAbsolutePath() + " : " + fileHash);
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

        }
    }


// read files (chunk larger files) calculate hash for file
    public static String calculateHash(File file) throws IOException, NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    // Process file in chunks to handle large files
    try (FileInputStream fis = new FileInputStream(file)) {
        byte[] buffer = new byte[8192]; // 8 KB buffer
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
    }

    // Convert hash bytes to a hexadecimal string
    byte[] hashBytes = digest.digest();
    StringBuilder hashString = new StringBuilder();
    for (byte b : hashBytes) {
        hashString.append(String.format("%02x", b)); // Format each byte as a 2-digit hex
    }
    return hashString.toString();
}

//check for a duplicate if duplicate found print the path of the duplicate file store in duplicate list
// else store in the hash map
}
