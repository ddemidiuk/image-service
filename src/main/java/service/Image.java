package service;

public class Image {

    private final String name;
    private final byte[] data;
    private final boolean rewrite;

    public Image(String name, byte[] data, boolean rewrite) {
        this.name = name;
        this.data = data;
        this.rewrite = rewrite;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isRewrite() {
        return rewrite;
    }
}
