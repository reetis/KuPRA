package eu.komanda30.kupra.services;

import eu.komanda30.kupra.entity.KupraUser;

import java.util.List;

public interface UserFinder {
    void indexUsers();
    List<KupraUser> searchForUsers(String searchText, int maxResults);
    List<KupraUser> searchForUsers(String searchText);
}
