package model;

public class SpellingError {

    private int typoPositionStart;
    private int typoPositionEnd;
    private String typo;
    private String suggestedWord;

    public SpellingError(int typoPositionStart, int typoPositionEnd, String typo, String suggestedWord) {
        this.typoPositionStart = typoPositionStart;
        this.typoPositionEnd = typoPositionEnd;
        this.typo = typo;
        this.suggestedWord = suggestedWord;
    }

}
