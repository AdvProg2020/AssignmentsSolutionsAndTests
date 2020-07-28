package DirectoriesAndFiles;

public abstract class File {
    protected String name;
    protected Directory parentDirectory;
    protected long size;
    protected boolean isCut = false;

    public File(String name, long size, Directory parentDirectory) {
        this.name = name;
        this.parentDirectory = parentDirectory;
        this.size = size;
    }

    public static boolean checkFormat(String format) {
        if (format.equals("img") || format.equals("mp4") || format.equals("txt"))
            return true;
        return false;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    //Getters
    public boolean isCut() {
        return isCut;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public abstract String getFormat();

    public abstract String getFileStats();

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public abstract File duplicateFile(Directory newDirectory);
}
