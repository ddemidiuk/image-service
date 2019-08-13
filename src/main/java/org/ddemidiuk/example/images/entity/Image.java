package org.ddemidiuk.example.images.entity;

public class Image {

    private final String name;
    private final byte[] data;
    private final long size;

    public Image(String name, byte[] data) {
        this.name = name;
        this.data = data;
        this.size = data.length;
    }

    public Image(String name, long size) {
        this.name = name;
        this.data = null;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public long getSize() {
        return size;
    }
}
