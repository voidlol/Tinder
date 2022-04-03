package ru.liga.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ScrollingWrapper {

    private final List<Profile> listToScroll;
    private int currentIndex = 0;

    public ScrollingWrapper(List<Profile> listToScroll) {
        this.listToScroll = new LinkedList<>(listToScroll);
    }

    public Profile getCurrentProfile() {
        return listToScroll.get(currentIndex);
    }

    public Profile getNextProfile() {
        return listToScroll.get(++currentIndex);
    }

    public boolean isLast() {
        return currentIndex == listToScroll.size() - 1;
    }

    public int getSize() {
        return listToScroll.size();
    }

    public boolean isEmpty() {
        return listToScroll.isEmpty();
    }

    public boolean isFirst() {
        return currentIndex == 0;
    }

    public Profile getPrevProfile() {
        return listToScroll.get(--currentIndex);
    }

    public Profile getLastProfile() {
        currentIndex = listToScroll.size() - 1;
        return listToScroll.get(currentIndex);
    }
}
