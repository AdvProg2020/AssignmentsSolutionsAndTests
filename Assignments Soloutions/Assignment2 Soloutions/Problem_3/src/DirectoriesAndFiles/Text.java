package DirectoriesAndFiles;

public class Text extends File {
    private String text;

    public Text(String name, long size, Directory parentDirectory, String text) {
        super(name, size, parentDirectory);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return name +" txt " + size + "MB";
    }

    @Override
    public File duplicateFile(Directory newDirectory) {
        return new Text(this.name, this.size, newDirectory, this.text);
    }

    @Override
    public String getFormat() {
        return "txt";
    }

    @Override
    public String getFileStats() {
        StringBuilder fileStats = new StringBuilder();
        fileStats.append(this.name).append(" ").append(this.getFormat()).append("\n");
        fileStats.append(this.parentDirectory.getFullAddress()).append("\\").append(this.getName()).append("\n");
        fileStats.append("Size: ").append(this.size).append("MB").append("\n");
        fileStats.append("Text: ").append(this.text);
        return fileStats.toString();
    }
}
