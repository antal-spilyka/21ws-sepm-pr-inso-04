package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class FileDto {
    private String name;
    private String uri;
    private String type;
    private long size;

    public FileDto(String name, String uri, String type, long size) {
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileDto{" +
            "name='" + name + '\'' +
            ", uri='" + uri + '\'' +
            ", type='" + type + '\'' +
            ", size=" + size +
            '}';
    }
}