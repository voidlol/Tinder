package ru.liga.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrerevolutionaryTranslatorTest {

    private static final PrerevolutionaryTranslator p = new PrerevolutionaryTranslator();

    @Test
    void test() {
        String input = "Всем привет! Я синий. Меня зовут Тимофей";
        String translate = p.translate(input);
        System.out.println(translate);
    }

}