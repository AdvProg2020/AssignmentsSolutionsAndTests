package DirectoriesAndFiles;

public class Folder extends Directory {
    private String name;
    private long size;
    private int timesEntered;
    private boolean isCut = false;

    public Folder(String name, Drive drive, Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
        this.drive = drive;
        this.name = name;
        this.size = 0;
    }

    //Getters
    public boolean isCut() {
        return isCut;
    }

    public long getSize() {
        return size;
    }

    public int getTimesEntered() {
        return timesEntered;
    }

    //Setters
    public void setCut(boolean cut) {
        isCut = cut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTimesEntered(int timesEntered) {
        this.timesEntered = timesEntered;
    }

    public void setAllElementsToZero() {
        if (this.allFolders.isEmpty()) {
            Directory.removeFromFrequentFolders(this);
            this.setTimesEntered(0);
            return;
        }
        for (Folder folder : allFolders) {
            Directory.removeFromFrequentFolders(folder);
            folder.setTimesEntered(0);
            folder.setAllElementsToZero();
        }
        Directory.removeFromFrequentFolders(this);
        this.setTimesEntered(0);
    }

    @Override
    public String getFullName() {
        return this.name;
    }

    @Override
    public String toString() {
        return  name + " " + size + "MB";
    }

    public Folder duplicateFolder(Directory newDirectory) {
        Folder duplicatedFolder = new Folder(this.name, null, null);
        duplicatedFolder.setTimesEntered(0);
        for (File file : allFiles) {
            duplicatedFolder.addToFiles(file.duplicateFile(duplicatedFolder));
        }
        for (Folder folder : allFolders) {
            duplicatedFolder.addToFolders(folder.duplicateFolder(duplicatedFolder));
        }
        duplicatedFolder.setParentDirectory(newDirectory);
        return duplicatedFolder;
    }

    public int compareTo(Folder anotherFolder) {
        if (this.getTimesEntered() > anotherFolder.getTimesEntered())
            return 1;
        else if (this.getTimesEntered() == anotherFolder.getTimesEntered()) {
            if (this.getFullAddress().compareTo(anotherFolder.getFullAddress()) < 0)
                return 1;
        }
        return -1;
    }
}
