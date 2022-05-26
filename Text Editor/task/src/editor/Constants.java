package editor;

import java.util.Map;

public class Constants {
    public static final String NEXT_BUTTON = "NextMatchButton";
    public static final String PREV_BUTTON = "PreviousMatchButton";
    public static final String SAVE_BUTTON = "SaveButton";
    public static final String LOAD_BUTTON = "OpenButton";
    public static final String SEARCH_BUTTON = "StartSearchButton";
    public static final String TEXT_AREA = "TextArea";
    public static final String SEARCH_FIELD = "SearchField";
    public static final String REGEX_CHECKBOX = "UseRegExCheckbox";
    public static final String REGEX_CHECKBOX_TEXT = "Use Regex";
    public static final String FILE_CHOOSER = "FileChooser";
    public static final String SCROLL_PANE = "ScrollPane";
    public static final String FILE_MENU = "MenuFile";
    public static final String SEARCH_MENU = "MenuSearch";
    public static final String OPEN_MENU = "MenuOpen";
    public static final String SAVE_MENU = "MenuSave";
    public static final String EXIT_MENU = "MenuExit";
    public static final String START_SEARCH_MENU = "MenuStartSearch";
    public static final String PREV_MATCH_MENU = "MenuPreviousMatch";
    public static final String NEXT_MATCH_MENU = "MenuNextMatch";
    public static final String USE_REGEX_MENU = "MenuUseRegExp";
    public static final String OPEN_FILE_TEXT = "Open file";
    public static final String SAVE_FILE_TEXT = "Save file";

    public static final Map<String, String> menuTitles = Map.of(
            FILE_MENU, "File",
            SEARCH_MENU, "Search",
            OPEN_MENU, "Open",
            SAVE_MENU, "Save",
            EXIT_MENU, "Exit",
            START_SEARCH_MENU, "Start search",
            PREV_MATCH_MENU, "Previous search",
            NEXT_MATCH_MENU, "Next match",
            USE_REGEX_MENU, "Use regular Expressions"
    );

    private static final String SOURCE = "source\\";
    public static final Map<String, String> iconsPaths = Map.of(
            LOAD_BUTTON, SOURCE + "open_file_32.png",
            SAVE_BUTTON, SOURCE + "save_file_32.png",
            SEARCH_BUTTON, SOURCE + "search_32.png",
            PREV_BUTTON, SOURCE + "prev_32.png",
            NEXT_BUTTON, SOURCE + "next_32.png"
    );
}