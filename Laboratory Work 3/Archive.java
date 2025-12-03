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
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File fileToZip = new File(sourceFile);
            addFileToZip(fileToZip, fileToZip.getName(), zos);
            return true;

        } catch (IOException e) {
            System.err.println("Ошибка создания ZIP архива: " + e.getMessage());
            return false;
        }
    }

    public static boolean createZipArchive(List<String> sourceFiles, String zipFile) {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String sourceFile : sourceFiles) {
                File fileToZip = new File(sourceFile);
                if (fileToZip.exists()) {
                    addFileToZip(fileToZip, fileToZip.getName(), zos);
                }
            }
            return true;

        } catch (IOException e) {
            System.err.println("Ошибка создания ZIP архива: " + e.getMessage());
            return false;
        }
    }

    public static boolean extractZipArchive(String zipFile, String destDirectory) {
        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    File newFile = new File(destDir, zipEntry.getName());

                    // Создаем директории если нужно
                    new File(newFile.getParent()).mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }

                    zipEntry = zis.getNextEntry();
                }
            }
            return true;

        } catch (IOException e) {
            System.err.println("Ошибка извлечения из ZIP архива: " + e.getMessage());
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
            for (File childFile : children) {
                addFileToZip(childFile, fileName + "/" + childFile.getName(), zos);
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

    public static boolean createJarArchive(String sourceFile, String jarFile) {
        return createZipArchive(sourceFile, jarFile);
    }
}