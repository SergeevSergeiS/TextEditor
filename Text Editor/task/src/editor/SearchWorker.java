package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWorker extends SwingWorker<List<MatchResult>, String> {
    private final String patternToFind;
    private final JTextArea textArea;
    private final boolean isUseRegex;

    public SearchWorker(String pattern, JTextArea textArea, boolean isUseRegex) {
        this.patternToFind = pattern;
        this.textArea = textArea;
        this.isUseRegex = isUseRegex;
    }

    @Override
    protected List<MatchResult> doInBackground() {
        List<MatchResult> foundSubstrings = new ArrayList<>();
        String resultPattern = isUseRegex ? patternToFind : Pattern.quote(patternToFind);
        Pattern pattern = Pattern.compile(resultPattern);
        Matcher matcher = pattern.matcher(textArea.getText());
        while (matcher.find()) {
            foundSubstrings.add(new MatchResult(matcher.start(), matcher.end()));
        }
        return foundSubstrings;
    }
}