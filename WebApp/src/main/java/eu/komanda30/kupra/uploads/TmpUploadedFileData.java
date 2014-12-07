package eu.komanda30.kupra.uploads;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class TmpUploadedFileData implements Serializable {
    private final Map<String, Map<String, File>> fileMap = new HashMap<>();

    public File get(String groupId, String fileId) {
        final Map<String, File> groupMap = fileMap.get(groupId);
        if (groupMap == null) {
            return null;
        }

        final File file = groupMap.get(fileId);
        if (file == null) {
            return null;
        }

        return file;
    }

    public void put(String groupId, String fileId, File file) {
        Map<String, File> groupFileMap = fileMap.get(groupId);
        if (groupFileMap == null) {
            groupFileMap = new HashMap<>();
            fileMap.put(groupId, groupFileMap);
        }

        groupFileMap.put(fileId, file);
    }

    public Collection<File> allFiles() {
        return fileMap.values().parallelStream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Collection<String> allFileIds(String groupId) {
        final Map<String, File> map = fileMap.get(groupId);
        return map != null ? map.keySet() : null;
    }

    public void remove(String groupId, String fileId) {
        final Map<String, File> map = fileMap.get(groupId);
        if (map != null) {
            map.remove(fileId);
            if (map.isEmpty()) {
                fileMap.remove(groupId);
            }
        }
    }
}
