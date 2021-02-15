package model;

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

    // REQURIES:
    // MODIFIES:
    // EFFECTS:
    public void showError(String text) {

        final int previewRange = 15;

        if (typoPositionStart > previewRange & text.length() > previewRange * 3) {
            System.out.println("\"..." + text.substring(typoPositionStart - previewRange, typoPositionStart)
                    + " { " + text.substring(typoPositionStart, typoPositionEnd) + " } "
                    + text.substring(typoPositionEnd, typoPositionEnd + previewRange) + "...\"");
        } else {
            System.out.println("\"...{ " + text.substring(typoPositionStart, typoPositionEnd) + " }...\"");
        }

//        System.out.println("Typo: { " + typo + " } at index " + typoPositionStart + " in text.");
    }

    public String getTypoText() {
        return typo;
    }

    public int typoPositionStart() {
        return typoPositionStart;
    }

    public int typoPositionEnd() {
        return typoPositionEnd;
    }

}
