import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class Archive {

    public static boolean createZipArchive(String sourceFile, String zipFile) {
        try {
            if (!zipFile.toLowerCase().endsWith(".zip")) {
                zipFile += ".zip";
            }

            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                File fileToZip = new File(sourceFile);
                if (!fileToZip.exists()) {
                    System.err.println("Ошибка: исходный файл не существует: " + sourceFile);
                    return false;
                }

                addFileToZip(fileToZip, fileToZip.getName(), zos);
                System.out.println("ZIP архив успешно создан: " + new File(zipFile).getAbsolutePath());
                return true;

            }
        } catch (IOException e) {
            System.err.println("Ошибка создания ZIP архива: " + e.getMessage());
            return false;
        }
    }

    public static boolean createZipArchive(List<String> sourceFiles, String zipFile) {
        try {
            if (!zipFile.toLowerCase().endsWith(".zip")) {
                zipFile += ".zip";
            }

            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                int addedFiles = 0;
                for (String sourceFile : sourceFiles) {
                    File fileToZip = new File(sourceFile);
                    if (fileToZip.exists()) {
                        addFileToZip(fileToZip, fileToZip.getName(), zos);
                        addedFiles++;
                        System.out.println("  Добавлен файл: " + fileToZip.getName());
                    } else {
                        System.err.println("  Предупреждение: файл не существует: " + sourceFile);
                    }
                }

                if (addedFiles > 0) {
                    System.out.println("ZIP архив успешно создан: " + new File(zipFile).getAbsolutePath() +
                            " (файлов: " + addedFiles + ")");
                    return true;
                } else {
                    System.err.println("Ошибка: ни один файл не был добавлен в архив");
                    return false;
                }

            }
        } catch (IOException e) {
            System.err.println("Ошибка создания ZIP архива: " + e.getMessage());
            return false;
        }
    }

    public static boolean extractZipArchive(String zipFile, String destDirectory) {
        try {
            File zip = new File(zipFile);
            if (!zip.exists()) {
                if (!zipFile.toLowerCase().endsWith(".zip")) {
                    zip = new File(zipFile + ".zip");
                }
                if (!zip.exists() && !zipFile.toLowerCase().endsWith(".jar")) {
                    zip = new File(zipFile + ".jar");
                }
                if (!zip.exists()) {
                    System.err.println("Ошибка: архив не существует: " + zipFile);
                    return false;
                }
                zipFile = zip.getAbsolutePath();
            }

            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry zipEntry = zis.getNextEntry();
                int extractedFiles = 0;

                while (zipEntry != null) {
                    File newFile = new File(destDir, zipEntry.getName());

                    String destDirPath = destDir.getCanonicalPath();
                    String destFilePath = newFile.getCanonicalPath();
                    if (!destFilePath.startsWith(destDirPath + File.separator)) {
                        throw new IOException("Опасный путь в архиве: " + zipEntry.getName());
                    }

                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Не удалось создать директорию: " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (parent != null && !parent.exists() && !parent.mkdirs()) {
                            throw new IOException("Не удалось создать директорию: " + parent);
                        }

                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, length);
                            }
                        }
                        extractedFiles++;
                        System.out.println("  Извлечен: " + zipEntry.getName());
                    }

                    zipEntry = zis.getNextEntry();
                }

                System.out.println("ZIP архив успешно извлечен: " + extractedFiles + " файлов в " + destDirectory);
                return true;
            }

        } catch (IOException e) {
            System.err.println("Ошибка извлечения из ZIP архива: " + e.getMessage());
            return false;
        }
    }

    public static boolean createJarArchive(String sourceFile, String jarFile) {
        try {
            if (!jarFile.toLowerCase().endsWith(".jar")) {
                jarFile += ".jar";
            }

            boolean success = createZipArchive(sourceFile, jarFile);
            if (success) {
                System.out.println("JAR архив успешно создан: " + new File(jarFile).getAbsolutePath());
            }
            return success;

        } catch (Exception e) {
            System.err.println("Ошибка создания JAR архива: " + e.getMessage());
            return false;
        }
    }

    private static void addFileToZip(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }

        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zos.closeEntry();

            File[] children = fileToZip.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    addFileToZip(childFile, fileName + "/" + childFile.getName(), zos);
                }
            }
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
        }
    }

    public static boolean createZipArchiveRelative(String sourceFile, String zipFile, String basePath) {
        try {
            if (!zipFile.toLowerCase().endsWith(".zip")) {
                zipFile += ".zip";
            }

            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                File fileToZip = new File(sourceFile);
                if (!fileToZip.exists()) {
                    System.err.println("Ошибка: исходный файл не существует: " + sourceFile);
                    return false;
                }

                String relativePath = getRelativePath(fileToZip, new File(basePath));
                addFileToZip(fileToZip, relativePath, zos);
                System.out.println("ZIP архив успешно создан: " + new File(zipFile).getAbsolutePath());
                return true;

            }
        } catch (IOException e) {
            System.err.println("Ошибка создания ZIP архива: " + e.getMessage());
            return false;
        }
    }

    private static String getRelativePath(File file, File base) throws IOException {
        String filePath = file.getCanonicalPath();
        String basePath = base.getCanonicalPath();

        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length() + 1);
        }
        return file.getName();
    }
}