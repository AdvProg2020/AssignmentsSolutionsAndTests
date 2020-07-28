package DirectoriesAndFiles;

import java.util.ArrayList;

public abstract class Directory {
    private static ArrayList<Folder> frequentFolders = new ArrayList<>();
    protected ArrayList<File> allFiles = new ArrayList<>();
    protected ArrayList<Folder> allFolders = new ArrayList<>();
    protected Directory parentDirectory;
    protected Drive drive;

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    //Getters
    public static ArrayList<Folder> getFrequentFolders() {
        return frequentFolders;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public ArrayList<File> getAllFiles() {
        return allFiles;
    }

    public ArrayList<Folder> getAllFolders() {
        return allFolders;
    }

    public abstract String getFullName();

    public String getFullAddress() {
        if (this.parentDirectory != null)
            return this.parentDirectory.getFullAddress() + "\\"+this.getFullName();
        else
            return this.getFullName();
    }

    public Folder getFolderByName (String name) {
        for (Folder folder : allFolders) {
            if (folder.getFullName().equalsIgnoreCase(name))
                return folder;
        }
        return null;
    }

    public File getFileByName (String name) {
        for (File file : allFiles) {
            if (file.getName().equalsIgnoreCase(name))
                return file;
        }
        return null;
    }

    public void addToFolders(Folder folder) {
        allFolders.add(folder);
        folder.setParentDirectory(this);
        folder.setDrive(this.drive);
        if (this.getDrive() != null)
            changeDriveCapacity(folder.getSize(), false);
        Directory parents = folder.getParentDirectory();
        while (parents instanceof Folder) {
            ((Folder) parents).setSize(((Folder) parents).getSize() + folder.getSize());
            parents = parents.getParentDirectory();
        }
    }

    public void addToFiles(File file) {
        this.allFiles.add(file);
        file.setParentDirectory(this);
        if (this.getDrive() != null)
            changeDriveCapacity(file.getSize(), false);
        Directory parents = file.getParentDirectory();
        while (parents instanceof Folder) {
            ((Folder) parents).setSize(((Folder) parents).getSize() + file.getSize());
            parents = parents.getParentDirectory();
        }
    }

    public void removeFromFolders(Folder folder) {
        this.allFolders.remove(folder);
        changeDriveCapacity(folder.getSize(), true);
        Directory parents = folder.getParentDirectory();
        while (parents instanceof Folder) {
            ((Folder) parents).setSize(((Folder) parents).getSize() - folder.getSize());
            parents = parents.getParentDirectory();
        }
    }

    public void removeFromFiles(File file) {
        this.allFiles.remove(file);
        changeDriveCapacity(file.getSize(), true);
        Directory parents = file.getParentDirectory();
        while (parents instanceof Folder) {
            ((Folder) parents).setSize(((Folder) parents).getSize() - file.getSize());
            parents = parents.getParentDirectory();
        }
    }

    public boolean isCapacityEnough(long size) {
        if (this.getDrive().getEmptyCapacity() >= size)
            return true;
        return false;
    }

    public void changeDriveCapacity(long size, boolean increase) {
        if (increase)
            this.getDrive().setEmptyCapacity(this.getDrive().getEmptyCapacity() + size);
        else
            this.getDrive().setEmptyCapacity(this.getDrive().getEmptyCapacity() - size);
    }

    public Drive getDrive() {
        return this.drive;
    }

    public void sortAllFiles() {
        for (int i = 0 ; i < allFiles.size() ; i++) {
            for (int j = i + 1 ; j < allFiles.size() ; j++) {
                if (allFiles.get(i).getName().compareTo(allFiles.get(j).getName()) > 0) {
                    File tmp1 = allFiles.get(i);
                    File tmp2 = allFiles.get(j);
                    allFiles.remove(i);
                    allFiles.add(i, tmp2);
                    allFiles.remove(j);
                    allFiles.add(j, tmp1);
                }
            }
        }
    }

    public void sortAllFolders() {
        for (int i = 0 ; i < allFolders.size() ; i++) {
            for (int j = i + 1 ; j < allFolders.size() ; j++) {
                if (allFolders.get(i).getFullName().compareTo(allFolders.get(j).getFullName()) > 0) {
                    Folder tmp1 = allFolders.get(i);
                    Folder tmp2 = allFolders.get(j);
                    allFolders.remove(i);
                    allFolders.add(i, tmp2);
                    allFolders.remove(j);
                    allFolders.add(j, tmp1);
                }
            }
        }
    }

    public static void updateFrequentFolders(Folder folder) {
        if (!frequentFolders.contains(folder))
            frequentFolders.add(folder);
        sortFrequentFolders();

    }

    public static void sortFrequentFolders() {
        for (int i = 0 ; i < frequentFolders.size() ; i ++) {
            for (int j = i + 1 ; j < frequentFolders.size() ; j++) {
                if (frequentFolders.get(i).compareTo(frequentFolders.get(j)) < 0) {
                    Folder tmp1 = frequentFolders.get(i);
                    Folder tmp2 = frequentFolders.get(j);
                    frequentFolders.remove(i);
                    frequentFolders.add(i, tmp2);
                    frequentFolders.remove(j);
                    frequentFolders.add(j, tmp1);
                }
            }
        }
    }

    public static void removeFromFrequentFolders(Folder folder) {
        if (frequentFolders.contains(folder)) {
            frequentFolders.remove(folder);
        }
    }
}
