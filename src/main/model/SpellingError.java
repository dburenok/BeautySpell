package model;

import java.util.ArrayList;

// SpellingError object which holds the typo and where it is positioned in the text
public class SpellingError {

    private final int typoPositionStart;
    private final int typoPositionEnd;
    private final String typo;
    private final String suggestedWord;

    // REQUIRES: all parameters must be given at object creation
    // MODIFIES: this
    // EFFECTS: creats a Spelling Error object with string index of typo start, end, the typo itself and
    // a suggested word
    public SpellingError(int typoPositionStart, int typoPositionEnd, String typo, String suggestedWord) {
        this.typoPositionStart = typoPositionStart;
        this.typoPositionEnd = typoPositionEnd;
        this.typo = typo;
        this.suggestedWord = suggestedWord;
    }

    // EFFECTS: print the spelling error to console, along with the text around it
    public void showError(String text) {
        final int previewRange = 10;

        if (typoPositionStart - previewRange > 0 & typoPositionEnd + previewRange < text.length()) {
            System.out.println("\"..." + text.substring(typoPositionStart - previewRange, typoPositionStart)
                    + " { " + text.substring(typoPositionStart, typoPositionEnd) + " } "
                    + text.substring(typoPositionEnd, typoPositionEnd + previewRange) + "...\"");
        } else {
            System.out.println("\"...{ " + text.substring(typoPositionStart, typoPositionEnd) + " }...\"");
        }
    }

    public String getTypoText() {
        return typo;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public int getTypoPositionStart() {
        return typoPositionStart;
    }

    public int getTypoPositionEnd() {
        return typoPositionEnd;
    }

}
