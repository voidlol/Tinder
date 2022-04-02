package ru.liga.service;

import org.springframework.stereotype.Service;
import ru.liga.domain.Profile;

import java.util.Set;
import java.util.StringJoiner;

@Service
public class PrerevolutionaryTranslator {

    private static final Set<Character> VOWELS_LOWER = Set.of('а', 'е', 'ё', 'и', 'й', 'о', 'у', 'э', 'ю', 'я');
    private static final String ALPHABET = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфЦцЧчШшЩщЪъЫыЬьЭэЮюЯя";

    private static final Set<String> FITA_WORDS = Set.of("Агафья", "Анфим", "Афанасий", "Афина", "Варфоломей", "Голиаф",
            "Евфимий", "Марфа", "Матфей", "Мефодий", "Нафанаил", "Парфенон", "Пифагор", "Руфь", "Саваоф", "Тимофей",
            "Эсфирь", "Иудифь", "Фаддей", "Фекла", "Фемида", "Фемистокл", "Феодор", "Фёдор", "Федя", "Феодосий",
            "Федосий", "Феодосия", "Феодот", "Федот", "Феофан", "Феофил", "Ферапонт", "Фома", "Фоминична");

    private static final Set<String> SUBSTRINGS = Set.of("ѣд", "ѣм", "ѣсть", "сыроѣжка", "сыроѣга", "ѣхать", "ѣзд", "бѣг",
            "бѣж", "бѣд", "бѣл", "бѣс", "бѣшеный", "обѣт", "обѣщать", "вѣять", "вѣер", "вѣт", "вѣтвь", "вѣха", "вѣдать",
            "вѣди", "вѣсть", "вѣдѣние", "вѣж", "вѣк", "вѣч", "вѣн", "вѣр", "вѣс", "вѣшать", "звѣзда", "звѣрь", "вѣщ",
            "дѣвать", "одѣяло", "одѣяние", "дѣл", "дѣйствие", "недѣля", "надѣяться", "свидѣтель", "дѣва", "дѣвочка", "дѣд",
            "дѣт", "зѣв", "ротозѣй", "зѣло", "зѣница", "зѣнки", "лѣв", "лѣзть", "лѣстница", "облѣзлый", "лѣкарь", "лѣчить",
            "лѣкарство", "лѣнь", "лѣнивец", "лѣнивый", "лѣнтяй", "лѣпота", "великолѣпный", "лѣпить", "нелѣпый", "слѣпок",
            "лѣс", "лѣсник", "лѣсничий", "лѣсопилка", "лѣш", "лѣто", "лѣт", "лѣха", "блѣдный", "лѣз", "калѣка", "калѣчить",
            "лѣн", "лелѣять", "Плѣханов", "лѣд", "слѣпой", "телѣга", "телѣжный", "хлѣб", "хлѣв", "мѣд", "мѣн", "мѣр",
            "мѣсяц", "мѣсить", "мѣшать", "помѣха", "мѣсто", "мѣщанин", "помѣщик", "намѣстник", "мѣтить", "замѣчать",
            "примѣчание", "смѣтить", "смѣта", "мѣх", "мѣшок", "змѣй", "змѣя", "смѣть", "смѣлый", "смѣяться", "смѣх",
            "нѣга", "нѣжный", "нѣжить", "нѣдра", "внѣдрить", "нѣмой", "нѣмец", "нѣт", "отнѣкаться", "гнѣв", "гнѣдой",
            "гнѣздо", "загнѣтка", "снѣг", "снѣжный", "мнѣние", "сомнѣваться", "пѣть", "пѣсня", "пѣтух", "пѣгий", "пѣна",
            "пѣнязь", "пѣстовать", "пѣстун", "пѣхота", "пѣший", "опѣшить", "спѣх", "спѣшить", "успѣх", "рѣять", "рѣка",
            "рѣчь", "нарѣчие", "рѣдкий", "рѣдька", "рѣзать", "рѣзвый", "рѣпа", "рѣпица", "рѣсница", "обрѣтать", "обрѣсти",
            "срѣтение", "встрѣчать", "прорѣха", "рѣшето", "рѣшетка", "рѣшать", "рѣшитьгрех", "грѣшный", "зрѣть", "созрѣть",
            "зрѣлый", "зрѣние", "крѣпкий", "крѣпиться", "орѣх", "прѣть", "прѣлый", "прѣние", "прѣсный", "свирѣпый",
            "свирѣль", "стрѣла", "стрѣлять", "стрѣха", "хрѣн", "сусѣк", "сѣять", "сѣмя", "сѣвер", "сѣдло", "сѣсть",
            "бесѣда", "сосѣд", "сѣдой", "сѣдеть", "сѣку", "сѣчь", "сѣча", "сѣчение", "просѣка", "насѣкомое", "сѣнь",
            "осѣнять", "сѣни", "сѣно", "сѣрый", "сѣра", "посѣтить", "посѣщать", "сѣтовать", "сѣть", "сѣтка", "стѣн",
            "тѣло", "растѣлешиться", "тѣльняшка", "тѣнь", "оттѣнок", "тѣнек", "тѣсто", "тѣсный", "стѣснять", "тѣснить",
            "затѣя", "утѣха", "потѣха", "тѣшить", "утѣшение", "хѣр", "цѣвка", "цѣвье", "цѣвница", "цѣдить", "цѣлый",
            "исцѣлять", "цѣловать", "поцѣлуй", "цѣль", "цѣлиться", "цѣна", "цѣпь", "цѣплять", "цѣп");


    public Profile translateProfile(Profile profile) {
        Profile result = new Profile();
        result.setId(profile.getId());
        result.setName(translate(profile.getName()));
        result.setDescription(translate(profile.getDescription()));
        result.setSexType(profile.getSexType());
        return result;
    }

    private String translate(String text) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        String[] words = text.split("\\s");
        for (String word : words) {
            String changedWord = replaceI(addYer(replaceFinName(replaceE(word))));
            stringJoiner.add(changedWord);
        }
        return stringJoiner.toString();
    }

    private String replaceE(String word) {
        for (String sub : SUBSTRINGS) {
            int startIndex = word.toLowerCase().indexOf(sub.replace('ѣ', 'е'));
            if (startIndex != -1) {
                int replaceIndex = sub.indexOf('ѣ');
                StringBuilder sb = new StringBuilder(word);
                sb.setCharAt(startIndex + replaceIndex, 'ѣ');
                return sb.toString();
            }
        }
        return word;
    }

    private String replaceFinName(String name) {
        if (FITA_WORDS.contains(name)) {
            return name.replaceAll("[фФ]", "ѳ");
        }
        return name;
    }

    private String addYer(String word) {
        int lastLetterIndex = word.length() - 1;
        while (ALPHABET.indexOf(word.charAt(lastLetterIndex)) == -1 && lastLetterIndex != 0) {
            lastLetterIndex--;
        }
        if (!VOWELS_LOWER.contains(Character.toLowerCase(word.charAt(lastLetterIndex)))
                && word.charAt(lastLetterIndex) != 'ь'
                && ALPHABET.indexOf(word.charAt(lastLetterIndex)) != -1) {
            return word.substring(0, lastLetterIndex + 1) + 'ъ' + word.substring(lastLetterIndex + 1);
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
