package org.example.mp3playerfx;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Getter
@Setter
public class Settings {
    private static Settings settingsInstance;
    private String path;
    private File settingsFile;
    private double positionX;
    private double positionY;

    private Settings() {
        readSettings();
    }

    public static Settings getSettingsInstance() {
        if(settingsInstance == null) {
            settingsInstance = new Settings();
        }
        return settingsInstance;
    }

    private void readSettings() {

        try {
            settingsFile = new File("src/main/resources/settings.txt");
            Scanner scanner = new java.util.Scanner(settingsFile);
            path = scanner.nextLine();
        } catch (FileNotFoundException | NoSuchElementException e) {
        }
    }

    public void saveSettings() {
        try {
            FileWriter fw = new FileWriter(settingsFile);
            fw.write(path);
            fw.close();
        } catch (IOException e) {
            System.out.println("Can't save settings");
        }
    }


}
