package com.kilopo;

import java.util.Arrays;

public class BotUtils {

    public static String checkName(String message) {
        message = message.toLowerCase();
        String[] words = message.split(" ");

        if (Arrays.stream(words).anyMatch(word -> word.contains("лисий") || word.contains("лиcий"))) {
            return "Мар'ян";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисого")
                || word.contains("лиcого") || word.contains("лисoго") || word.contains("лисоrо") || word.contains("лисогo")
                || word.contains("лиcoго") || word.contains("лиcоrо") || word.contains("лиcогo")
                || word.contains("лисorо") || word.contains("лисoгo")
                || word.contains("лисоro")
                || word.contains("лиcorо") || word.contains("лиcoгo") || word.contains("лиcоro")
                || word.contains("лиcoro"))) {
            return "Мар'яна";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисому") || word.contains("лиcому") || word.contains("лисoму") || word.contains("лисоmу") || word.contains("лисомy")
                || word.contains("лиcoму") || word.contains("лиcоmу") || word.contains("лиcомy")
                || word.contains("лисomу") || word.contains("лисoмy")
                || word.contains("лисоmy")
                || word.contains("лиcomу") || word.contains("лиcoмy") || word.contains("лиcоmy")
                || word.contains("лисomy"))) {
            return "Мар'яну";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисим") || word.contains("лиcим") || word.contains("лисиm") || word.contains("лиcиm"))) {
            return "Мар'яном";
        } else
            return null;
    }
}
