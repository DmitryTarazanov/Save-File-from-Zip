package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("D://Games//savegames//save1.dat", "D://Games//savegames//save2.dat", "D://Games//savegames//save3.dat");
        saveGames(list.get(0), new GameProgress(100, 7, 1, 0));
        saveGames(list.get(1), new GameProgress(80, 5, 10, 20));
        saveGames(list.get(2), new GameProgress(50, 3, 23, 40));
        zipFiles("D://Games//savegames//zip.zip", list);
    }

    private static void saveGames(String str, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(str))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void zipFiles(String zipRoot, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipRoot))) {
            for (String file : list) {
                //Считываем файл, который необходимо заархивировать
                FileInputStream fileFromZip = new FileInputStream(file);
                //Создаём в архиве файл с таким же названием
                File saveFile = new File(file);
                ZipEntry entry = new ZipEntry(saveFile.getName());
                //Добавляем его к архиву
                zout.putNextEntry(entry);
                //Считываем содержимое файла в массив
                byte[] buffer = new byte[fileFromZip.available()];
                fileFromZip.read(buffer);
                //Добавляем содержимое в файл архива
                zout.write(buffer);
                //Закрываем запись
                fileFromZip.close();
                zout.closeEntry();
                saveFile.delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
