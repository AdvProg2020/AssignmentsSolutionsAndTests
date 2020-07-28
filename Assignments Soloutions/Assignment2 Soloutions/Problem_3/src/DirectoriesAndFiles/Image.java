package DirectoriesAndFiles;

public class Image extends File {
    private String extension;
    private String resolution;

    public Image(String name, long size, Directory parentDirectory, String extension, String resolution) {
        super(name, size, parentDirectory);
        this.extension = extension;
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return name +" img " + size + "MB";
    }

    @Override
    public File duplicateFile(Directory newDirectory) {
        return new Image(this.name, this.size, newDirectory, this.extension, this.resolution);
    }

    @Override
    public String getFormat() {
        return "img";
    }

    @Override
    public String getFileStats() {
        StringBuilder fileStats = new StringBuilder();
        fileStats.append(this.name).append(" ").append(this.getFormat()).append("\n");
        fileStats.append(this.parentDirectory.getFullAddress()).append("\\").append(this.getName()).append("\n");
        fileStats.append("Size: ").append(this.size).append("MB").append("\n");
        fileStats.append("Resolution: ").append(this.resolution).append("\n");
        fileStats.append("Extension: ").append(this.extension);
        return fileStats.toString();
    }
}
