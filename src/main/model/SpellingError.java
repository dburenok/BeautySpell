package model;

public class SpellingError {

    private int typoPositionStart;
    private int typoPositionEnd;
    private String typo;
    private String suggestedWord;

    private final int previewRange = 15;

    public SpellingError(int typoPositionStart, int typoPositionEnd, String typo, String suggestedWord) {
        this.typoPositionStart = typoPositionStart;
        this.typoPositionEnd = typoPositionEnd;
        this.typo = typo;
        this.suggestedWord = suggestedWord;
    }

    public void showError(String text) {
        if (typoPositionStart > previewRange & text.length() > previewRange * 3) {
            System.out.println("\"..." + text.substring(typoPositionStart - previewRange, typoPositionStart)
                    + " { " + text.substring(typoPositionStart, typoPositionEnd) + " } "
                    + text.substring(typoPositionEnd, typoPositionEnd + previewRange) + "...\"");
        } else {
            System.out.println("\"...{ " + text.substring(typoPositionStart, typoPositionEnd) + " }...\"");
        }

        System.out.println("Typo: { " + typo + " } at index " + typoPositionStart + " in text.");
    }

}
