package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TextEditor extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int PADDING_NORMAL = 16;
    public static final int PADDING_MIDDLE = 8;
    public static final int PADDING_SMALL = 4;
    private static final List<MatchResult> foundMatches = new ArrayList<>();
    private static int currentMatchIndex = 0;

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JTextField searchText = new JTextField();
        searchText.setName(Constants.SEARCH_FIELD);

        JTextArea textArea = new JTextArea();
        textArea.setName(Constants.TEXT_AREA);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName(Constants.FILE_CHOOSER);
        this.add(fileChooser);

        JCheckBox useRegexCheckBox = new JCheckBox(Constants.REGEX_CHECKBOX_TEXT);
        useRegexCheckBox.setName(Constants.REGEX_CHECKBOX);

        ActionListener loadFromFileListener = getOpenFileListener(fileChooser, textArea);
        ActionListener saveToFileListener = getSaveFileListener(fileChooser, textArea);
        ActionListener exitListener = e -> System.exit(0);
        ActionListener searchListener = getSearchInTextListener(searchText,
                textArea,
                useRegexCheckBox);
        ActionListener toggleUseRegexListener = getUseRegexListener(useRegexCheckBox);
        ActionListener prevMatchListener = getPrevMatchListener(textArea);
        ActionListener nextMatchListener = getNextMatchListener(textArea);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = ElementsFactory.getMenu(Constants.FILE_MENU, KeyEvent.VK_F);
        String[] fileMenuItems = {Constants.OPEN_MENU, Constants.SAVE_MENU, Constants.EXIT_MENU};
        ActionListener[] fileMenuItemsListeners = {loadFromFileListener, saveToFileListener, exitListener};
        fulfillMenu(fileMenu, fileMenuItems, fileMenuItemsListeners);
        menuBar.add(fileMenu);

        JMenu searchMenu = ElementsFactory.getMenu(Constants.SEARCH_MENU, KeyEvent.VK_S);
        String[] searchMenuItems = {
                Constants.START_SEARCH_MENU,
                Constants.PREV_MATCH_MENU,
                Constants.NEXT_MATCH_MENU,
                Constants.USE_REGEX_MENU
        };
        ActionListener[] searchMenuListeners = {
                searchListener,
                prevMatchListener,
                nextMatchListener,
                toggleUseRegexListener
        };
        fulfillMenu(searchMenu, searchMenuItems, searchMenuListeners);
        menuBar.add(searchMenu);

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.LINE_AXIS));
        toolsPanel.setBorder(new EmptyBorder(PADDING_MIDDLE, PADDING_NORMAL, PADDING_SMALL, PADDING_NORMAL));
        add(toolsPanel, BorderLayout.NORTH);

        JButton saveButton = ElementsFactory.getButton(Constants.SAVE_BUTTON, saveToFileListener);
        JButton loadButton = ElementsFactory.getButton(Constants.LOAD_BUTTON, loadFromFileListener);
        JButton searchButton = ElementsFactory.getButton(Constants.SEARCH_BUTTON, searchListener);
        JButton prevButton = ElementsFactory.getButton(Constants.PREV_BUTTON, prevMatchListener);
        JButton nextButton = ElementsFactory.getButton(Constants.NEXT_BUTTON, nextMatchListener);

        toolsPanel.add(loadButton);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(saveButton);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(searchText);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(searchButton);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(prevButton);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(nextButton);
        toolsPanel.add(Box.createRigidArea(new Dimension(PADDING_SMALL, 0))); // Spacer
        toolsPanel.add(useRegexCheckBox);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new EmptyBorder(PADDING_SMALL, PADDING_NORMAL, PADDING_NORMAL, PADDING_NORMAL));
        scrollPane.setName(Constants.SCROLL_PANE);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void fulfillMenu(JMenu menu,
                             String[] menuNames,
                             ActionListener[] menuListeners) {
        for (int i = 0; i < menuNames.length; i++) {
            JMenuItem menuItem = ElementsFactory.getMenuItem(menuNames[i], menuListeners[i]);
            menu.add(menuItem);
        }
    }

    private ActionListener getUseRegexListener(JCheckBox useRegexCheckBox) {
        return e -> useRegexCheckBox.setSelected(true);
    }

    private ActionListener getOpenFileListener(JFileChooser dialog, JTextArea textArea) {
        return e -> {
            dialog.setDialogTitle(Constants.OPEN_FILE_TEXT);
            int code = dialog.showOpenDialog(null);
            if (code == JFileChooser.APPROVE_OPTION) {
                File file = dialog.getSelectedFile();
                String text = null;
                try {
                    text = FileManager.readFileAsString(file);
                } catch (IOException ex) {
                    System.out.println("No such file found!");
                }
                textArea.setText(text);
            }
        };
    }

    private ActionListener getSaveFileListener(JFileChooser dialog, JTextArea textArea) {
        return e -> {
            dialog.setDialogTitle(Constants.SAVE_FILE_TEXT);
            int code = dialog.showSaveDialog(null);
            if (code == JFileChooser.APPROVE_OPTION) {
                File fileToSave = dialog.getSelectedFile();
                String text = textArea.getText();
                FileManager.writeStringToFile(text, fileToSave);
            }
        };
    }

    private ActionListener getSearchInTextListener(JTextField patternField,
                                                   JTextArea textArea,
                                                   JCheckBox useRegexCheckbox) {
        return e -> findAllMatches(patternField, textArea, useRegexCheckbox);
    }

    private ActionListener getNextMatchListener(JTextArea textArea) {
        return e -> {
            if (foundMatches.size() == 0) {
                return;
            }
            currentMatchIndex = ++currentMatchIndex % foundMatches.size();
            MatchResult next = foundMatches.get(currentMatchIndex);
            selectSubstring(textArea, next);
        };
    }

    private ActionListener getPrevMatchListener(JTextArea textArea) {
        return e -> {
            if (foundMatches.size() == 0) {
                return;
            }
            if (currentMatchIndex - 1 >= 0) {
                currentMatchIndex = --currentMatchIndex % foundMatches.size();
            } else {
                currentMatchIndex = foundMatches.size() - 1;
            }
            MatchResult previous = foundMatches.get(currentMatchIndex);
            selectSubstring(textArea, previous);
        };
    }

    private void findAllMatches(JTextField patternField, JTextArea textArea, JCheckBox useRegexCheckbox) {
        foundMatches.clear();
        boolean isUseRegex = useRegexCheckbox.isSelected();
        SearchWorker worker = new SearchWorker(patternField.getText(), textArea, isUseRegex);
        worker.execute();
        try {
            List<MatchResult> result = worker.get();
            if (result.size() > 0) {
                foundMatches.addAll(result);
                currentMatchIndex = 0;
                MatchResult first = foundMatches.get(currentMatchIndex);
                selectSubstring(textArea, first);
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private void selectSubstring(JTextArea textArea, MatchResult match) {
        textArea.setCaretPosition(match.getStart());
        textArea.select(match.getStart(), match.getEnd());
        textArea.grabFocus();
    }
}