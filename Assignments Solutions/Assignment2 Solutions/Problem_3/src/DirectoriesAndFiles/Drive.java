package DirectoriesAndFiles;

import java.util.ArrayList;

public class Drive extends Directory {
    private static ArrayList<Drive> allDrives = new ArrayList<>();
    private final char NAME;
    private final long CAPACITY;
    private long emptyCapacity;

    public Drive(char NAME, long CAPACITY) {
        this.NAME = NAME;
        this.CAPACITY = CAPACITY;
        emptyCapacity = CAPACITY;
        this.parentDirectory = null;
        this.drive = this;
    }
    //Getters
    public char getNAME() {
        return NAME;
    }

    public static ArrayList<Drive> getAllDrives() {
        return allDrives;
    }

    public long getEmptyCapacity() {
        return emptyCapacity;
    }

    //Setters
    public void setEmptyCapacity(long emptyCapacity) {
        this.emptyCapacity = emptyCapacity;
    }

    public static boolean checkDriveName(String name) {
        if (name.matches("[A-Z]")) {
            char nameAsChar = name.charAt(0);
            for (int i = 0 ; i < allDrives.size() ; i ++) {
                if (allDrives.get(i).getNAME() == nameAsChar)
                    return false;
            }
            return true;
        }
        return false;
    }

    public static void addToDrives(Drive drive) {
        allDrives.add(drive);
    }

    public static Drive getDriveByName(char driveName) {
        for (Drive drive : allDrives) {
            if (drive.getNAME() == driveName)
                return drive;
        }
        return null;
    }

    @Override
    public String toString() {
        return "" + NAME +
                " " + CAPACITY +
                "MB " + (CAPACITY - emptyCapacity) +
                "MB";
    }

    @Override
    public String getFullName() {
        return this.getNAME()+":";
    }
}
