import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants;

public class DarkSmartChatBot extends JFrame {

    private JTextPane chatPane;
    private JTextField inputField;
    private JButton sendButton;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final Map<String, String> knowledge = new LinkedHashMap<>();

    public DarkSmartChatBot() {
        // === Window Setup ===
        setTitle("🧠 Smart AI ChatBot (Dark Mode)");
        setSize(650, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(18, 18, 18));

        // === Chat Pane ===
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(new Color(30, 30, 30));
        chatPane.setForeground(Color.WHITE);
        chatPane.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(chatPane);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scroll, BorderLayout.CENTER);

        // === Input Bar ===
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBackground(new Color(40, 40, 40));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0, 122, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setBackground(new Color(18, 18, 18));
        bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // === Knowledge Base ===
        loadKnowledge();

        // === Actions ===
        ActionListener sendAction = e -> handleInput();
        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        // === Greeting ===
        appendBot("Hello! I'm SmartChatBot in Dark Mode 🌙\nType 'help' to learn what I can do.");

        setVisible(true);
    }

    private void loadKnowledge() {
        knowledge.put("hello", "Hello there! 👋 I'm here 24/7. Ask me about Java, AI, or tech.");
        knowledge.put("hi", "Hey! How’s your day going?");
        knowledge.put("name", "I'm SmartChatBot — your intelligent Java assistant.");
        knowledge.put("java", "Java is a class-based, object-oriented language developed by Sun Microsystems (now Oracle).");
        knowledge.put("oop", "OOP stands for Object-Oriented Programming. It focuses on objects and classes for modular design.");
        knowledge.put("ai", "AI — Artificial Intelligence — enables machines to mimic human intelligence.");
        knowledge.put("ml", "Machine Learning (ML) is a subset of AI that allows systems to learn from data.");
        knowledge.put("python", "Python is popular for AI and data science due to its simplicity and large ecosystem.");
        knowledge.put("creator", "I was designed in Java using Swing components — built by a passionate developer.");
        knowledge.put("help", """
                🔹 Topics you can ask:
                   • Java, OOP, AI, ML, Python
                   • My creator / name
                   • 'search <topic>' → simulate web info
                   • 'time' → show system time
                   • 'bye' → exit conversation
                """);
        knowledge.put("bye", "Goodbye 👋 It was nice chatting with you!");
    }

    private void handleInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        appendUser(text);
        inputField.setText("");

        String lower = text.toLowerCase();
        String response;

        if (lower.startsWith("search ")) {
            String query = lower.replaceFirst("search ", "").trim();
            response = simulateSearch(query);
        } else if (lower.contains("time")) {
            response = "🕒 Current time: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
        } else {
            String found = null;
            for (String key : knowledge.keySet()) {
                if (lower.contains(key)) {
                    found = knowledge.get(key);
                    break;
                }
            }
            response = found;
        }

        if (response == null)
            response = "Hmm 🤔 I’m not sure. Try 'help' or 'search <your topic>'.";

        // simulate typing delay using javax.swing.Timer
        final String botResponse = response;
        javax.swing.Timer t = new javax.swing.Timer(500, e -> appendBot(botResponse));
        t.setRepeats(false);
        t.start();
    }

    private String simulateSearch(String query) {
        // Fake search engine result generator
        String[] examples = {
                "Here’s what I found on " + query + ": it’s an interesting topic widely discussed in tech forums.",
                "🔍 According to simulated web sources, " + query + " is trending in computer science.",
                "📘 " + query + " is a vast subject! You can read more on Wikipedia or educational sites.",
                "Sorry, I can’t access real-time web, but you can easily find info on " + query + " online."
        };
        return examples[new Random().nextInt(examples.length)];
    }

    private void appendUser(String text) {
        appendMessage("You", text, new Color(0, 153, 255));
    }

    private void appendBot(String text) {
        appendMessage("Bot", text, new Color(200, 200, 200));
    }

    private void appendMessage(String sender, String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            try {
                var doc = chatPane.getStyledDocument();
                var style = chatPane.addStyle("Style_" + sender, null);
                StyleConstants.setForeground(style, color);
                StyleConstants.setFontFamily(style, "Consolas");
                doc.insertString(doc.getLength(),
                        String.format("[%s] %s: %s\n\n", timeFormat.format(new Date()), sender, message),
                        style);
                chatPane.setCaretPosition(doc.getLength());
            } catch (Exception ignored) {}
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DarkSmartChatBot::new);
    }
}
