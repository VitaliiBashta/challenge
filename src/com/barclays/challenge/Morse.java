package com.barclays.challenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Morse {
    private static final Map<String, String> DICTIONARY = new HashMap<>();
    private static final List<String> INPUTS = List.of(
            "EGRAGF",
            "FENDSVTSLHW,EDATS.EULAY",
            "QEWOISE,EIVCAEFNOVTBELYTGD.",
            "?EJHUT,TSMYGW?EJHOT",
            "DSU,XFNCJEVE,OE_UJDXNO_YHU?VIDWDHPDJIKXZT?E",
            "CONTX_E.EIZODHZTEOLJXNRZWDEIIIUDDKFXGOMEL,SNVTPUTJWBAEZWW?BEPGBPES_XLDUTNSUCEWHENRFNFRTXKNAJ_I_TGR_YMN.SSBAFDTQTTW_PLEEBVBSUEBEOHVTISQARIESVKDBSGRH,ERM_KFST.ENAWVOG_BHM_HCIAGIIT?ZWIZTSMDHJRONFLEEK.ESQT_TVLTGIHZE.ITU_PASHZEVELIIZHAOTTR_POMEK_HTVTYMDXKNMRJMXBH.H,OG.VTIWCSGE?VKDBE_T,ESAHOVTUIEL_FRSA?XCBEWDGRCL_S_RWRSCX?XCWZEXKIWJEH?RYRDQDPSMNZ_K?FWAI?MNMLIMBSIS,GRBVYUA?FWMXEHWNITTEGCWSH,OMUITMFWEAGGAR."
    );

    static {
        DICTIONARY.put("A", ".-");
        DICTIONARY.put("B", "-...");
        DICTIONARY.put("C", "-.-.");
        DICTIONARY.put("D", "-..");
        DICTIONARY.put("E", ".");
        DICTIONARY.put("F", "..-.");
        DICTIONARY.put("G", "--.");
        DICTIONARY.put("H", "....");
        DICTIONARY.put("I", "..");
        DICTIONARY.put("J", ".---");
        DICTIONARY.put("K", "-.-");
        DICTIONARY.put("L", ".-..");
        DICTIONARY.put("M", "--");
        DICTIONARY.put("N", "-.");
        DICTIONARY.put("O", "---");
        DICTIONARY.put("P", ".--.");
        DICTIONARY.put("Q", "--.-");
        DICTIONARY.put("R", ".-.");
        DICTIONARY.put("S", "...");
        DICTIONARY.put("T", "-");
        DICTIONARY.put("U", "..-");
        DICTIONARY.put("V", "...-");
        DICTIONARY.put("W", ".--");
        DICTIONARY.put("X", "-..-");
        DICTIONARY.put("Y", "-.--");
        DICTIONARY.put("Z", "--..");
        DICTIONARY.put("_", "..--");
        DICTIONARY.put(",", "---.");
        DICTIONARY.put(".", ".-.-");
        DICTIONARY.put("?", "----");

    }

    public static void main(String[] args) {


        final Map<String, String> results = INPUTS.stream().collect(Collectors.toMap(k -> k, v -> process(v)));
        results.entrySet().stream().forEach(e -> System.out.println(e));
    }

    private static String process(String input) {

        final String encoded = encode(input);
        final String reversedEncoded = reverseNumbers(encoded);
        return decode(reversedEncoded);
    }

    private static String morseEncode(String input) {
        return input.chars()
                .mapToObj(Character::toString)
                .map(DICTIONARY::get)
                .collect(Collectors.joining(" "));
    }

    private static String encode(String input) {
        final StringBuilder sb = new StringBuilder();
        final StringBuilder digits = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            final char charAt = input.charAt(i);
            final String letter = Character.toString(charAt);
            final String morseLetter = DICTIONARY.get(letter);
            sb.append(morseLetter);
            digits.append(morseLetter.length());
        }
        return sb.toString() + digits;
    }

    private static String decode(String encoded) {
        final List<String> splited = splitDigits(encoded);
        String morse = splited.get(0);
        String digits = splited.get(1);
        int pos = 0;
        final StringBuilder sb = new StringBuilder();
        for (int letterPos = 0; letterPos < digits.length(); letterPos++) {
            String sLength = Character.toString(digits.charAt(letterPos));
            final int letterSize = Integer.parseInt(sLength);
            final String morseLetter = morse.substring(pos, pos + letterSize);
            sb.append(findLetter(morseLetter));
            pos += letterSize;
        }

        return sb.toString();
    }

    private static String findLetter(String morse) {
        return DICTIONARY
                .entrySet()
                .stream()
                .filter(entry -> morse.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No letter for :" + morse));
    }

    private static String reverseNumbers(String encoded) {

        int digitPos = getDigitPos(encoded);
        String reversed = encoded.substring(0, digitPos);
        String digits = encoded.substring(digitPos);
        String reversedDigits = new StringBuilder(digits).reverse().toString();
        return reversed + reversedDigits;
    }

    private static List<String> splitDigits(String encoded) {
        int digitPos = getDigitPos(encoded);
        String morse = encoded.substring(0, digitPos);
        String digits = encoded.substring(digitPos);
        return List.of(morse, digits);
    }

    private static int getDigitPos(String encoded) {
        for (int i = 0; i < encoded.length(); i++) {
            if (Character.isDigit(encoded.charAt(i))) {
                return i;
            }
        }
        return 0;
    }
}
