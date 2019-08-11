package service;

import java.io.IOException;
import java.util.Set;

public interface StoreFileService {

    void save(String fileName, byte[] data, boolean rewrite) throws IOException;

    void save(String fileName, String url, boolean rewrite) throws IOException;

    byte[] get(String fileName) throws IOException;

    Set<String> list() throws IOException;
}
