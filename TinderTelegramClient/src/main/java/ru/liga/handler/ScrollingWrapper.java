package ru.liga.handler;

import ru.liga.domain.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScrollingWrapper {

    private final List<Profile> listToScroll;
    private int currentIndex = 0;

    public ScrollingWrapper(List<Profile> listToScroll) {
        this.listToScroll = listToScroll;
    }

    public ScrollingWrapper(Set<Profile> profileSet) {
        listToScroll = new ArrayList<>(profileSet);
    }

    public Profile getCurrentProfile() {
        return listToScroll.get(currentIndex);
    }

    public Profile getNextProfile() {
        currentIndex++;
        if (currentIndex == listToScroll.size()) {
            currentIndex = 0;
        }
        return listToScroll.get(currentIndex);
    }
}
