package org.example.mp3playerfx.model.library;
import lombok.Getter;
import lombok.Setter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class DirectoryManager {

    private String path;
    private File directory;
    private File[] files;
    private List<DirectoryObserver> directoryObservers;

    public DirectoryManager(String path) {
        this.path = path;
        directory = new File(path);
        files = directory.listFiles();
        directoryObservers = new ArrayList<>();
        scanDirectory();
    }

    public void scanDirectory() {
        Timer timer = new Timer();
        TimerTask scanTask = new TimerTask() {
            @Override
            public void run() {
                directory = new File(path);
                File[] newFiles = directory.listFiles();
                if(filesChanged(files,newFiles)) {
                    System.out.println("Change");
                    notifyDirectoryObservers();
                }
                files = newFiles;

            }
        };
        timer.scheduleAtFixedRate(scanTask,0,10000);
    }

    private boolean filesChanged(File[] files, File[] newFiles) {
        if(files.length != newFiles.length) {
           return true;
        }
        for(int i = 0;i < files.length; i++) {
            if(!files[i].equals(newFiles[i])) {
                return true;
            }
        }
        return false;
    }

    public void addObserver(DirectoryObserver observer) {
        directoryObservers.add(observer);
    }

    private void notifyDirectoryObservers() {
        for(DirectoryObserver o : directoryObservers) {
            o.updateFiles();
        }
    }
}
