package DirectoriesAndFiles;

public class Video extends File {
    private String quality;
    private String length;

    public Video(String name, long size, Directory parentDirectory, String quality, String length) {
        super(name, size, parentDirectory);
        this.quality = quality;
        this.length = length;
    }

    @Override
    public String toString() {
        return name +" mp4 " + size + "MB";
    }

    @Override
    public File duplicateFile(Directory newDirectory) {
        return new Video(this.name, this.size, newDirectory, this.quality, this.length);
    }

    @Override
    public String getFormat() {
        return "mp4";
    }

    @Override
    public String getFileStats() {
        StringBuilder fileStats = new StringBuilder();
        fileStats.append(this.name).append(" ").append(this.getFormat()).append("\n");
        fileStats.append(this.parentDirectory.getFullAddress()).append("\\").append(this.getName()).append("\n");
        fileStats.append("Size: ").append(this.size).append("MB").append("\n");
        fileStats.append("Quality: ").append(this.quality).append("\n");
        fileStats.append("Video Length: ").append(this.length);
        return fileStats.toString();
    }
}
