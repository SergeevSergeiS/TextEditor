package editor;

public class MatchResult {
    private final int start;
    private final int end;

    public MatchResult(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}