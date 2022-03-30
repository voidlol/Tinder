package ru.liga.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.StringJoiner;

@Service
public class PrerevolutionaryTranslator {
    //ѣ
    private static final Set<Character> VOWELS_LOWER = Set.of('а', 'е', 'ё', 'и', 'й', 'о', 'у', 'э', 'ю', 'я');
    private static final Set<Character> PUNCTUATIONS = Set.of('!', '?', ',', '.', '\"', '\'', ')', '<', '>');
    private static final Set<String> FITA_WORDS = Set.of("Агафья", "Анфим", "Афанасий", "Афина", "Варфоломей", "Голиаф",
            "Евфимий", "Марфа", "Матфей", "Мефодий", "Нафанаил", "Парфенон", "Пифагор", "Руфь", "Саваоф", "Тимофей",
            "Эсфирь", "Иудифь", "Фаддей", "Фекла", "Фемида", "Фемистокл", "Феодор", "Фёдор", "Федя", "Феодосий",
            "Федосий", "Феодосия", "Феодот", "Федот", "Феофан", "Феофил", "Ферапонт", "Фома", "Фоминична");
    private static final Set<String> SUBSTRINGS = Set.of("еда", "ем", "есть", "обед", "обедня", "сыроежка", "сыроега",
            "медведь", "снедь", "едкий", "ехать", "езда", "уезд", "еду", "ездить", "поезд");

    public String translate(String text) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        String[] words = text.split("\\s");
        for (String word : words) {
            String changedWord = replaceI(addYer(replaceFinName(word)));
            stringJoiner.add(changedWord);
        }
        return stringJoiner.toString();
    }

    private String replaceFinName(String name) {
        if (FITA_WORDS.contains(name)) {
            return name.replaceAll("[фФ]", "ѳ");
        }
        return name;
    }

    private String addYer(String word) {
        int lastLetterIndex = word.length() - 1;
        if (PUNCTUATIONS.contains(word.charAt(lastLetterIndex))) {
            lastLetterIndex--;
        }
        if (!VOWELS_LOWER.contains(Character.toLowerCase(word.charAt(lastLetterIndex))) && word.charAt(lastLetterIndex) != 'ь') {
            return word.substring(0, lastLetterIndex + 1) + "ъ" + word.substring(lastLetterIndex + 1);
        }
        return word;
    }

    private String replaceI(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (VOWELS_LOWER.contains(Character.toLowerCase(chars[i + 1]))) {
                if (chars[i] == 'и') {
                    chars[i] = 'i';
                } else if (chars[i] == 'И') {
                    chars[i] = 'I';
                }
            }
        }
        return String.valueOf(chars);
    }
}
