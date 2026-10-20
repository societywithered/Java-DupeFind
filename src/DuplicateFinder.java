import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DuplicateFinder {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java DuplicateFinder <folder>");
            return;
        }

        Path folder = Path.of(args[0]);

        if (!Files.isDirectory(folder)) {
            System.err.println("Not a folder: " + folder);
            return;
        }

        Map<String, Path> filesByHash = new HashMap<>();

        try (Stream<Path> paths = Files.walk(folder)) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                try {
                    String hash = hash(path);
                    Path original = filesByHash.putIfAbsent(hash, path);

                    if (original != null) {
                        System.out.println(path + " matches " + original);
                    }
                } catch (IOException | NoSuchAlgorithmException e) {
                    System.err.println("Could not read " + path + ": " + e.getMessage());
                }
            });
        }
    }

    private static String hash(Path path)
            throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (var input = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int count;

            while ((count = input.read(buffer)) != -1) {
                digest.update(buffer, 0, count);
            }
        }

        return java.util.HexFormat.of().formatHex(digest.digest());
    }
}