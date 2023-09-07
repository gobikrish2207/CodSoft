import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class WordCounterGUI extends JFrame {
    private JTextArea textArea;
    private JButton clearButton;
    private JButton countButton;
    private JLabel wordCountLabel;
    private JTextArea statisticsArea;

    public WordCounterGUI() {
        setTitle("Word Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                wordCountLabel.setText("Word count: 0");
                statisticsArea.setText("");
            }
        });

        countButton = new JButton("Count Words");
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textArea.getText().trim();

                if (inputText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter text.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] words = inputText.split("[\\s\\p{Punct}]+");
                int wordCount = words.length;
                wordCountLabel.setText("Word count: " + wordCount);

                // Ignore common words (stop words)
                Set<String> stopWords = new HashSet<>(Arrays.asList("the", "and", "in", "to", "of", "a"));
                Map<String, Integer> wordFrequency = new HashMap<>();
                for (String word : words) {
                    String lowerCaseWord = word.toLowerCase();
                    if (!stopWords.contains(lowerCaseWord)) {
                        wordFrequency.put(lowerCaseWord, wordFrequency.getOrDefault(lowerCaseWord, 0) + 1);
                    }
                }

                // Display statistics
                StringBuilder statistics = new StringBuilder();
                statistics.append("Statistics:\n");
                statistics.append("Unique words: ").append(wordFrequency.size()).append("\n");
                for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                    statistics.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                statisticsArea.setText(statistics.toString());
            }
        });

        wordCountLabel = new JLabel("Word count: 0");
        statisticsArea = new JTextArea();
        statisticsArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(countButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(wordCountLabel, BorderLayout.NORTH);

        JPanel statisticsPanel = new JPanel(new BorderLayout());
        statisticsPanel.add(new JLabel("Word Statistics:"), BorderLayout.NORTH);
        statisticsPanel.add(new JScrollPane(statisticsArea), BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.add(mainPanel);
        contentPanel.add(statisticsPanel);

        add(contentPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCounterGUI();
            }
        });
    }
}
