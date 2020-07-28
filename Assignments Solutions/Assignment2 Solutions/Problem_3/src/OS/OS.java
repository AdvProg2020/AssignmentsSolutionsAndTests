package OS;

import DirectoriesAndFiles.*;

import java.util.ArrayList;
import java.util.Scanner;

public class OS {
    private Scanner scanner;
    private final String NAME;
    private final String VERSION;
    private long hardSize;
    private int numDrives;
    private Directory currentDirectory;
    private ArrayList<File> filesToBePasted = new ArrayList<>();
    private ArrayList<Folder> foldersToBePasted = new ArrayList<>();

    public OS(Scanner scanner, String NAME, String VERSION) {
        this.scanner = scanner;
        this.NAME = NAME;
        this.VERSION = VERSION;
    }

    public void commandProcessor() {
        String input;
        while (!(input = scanner.nextLine()).trim().matches("\\d+ \\d+"))
            System.out.println("invalid command");
        input = input.trim();
        hardSize = Long.parseLong(input.split(" ")[0]);
        numDrives = Integer.parseInt(input.split(" ")[1]);
        setDrives();
        currentDirectory = Drive.getAllDrives().get(0);
        while (!(input = scanner.nextLine()).trim().equals("end")) {
            input = input.trim();
            if (input.matches("open \\S+")) {
                goToFolder(input.split(" ")[1]);
            }else if (input.matches("go to drive \\S+")) {
                goToDrive(input.split(" ")[3]);
            }else if (input.equals("back")) {
                goBack();
            }else if (input.matches("create folder \\S+")) {
                createFolder(input.split(" ")[2]);
            }else if (input.matches("create file \\S+ \\S+ \\d+")) {
                createFile(input.split(" ")[2], input.split(" ")[3],
                        Long.parseLong(input.split(" ")[4]));
            }else if (input.matches("delete file \\S+")) {
                delete(input.split(" ")[2], 1);
            }else if (input.matches("delete folder \\S+")) {
                delete(input.split(" ")[2], 2);
            }else if (input.matches("rename file \\S+ \\S+")) {
                renameFile(input.split(" ")[2], input.split(" ")[3], 1);
            }else if (input.matches("rename folder \\S+ \\S+")) {
                renameFile(input.split(" ")[2], input.split(" ")[3], 2);
            }else if (input.equals("status")) {
                showStatus();
            }else if (input.equals("print drives status")) {
                showDrivesStatus();
            }else if (input.startsWith("copy file ")) {
                copy(input.substring(10).split(" "), 1);
            }else if (input.startsWith("copy folder ")) {
                copy(input.substring(12).split(" "), 2);
            }else if (input.equals("paste")) {
                paste();
            }else if (input.startsWith("cut file ")) {
                cut(input.substring(9).split(" "), 1);
            }else if (input.startsWith("cut folder ")) {
                cut(input.substring(11).split(" "), 2);
            }else if (input.matches("print file stats \\S+")) {
                showFileStats(input.split(" ")[3]);
            }else if (input.matches("write text \\S+")) {
                writeToTextFile(input.split(" ")[2]);
            }else if (input.equals("print frequent folders")) {
                showFrequentFolders();
            }else if (input.equals("print OS information")) {
                showOsInformation();
            }else
                System.out.println("invalid command");
        }
    }

    public void setDrives() {
        String input;
        long sumSizes = 0;
        int i = 0;
        while (i < numDrives){
            while (!(input = scanner.nextLine()).trim().matches("\\S+ \\d+"))
                System.out.println("invalid command");
            input = input.trim();
            if (Drive.checkDriveName(input.split(" ")[0])) {
                if (sumSizes + Long.parseLong(input.split(" ")[1]) <= hardSize) {
                    Drive.addToDrives(new Drive(input.split(" ")[0].charAt(0), Long.parseLong(input.split(" ")[1])));
                    sumSizes += Long.parseLong(input.split(" ")[1]);
                    i ++;
                }else
                    System.out.println("insufficient hard size");
            } else
                System.out.println("invalid name");
        }
    }

    public void showDrivesStatus() {
        for (Drive drive : Drive.getAllDrives()) {
            System.out.println(drive);
        }
    }

    public void goToDrive(String name) {
        if (name.length() == 1 && Drive.getDriveByName(name.charAt(0)) != null)
            this.currentDirectory = Drive.getDriveByName(name.charAt(0));
        else
            System.out.println("invalid name");
    }

    public void goToFolder(String folderName) {
        if (this.currentDirectory.getFolderByName(folderName) != null) {
            this.currentDirectory.getFolderByName(folderName).
                    setTimesEntered(this.currentDirectory.getFolderByName(folderName).getTimesEntered() + 1);
            Directory.updateFrequentFolders(this.currentDirectory.getFolderByName(folderName));
            this.currentDirectory = this.currentDirectory.getFolderByName(folderName);
        }else
            System.out.println("invalid name");
    }

    public void goBack() {
        if (this.currentDirectory.getParentDirectory() != null) {
            this.currentDirectory = this.currentDirectory.getParentDirectory();
        }
    }

    public void createFolder(String name) {
        if (this.currentDirectory.getFolderByName(name) == null) {
            this.currentDirectory.addToFolders(new Folder(name, this.currentDirectory.getDrive(), this.currentDirectory));
            System.out.println("folder created");
        }else
            System.out.println("folder exists with this name");
    }

    public void createFile(String name, String format, long size) {
        if (this.currentDirectory.getFileByName(name) == null) {
            if (File.checkFormat(format)) {
                if (this.currentDirectory.isCapacityEnough(size)) {
                    switch (format) {
                        case "img":
                            this.currentDirectory.addToFiles(createImage(name, size));
                            break;
                        case "mp4":
                            this.currentDirectory.addToFiles(createVideo(name, size));
                            break;
                        case "txt":
                            this.currentDirectory.addToFiles(createText(name, size));
                            break;
                    }
                    System.out.println("file created");
                }else
                    System.out.println("insufficient drive size");
            }else
                System.out.println("invalid format");
        }else
            System.out.println("file exists with this name");
    }

    public Text createText (String name, long size) {
        System.out.println("Text:");
        String text = scanner.nextLine();
        return new Text(name, size, this.currentDirectory, text);
    }

    public Image createImage (String name, long size) {
        System.out.println("Resolution:");
        String resolution = scanner.nextLine();
        System.out.println("Extension:");
        String extension = scanner.nextLine();
        return new Image(name, size, this.currentDirectory, extension, resolution);
    }

    public Video createVideo (String name, long size) {
        System.out.println("Quality:");
        String quality = scanner.nextLine();
        System.out.println("Video Length:");
        String length = scanner.nextLine();
        return new Video(name, size, this.currentDirectory, quality, length);
    }

    public void delete(String fileName, int type) {
        if (type == 1) {
            if (this.currentDirectory.getFileByName(fileName) != null) {
                this.currentDirectory.removeFromFiles(this.currentDirectory.getFileByName(fileName));
                System.out.println("file deleted");
            } else
                System.out.println("invalid name");
        }else if (type == 2) {
            if (this.currentDirectory.getFolderByName(fileName) != null) {
                this.currentDirectory.removeFromFolders(this.currentDirectory.getFolderByName(fileName));
                System.out.println("folder deleted");
            }else
                System.out.println("invalid name");
        }
    }

    public void renameFile(String fileName, String newName, int type) {
        if (type == 1) {
            if (this.currentDirectory.getFileByName(fileName) == null)
                System.out.println("invalid name");
            else if (this.currentDirectory.getFileByName(newName) != null)
                System.out.println("file exists with this name");
            else {
                this.currentDirectory.getFileByName(fileName).setName(newName);
                System.out.println("file renamed");
            }
        }else if (type == 2) {
            if (this.currentDirectory.getFolderByName(fileName) == null)
                System.out.println("invalid name");
            else if (this.currentDirectory.getFolderByName(newName) != null)
                System.out.println("folder exists with this name");
            else {
                this.currentDirectory.getFolderByName(fileName).setName(newName);
                System.out.println("folder renamed");
            }
        }
    }

    public void showStatus() {
        this.currentDirectory.sortAllFiles();
        this.currentDirectory.sortAllFolders();
        System.out.println(this.currentDirectory.getFullAddress());
        System.out.println("Folders:");
        for (Folder folder : this.currentDirectory.getAllFolders()) {
            System.out.println(folder);
        }
        System.out.println("Files:");
        for (File file : this.currentDirectory.getAllFiles()) {
            if (file instanceof Image)
                System.out.println(file);
        }
        for (File file : this.currentDirectory.getAllFiles()) {
            if (file instanceof Text)
                System.out.println(file);
        }
        for (File file : this.currentDirectory.getAllFiles()) {
            if (file instanceof Video)
                System.out.println(file);
        }
    }

    public void copy(String[] fileNames, int type) {
        if (type == 1) {
            for (String fileName : fileNames) {
                if (this.currentDirectory.getFileByName(fileName) == null) {
                    System.out.println("invalid name");
                    return;
                }
            }
            foldersToBePasted = new ArrayList<>();
            filesToBePasted = new ArrayList<>();
            for (String fileName : fileNames)
                filesToBePasted.add(this.currentDirectory.getFileByName(fileName).duplicateFile(null));
            System.out.println("files copied");
        }else if (type == 2) {
            for (String fileName : fileNames) {
                if (this.currentDirectory.getFolderByName(fileName) == null) {
                    System.out.println("invalid name");
                    foldersToBePasted = new ArrayList<>();
                    return;
                }
            }
            filesToBePasted = new ArrayList<>();
            foldersToBePasted = new ArrayList<>();
            for (String fileName : fileNames)
                foldersToBePasted.add(this.currentDirectory.getFolderByName(fileName).duplicateFolder(null));
            System.out.println("folders copied");
        }
    }

    public void cut(String[] fileNames, int type) {
        filesToBePasted = new ArrayList<>();
        foldersToBePasted = new ArrayList<>();
        if (type == 1) {
            for (String fileName : fileNames) {
                if (this.currentDirectory.getFileByName(fileName) == null) {
                    System.out.println("invalid name");
                    return;
                }
            }
            foldersToBePasted = new ArrayList<>();
            filesToBePasted = new ArrayList<>();
            for (String fileName : fileNames) {
                filesToBePasted.add(this.currentDirectory.getFileByName(fileName));
                this.currentDirectory.getFileByName(fileName).setCut(true);
            }
            System.out.println("files cut completed");
        }else if (type == 2) {
            for (String fileName : fileNames) {
                if (this.currentDirectory.getFolderByName(fileName) == null) {
                    System.out.println("invalid name");
                    return;
                }
            }
            filesToBePasted = new ArrayList<>();
            foldersToBePasted = new ArrayList<>();
            for (String fileName : fileNames) {
                foldersToBePasted.add(this.currentDirectory.getFolderByName(fileName));
                this.currentDirectory.getFolderByName(fileName).setCut(true);
            }
            System.out.println("folders cut completed");
        }
    }

    public void paste() {
        if (!filesToBePasted.isEmpty()) {
            for (File file : filesToBePasted) {
                if (this.currentDirectory.getFileByName(file.getName()) != null) {
                    System.out.println("file exists with this name");
                    return;
                }
            }
            long sumSizes = 0;
            for (File file : filesToBePasted) {
                sumSizes += file.getSize();
            }
            if (!this.currentDirectory.isCapacityEnough(sumSizes)) {
                System.out.println("insufficient drive size");
                return;
            }
            for (File file : filesToBePasted) {
                if (file.isCut()) {
                    file.getParentDirectory().removeFromFiles(file);
                    file.setCut(false);
                }
                this.currentDirectory.addToFiles(file);
            }
            System.out.println("paste completed");
            filesToBePasted = new ArrayList<>();
        }
        if (!foldersToBePasted.isEmpty()) {
            for (Folder folder : foldersToBePasted) {
                if (this.currentDirectory.getFolderByName(folder.getFullName()) != null) {
                    System.out.println("folder exists with this name");
                    return;
                }
            }
            long sumSizes = 0;
            for (Folder folder : foldersToBePasted) {
                sumSizes += folder.getSize();
            }
            if (!this.currentDirectory.isCapacityEnough(sumSizes)) {
                System.out.println("insufficient drive size");
                return;
            }
            for (Folder folder : foldersToBePasted) {
                folder.setAllElementsToZero();
                if (folder.isCut()) {
                    folder.getParentDirectory().removeFromFolders(folder);
                    folder.setCut(false);
                }
                this.currentDirectory.addToFolders(folder);
            }
            System.out.println("paste completed");
            foldersToBePasted = new ArrayList<>();
        }
    }

    public void showFileStats(String fileName) {
        if (this.currentDirectory.getFileByName(fileName) == null) {
            System.out.println("invalid name");
            return;
        }
        System.out.println(this.currentDirectory.getFileByName(fileName).getFileStats());
    }

    public void writeToTextFile(String fileName) {
        if (this.currentDirectory.getFileByName(fileName) == null)
            System.out.println("invalid name");
        else if (!(this.currentDirectory.getFileByName(fileName) instanceof Text))
            System.out.println("this file is not a text file");
        else {
            String newText = scanner.nextLine();
            ((Text) this.currentDirectory.getFileByName(fileName)).setText(newText);
        }
    }

    public void showFrequentFolders() {
        ArrayList<Folder> frequentFolders = Directory.getFrequentFolders();
        int max = 5;
        for (int i = 0 ; i < frequentFolders.size() && i < max ; i++) {
            if (frequentFolders.get(i).getTimesEntered() != 0)
                System.out.println(frequentFolders.get(i).getFullAddress() + " " + frequentFolders.get(i).getTimesEntered());
            else
                max ++;
        }
    }

    public void showOsInformation() {
        System.out.println("OS is "+this.NAME+" "+this.VERSION);
    }
}
