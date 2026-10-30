import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** Supplied offline exam utility, not part of the student's implementation. */
class Session {
  private static final long RELEASE = 50 * 60_000L;
  private static final long END = 90 * 60_000L;
  private static final Path CLOCK = Path.of(".session");
  private static final Path MARKER = Path.of(".task2-unlocked");
  private static final String KEY = "cpX6QieluPW3kpVygCc9P26XvEhef3onLGMtevNCgO0=";

  public static void main(String[] args) {
    try {
      if (Runtime.version().feature() < 17) {
        throw new IllegalStateException("Java 17 or later is required.");
      }
      if (args.length != 1) { throw new IllegalArgumentException("Use the supplied scripts."); }
      if (args[0].equals("start")) {
        if (!Files.exists(CLOCK)) {
          for (String file : new String[] {"task1.md", "task2.secret", "test.jar"}) {
            if (!Files.isRegularFile(Path.of(file))) {
              throw new IllegalStateException("Missing " + file + ": extract the whole package.");
            }
          }
          Files.writeString(CLOCK, Long.toString(System.currentTimeMillis()), StandardOpenOption.CREATE_NEW);
          System.out.println("90-minute session started. Task 2 releases at minute 50.");
        } else { System.out.println("Session already started; timer retained."); }
        report(elapsed());
      } else if (args[0].equals("status")) {
        report(elapsed());
      } else if (args[0].equals("unlock")) {
        long elapsed = elapsed();
        if (elapsed < RELEASE) {
          throw new IllegalStateException("Task 2 is locked for another " + duration(RELEASE - elapsed) + ".");
        }
        if (Files.exists(MARKER)) {
          System.out.println("Task 2 is already unlocked. Read task2.md.");
          report(elapsed);
          return;
        }
        unlock();
        Files.writeString(MARKER, "Task 2 released\n", StandardOpenOption.CREATE_NEW);
        System.out.println("Task 2 unlocked. Read task2.md. Your Java implementation was not changed.");
        report(elapsed);
      } else { throw new IllegalArgumentException("Unknown session command."); }
    } catch (Exception e) {
      System.err.println("Session: " + e.getMessage());
      System.exit(1);
    }
  }

  private static long elapsed() throws Exception {
    if (!Files.isRegularFile(CLOCK)) { throw new IllegalStateException("Run bash start.sh first."); }
    long start = Long.parseLong(Files.readString(CLOCK).trim());
    long elapsed = System.currentTimeMillis() - start;
    if (elapsed < 0) { throw new IllegalStateException("System clock is earlier than the recorded start."); }
    return elapsed;
  }

  private static String duration(long millis) {
    long seconds = (millis + 999) / 1000;
    return (seconds / 60) + "m " + (seconds % 60) + "s";
  }

  private static void report(long elapsed) {
    System.out.println("Elapsed: " + duration(elapsed));
    if (elapsed < RELEASE) { System.out.println("Task 2 available in: " + duration(RELEASE - elapsed)); }
    else { System.out.println("Task 2 is available; use bash unlock.sh if needed."); }
    if (elapsed < END) { System.out.println("Time remaining: " + duration(END - elapsed)); }
    else { System.out.println("90 minutes have elapsed. Stop coding and save your attempt."); }
  }

  private static void unlock() throws Exception {
    byte[] encrypted = Base64.getMimeDecoder().decode(Files.readString(Path.of("task2.secret")));
    if (encrypted.length < 28) { throw new IllegalStateException("Invalid task2.secret."); }
    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getDecoder().decode(KEY), "AES"),
        new GCMParameterSpec(128, Arrays.copyOfRange(encrypted, 0, 12)));
    byte[] plain = cipher.doFinal(Arrays.copyOfRange(encrypted, 12, encrypted.length));
    Set<String> allowed = Set.of("task2.md", "test.jar", "tests/Test5.java", "tests/Test6.java", "tests/Test7.java");
    Map<String, byte[]> files = new LinkedHashMap<String, byte[]>();
    try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(plain), StandardCharsets.UTF_8)) {
      ZipEntry entry;
      while ((entry = zip.getNextEntry()) != null) {
        if (!allowed.contains(entry.getName()) || files.containsKey(entry.getName())) {
          throw new IllegalStateException("Unexpected item in release package.");
        }
        files.put(entry.getName(), zip.readAllBytes());
      }
    }
    if (!files.keySet().equals(allowed)) { throw new IllegalStateException("Incomplete release package."); }
    for (Map.Entry<String, byte[]> entry : files.entrySet()) {
      Path path = Path.of(entry.getKey());
      if (path.getParent() != null) { Files.createDirectories(path.getParent()); }
      Files.write(path, entry.getValue());
    }
  }
}
