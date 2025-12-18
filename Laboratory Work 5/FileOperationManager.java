import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileOperationManager {
    private ExecutorService executor;
    private JProgressBar progressBar;

    public FileOperationManager(JProgressBar progressBar) {
        this.executor = Executors.newFixedThreadPool(3);
        this.progressBar = progressBar;
    }

    public void readFileAsync(String filePath, String format, FileOperationCallback callback) {
        executor.submit(() -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setIndeterminate(true);
                progressBar.setString("Чтение " + format + " файла...");
            });

            try {
                Storage<Factory> devices = null;

                switch (format.toUpperCase()) {
                    case "TXT":
                        ReadFile readFile = new ReadFile();
                        devices = readFile.readDevicesFromFile(filePath);
                        break;
                    case "XML":
                        XMLReadFile xmlReadFile = new XMLReadFile();
                        devices = xmlReadFile.readDevicesFromXML(filePath);
                        break;
                    case "JSON":
                        JSONReadFile jsonReadFile = new JSONReadFile();
                        devices = jsonReadFile.readDevicesFromJSON(filePath);
                        break;
                }

                final Storage<Factory> result = devices;
                SwingUtilities.invokeLater(() -> {
                    callback.onSuccess(result);
                    progressBar.setIndeterminate(false);
                    progressBar.setString("Чтение завершено");
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    callback.onError(e);
                    progressBar.setIndeterminate(false);
                    progressBar.setString("Ошибка чтения");
                });
            }
        });
    }

    public void writeFileAsync(String filePath, String format, Storage<Factory> storage,
                               FileOperationCallback callback) {
        executor.submit(() -> {
            SwingUtilities.invokeLater(() -> {
                progressBar.setIndeterminate(true);
                progressBar.setString("Запись " + format + " файла...");
            });

            try {
                boolean success = false;

                switch (format.toUpperCase()) {
                    case "TXT":
                        WriteFile writeFile = new WriteFile();
                        success = writeFile.writeDevicesToFile(filePath, storage);
                        break;
                    case "XML":
                        XMLWriteFile xmlWriteFile = new XMLWriteFile();
                        success = xmlWriteFile.writeDevicesToXML(filePath, storage);
                        break;
                    case "JSON":
                        JSONWriteFile jsonWriteFile = new JSONWriteFile();
                        success = jsonWriteFile.writeDevicesToJSON(filePath, storage);
                        break;
                }

                final boolean result = success;
                SwingUtilities.invokeLater(() -> {
                    if (result) {
                        callback.onSuccess(null);
                    } else {
                        callback.onError(new Exception("Ошибка записи файла"));
                    }
                    progressBar.setIndeterminate(false);
                    progressBar.setString("Запись завершена");
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    callback.onError(e);
                    progressBar.setIndeterminate(false);
                    progressBar.setString("Ошибка записи");
                });
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public interface FileOperationCallback {
        void onSuccess(Object result);
        void onError(Exception e);
    }
}