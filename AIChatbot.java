import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * CodeAlpha Task 3 - Artificial Intelligence Chatbot
 * Single-file Java Swing application. No external libraries required.
 * Run: javac AICodeAlphaChatbot.java && java AICodeAlphaChatbot
 */
public class AIChatbot extends JFrame {
    private static final Color BG = new Color(9, 12, 27);
    private static final Color PANEL = new Color(17, 22, 43);
    private static final Color CYAN = new Color(53, 220, 245);
    private static final Color PURPLE = new Color(157, 105, 255);
    private static final Color MUTED = new Color(151, 164, 190);

    private final JPanel messages = new JPanel();
    private JScrollPane chatScroll;
    private final JTextField input = new JTextField();
    private final JLabel status = new JLabel("  ONLINE • READY TO ASSIST");
    private final JLabel clock = new JLabel();
    private final Map<String, String> knowledge = new LinkedHashMap<>();
    private final java.util.List<String> chatHistory = new ArrayList<>();
    private final Set<String> stopWords = new HashSet<>(Arrays.asList(
            "the", "is", "a", "an", "what", "how", "are", "about", "of", "to", "for", "in", "on", "and", "can", "you", "tell", "me", "please", "do", "does", "i", "it", "this", "that", "with", "your", "my", "be", "as", "at", "from", "or", "we", "our", "does", "work", "explain"
    ));

    public AIChatbot() {
        super("NEXUS AI | CodeAlpha Chatbot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);
        loadKnowledgeBase();
        buildInterface();
        startClock();
    }

    private void loadKnowledgeBase() {
        knowledge.put("hello", "Hello! 👋 I'm Nexus, your AI assistant. Ask me anything about Java, programming, or the CodeAlpha internship.");
        knowledge.put("hi", "Hi there! Great to see you. What would you like to know today?");
        knowledge.put("how are you", "I'm running smoothly and ready to help! Thanks for asking. 😊");
        knowledge.put("what is codealpha", "CodeAlpha is a technology company that provides internship and learning opportunities in areas such as software development, artificial intelligence, and other technologies.");
        knowledge.put("internship", "An internship is a learning opportunity where students gain practical experience by working on projects related to their field. CodeAlpha assigns tasks to help interns build real-world skills.");
        knowledge.put("what is artificial intelligence", "Artificial Intelligence (AI) is a field of computer science that develops systems capable of performing tasks that normally require human intelligence, such as learning, reasoning, and understanding language.");
        knowledge.put("what is machine learning", "Machine Learning (ML) is a branch of AI that enables computers to learn patterns from data and make predictions or decisions without being explicitly programmed for every situation.");
        knowledge.put("what is nlp", "Natural Language Processing (NLP) is a branch of AI that helps computers understand, process, and respond to human language.");
        knowledge.put("what is a chatbot", "A chatbot is a software application that communicates with users through text or voice. It can answer questions, provide information, and automate conversations.");
        knowledge.put("how does this chatbot work", "I process your message, clean and tokenize the text, compare its important words with my FAQ knowledge base, and return the closest matching answer when the similarity is high enough.");
        knowledge.put("what is java", "Java is a high-level, object-oriented programming language. It is widely used to build desktop, web, mobile, and enterprise applications.");
        knowledge.put("what is oop", "Object-Oriented Programming (OOP) organizes software around objects and classes. Its four main principles are encapsulation, inheritance, polymorphism, and abstraction.");
        knowledge.put("what is an array", "An array is a data structure that stores multiple values of the same data type in a fixed-size sequence. Each element is accessed using an index.");
        knowledge.put("what is swing", "Java Swing is a GUI toolkit used to create desktop applications. It provides components such as JFrame, JPanel, JButton, JLabel, and JTextField.");
        knowledge.put("what is gui", "A Graphical User Interface (GUI) allows users to interact with an application through visual elements such as buttons, menus, icons, and text fields.");
        knowledge.put("what is a database", "A database is an organized collection of data that can be stored, managed, retrieved, and updated efficiently.");
        knowledge.put("what is an algorithm", "An algorithm is a finite sequence of clear steps used to solve a problem or perform a computation.");
        knowledge.put("what is an api", "An API (Application Programming Interface) is a set of rules that allows different software applications or components to communicate with each other.");
        knowledge.put("what are the features", "This chatbot includes a graphical interface, animated AI orb, FAQ knowledge base, text preprocessing, similarity-based answer matching, and a fallback response for unknown questions.");
        knowledge.put("who created you", "I am Nexus AI, a Java Swing chatbot created as a CodeAlpha Artificial Intelligence Chatbot internship project.");
        knowledge.put("thank you", "You're welcome! Happy to help. ✨ Feel free to ask another question.");
        knowledge.put("bye", "Goodbye! Keep learning and building amazing things. See you again! 👋");
        knowledge.put("help", "Here are some topics you can ask about: Java, OOP, arrays, Swing, GUI, AI, machine learning, NLP, chatbots, algorithms, APIs, and CodeAlpha internships.");
        // Expanded FAQ knowledge base: programming, AI, NLP, and computer science.
        knowledge.put("what is a variable", "A variable is a named memory location used to store a value that can be used or changed while a program runs.");
        knowledge.put("what are data types", "Data types specify the kind of value a variable can store. Java has primitive types such as int, double, char, and boolean, as well as reference types such as String and arrays.");
        knowledge.put("what is a loop", "A loop repeatedly executes a block of code while a condition is satisfied or for a specified number of iterations.");
        knowledge.put("what is a for loop", "A for loop is commonly used when the number of repetitions is known. It has initialization, a condition, and an update expression.");
        knowledge.put("what is a while loop", "A while loop repeats its body as long as its condition is true. The condition is checked before each iteration.");
        knowledge.put("what is a do while loop", "A do-while loop executes its body at least once, then checks the condition before repeating.");
        knowledge.put("what is a method", "A method is a named block of code that performs a task and can be called whenever needed.");
        knowledge.put("what is a constructor", "A constructor is a special class member used to initialize a new object. It has the same name as the class and no return type.");
        knowledge.put("what is inheritance", "Inheritance allows one Java class to acquire accessible fields and methods from another class using the extends keyword.");
        knowledge.put("what is polymorphism", "Polymorphism means one interface or parent type can represent different object forms. In Java it commonly appears through method overloading and overriding.");
        knowledge.put("what is encapsulation", "Encapsulation bundles data and methods in a class and controls access to data, often using private fields and public methods.");
        knowledge.put("what is abstraction", "Abstraction exposes essential behaviour while hiding implementation details. Java supports it through abstract classes and interfaces.");
        knowledge.put("what is an interface", "An interface defines a contract that implementing classes agree to follow. It can declare abstract methods and also support default and static methods.");
        knowledge.put("what is an exception", "An exception is an event that interrupts the normal flow of a program. Java handles exceptions with try, catch, finally, throw, and throws.");
        knowledge.put("what is exception handling", "Exception handling is the process of detecting and responding to runtime problems so a program can handle errors gracefully.");
        knowledge.put("what is a string", "A String in Java represents text as a sequence of characters. String objects are immutable.");
        knowledge.put("what is an arraylist", "ArrayList is a resizable list implementation in Java. It stores elements in order and provides methods such as add, get, set, and remove.");
        knowledge.put("what is a hashmap", "HashMap stores key-value pairs and provides efficient average-time lookup by key. It does not guarantee iteration order.");
        knowledge.put("what is multithreading", "Multithreading allows multiple threads of execution to run within one process, helping applications perform concurrent tasks.");
        knowledge.put("what is a package in java", "A Java package groups related classes and interfaces, helps organize code, and can help control access.");
        knowledge.put("what is jvm", "The Java Virtual Machine executes Java bytecode. It helps Java programs run on different platforms that provide a compatible JVM.");
        knowledge.put("what is jdk", "The Java Development Kit contains tools needed to develop Java applications, including the compiler and runtime components.");
        knowledge.put("what is jre", "The Java Runtime Environment provides the components needed to run Java applications, including the JVM and supporting libraries.");
        knowledge.put("what is compilation", "Compilation translates source code into another form, such as Java source code into bytecode that the JVM can execute.");
        knowledge.put("what is artificial intelligence", "Artificial Intelligence is the area of computing focused on systems that perform tasks associated with human intelligence, such as reasoning, perception, and language understanding.");
        knowledge.put("what are applications of ai", "AI is used in healthcare support, recommendation systems, translation, fraud detection, robotics, customer service, accessibility tools, and many other areas.");
        knowledge.put("what are the advantages of ai", "AI can automate repetitive work, process large amounts of data, support decisions, improve consistency, and provide services quickly. Results still need appropriate human oversight.");
        knowledge.put("what are the limitations of ai", "AI systems can make mistakes, reflect biases in their data, lack common-sense understanding, require resources, and raise privacy or security concerns.");
        knowledge.put("what is machine learning", "Machine learning is a branch of AI in which systems learn patterns from data to make predictions or decisions.");
        knowledge.put("what is supervised learning", "Supervised learning trains a model using labelled examples, where each training input is paired with a known target answer.");
        knowledge.put("what is unsupervised learning", "Unsupervised learning finds patterns or structure in data that has no supplied target labels, such as grouping similar items.");
        knowledge.put("what is reinforcement learning", "Reinforcement learning trains an agent to choose actions by receiving rewards or penalties from an environment.");
        knowledge.put("what is a neural network", "A neural network is a machine-learning model made of connected computational units arranged in layers. It learns patterns by adjusting numerical weights.");
        knowledge.put("what is deep learning", "Deep learning is a part of machine learning that uses neural networks with multiple layers to learn complex patterns.");
        knowledge.put("what is training data", "Training data is the set of examples used by a machine-learning algorithm to learn model parameters or patterns.");
        knowledge.put("what is testing data", "Testing data is a separate set of examples used to evaluate how well a trained model performs on data it has not used for training.");
        knowledge.put("what is overfitting", "Overfitting occurs when a model learns training examples too closely, including noise, and performs poorly on new data.");
        knowledge.put("what is nlp", "Natural Language Processing is a field of AI that enables computers to process, analyse, and generate human language.");
        knowledge.put("what is tokenization", "Tokenization splits text into smaller units called tokens, such as sentences, words, or subwords.");
        knowledge.put("what are stop words", "Stop words are common words such as 'the', 'is', and 'a' that may be removed during some text-processing tasks because they often carry limited topic-specific information.");
        knowledge.put("what is stemming", "Stemming reduces words to a root-like form by removing prefixes or suffixes. The result may not be a valid dictionary word.");
        knowledge.put("what is lemmatization", "Lemmatization converts a word to its dictionary base form using linguistic information, such as converting 'running' to 'run'.");
        knowledge.put("what is tf idf", "TF-IDF stands for Term Frequency-Inverse Document Frequency. It scores how important a word is to a document relative to a collection of documents.");
        knowledge.put("what is cosine similarity", "Cosine similarity measures the angle-based similarity between two non-zero vectors. It is often used to compare text represented as numerical vectors.");
        knowledge.put("what is sentiment analysis", "Sentiment analysis is an NLP task that identifies opinions or emotional tone in text, often classifying it as positive, negative, or neutral.");
        knowledge.put("what is text preprocessing", "Text preprocessing prepares raw text for analysis. Common steps include lowercasing, removing punctuation, tokenization, and optionally removing stop words.");
        knowledge.put("what is python", "Python is a high-level programming language known for readable syntax and use in automation, web development, data analysis, and AI.");
        knowledge.put("what is c programming", "C is a procedural programming language widely used in systems programming, embedded development, and performance-sensitive software.");
        knowledge.put("what is c plus plus", "C++ is a general-purpose programming language that supports procedural, object-oriented, and generic programming.");
        knowledge.put("what is html", "HTML is the markup language used to structure content on web pages, such as headings, paragraphs, links, and forms.");
        knowledge.put("what is css", "CSS is used to style web pages, controlling layout, colours, typography, spacing, and responsive presentation.");
        knowledge.put("what is javascript", "JavaScript is a programming language commonly used to add interactivity and dynamic behaviour to websites and web applications.");
        knowledge.put("what is dbms", "A Database Management System is software used to define, create, store, retrieve, and manage data in databases.");
        knowledge.put("what is sql", "SQL is a language used to define, query, and modify data in relational database systems.");
        knowledge.put("what is operating system", "An operating system manages computer hardware and software resources and provides services for application programs.");
        knowledge.put("what is computer network", "A computer network is a group of connected devices that communicate and share data or resources.");
        knowledge.put("what is ip address", "An IP address is a numerical address used to identify an interface on an IP network and help route data to it.");
        knowledge.put("what is data structure", "A data structure is a way of organizing and storing data so operations such as access, insertion, deletion, and search can be performed efficiently.");
        knowledge.put("what is linked list", "A linked list is a linear data structure made of nodes, where each node stores data and a reference to another node.");
        knowledge.put("what is stack", "A stack is a data structure that follows Last In, First Out (LIFO). The most recently added item is removed first.");
        knowledge.put("what is queue", "A queue is a data structure that typically follows First In, First Out (FIFO). The earliest added item is removed first.");
        knowledge.put("what is binary search", "Binary search finds a target in sorted data by repeatedly dividing the search range in half.");
        knowledge.put("what is sorting", "Sorting arranges data in a chosen order, such as ascending or descending. Examples include bubble sort, insertion sort, merge sort, and quicksort.");
        knowledge.put("what is an algorithm", "An algorithm is a finite, well-defined sequence of steps for solving a problem or completing a task.");
        knowledge.put("what is time complexity", "Time complexity describes how an algorithm's running time grows as the input size increases, often expressed using Big O notation.");
        knowledge.put("what is cloud computing", "Cloud computing provides computing resources such as storage, servers, databases, and software over a network, usually on demand.");
        knowledge.put("what is cybersecurity", "Cybersecurity is the practice of protecting systems, networks, applications, and data from digital attacks and unauthorized access.");
        knowledge.put("what is api", "An API is a defined way for software components or applications to communicate and use each other's functionality.");
        knowledge.put("what is git", "Git is a distributed version-control system that tracks changes to files and helps developers collaborate on software projects.");
        knowledge.put("what is github", "GitHub is an online platform for hosting Git repositories and collaborating through features such as issues and pull requests.");
        knowledge.put("what is debugging", "Debugging is the process of finding, understanding, and fixing errors or unexpected behaviour in a program.");
        knowledge.put("what is software testing", "Software testing checks whether an application behaves as expected and helps identify defects before release.");
        knowledge.put("what is agile", "Agile is an approach to software development that emphasizes iterative work, feedback, collaboration, and adapting to change.");
        knowledge.put("what is codealpha", "CodeAlpha is a technology company that offers internship and learning opportunities in software and technology-related fields.");
        knowledge.put("how do i complete my internship task", "Read the task requirements, implement and test your project, upload the complete source code to the required GitHub repository, prepare the requested demonstration, and submit through the official form.");
        knowledge.put("what should i include in github", "Include your complete source code, a README with project description and run instructions, and any required resources. Do not upload passwords, API keys, or private information.");
        knowledge.put("what is the purpose of this project", "The purpose of this project is to demonstrate a Java-based chatbot with a graphical interface, text preprocessing, FAQ matching, and responses to common questions.");
        knowledge.put("what is rule based chatbot", "A rule-based chatbot uses predefined rules, keywords, or stored question-answer pairs to select responses. It does not learn automatically unless additional learning functionality is implemented.");
        knowledge.put("is this chatbot connected to the internet", "No. This version works locally using its built-in knowledge base. It does not access the internet or a live AI service.");
        knowledge.put("does this chatbot learn automatically", "No. The current version uses predefined FAQ data and similarity matching. Its knowledge base must be updated by adding more question-answer entries.");
        knowledge.put("what algorithm does this chatbot use", "This chatbot tokenizes and normalizes the question, compares its words with stored FAQ questions using the Dice similarity coefficient, and returns the closest answer when the score passes a threshold.");
    }

    private void buildInterface() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(0, 0));
        main.setOpaque(false);
        main.add(createTopBar(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(16, 22, 18, 22));
        center.add(createHero(), BorderLayout.NORTH);

        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        messages.setBackground(PANEL);
        messages.setBorder(new EmptyBorder(18, 18, 18, 18));
        chatScroll = new JScrollPane(messages);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(49, 61, 91), 1, true));
        chatScroll.getViewport().setBackground(PANEL);
        chatScroll.getVerticalScrollBar().setUnitIncrement(18);
        chatScroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() { thumbColor = new Color(73, 91, 139); trackColor = PANEL; }
            @Override protected JButton createDecreaseButton(int o) { return zeroButton(); }
            @Override protected JButton createIncreaseButton(int o) { return zeroButton(); }
            private JButton zeroButton() { JButton b = new JButton(); b.setPreferredSize(new Dimension(0, 0)); return b; }
        });
        center.add(chatScroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(0, 10));
        bottom.setOpaque(false);
        bottom.add(createSuggestions(), BorderLayout.NORTH);
        bottom.add(createInputBar(), BorderLayout.SOUTH);
        center.add(bottom, BorderLayout.SOUTH);
        main.add(center, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);
        add(root, BorderLayout.CENTER);

        addMessage("assistant", "Welcome to Nexus AI! I'm your intelligent assistant. Ask me a question or choose one of the suggested prompts below.\n\nI can help with Java, AI, NLP, programming concepts, and CodeAlpha internship topics.");
    }

    private JPanel createSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(225, 0));
        side.setBackground(new Color(12, 16, 33));
        side.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(38, 48, 76)));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(new EmptyBorder(24, 16, 20, 16));

        JLabel brand = new JLabel("✦  NEXUS AI");
        brand.setFont(new Font("SansSerif", Font.BOLD, 22));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(brand);
        JLabel sub = new JLabel("  INTELLIGENCE INTERFACE");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 9));
        sub.setForeground(CYAN);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(Box.createVerticalStrut(5)); side.add(sub);
        side.add(Box.createVerticalStrut(42));

        side.add(sideLabel("WORKSPACE"));
        side.add(Box.createVerticalStrut(12));
        side.add(navItem("◈", "AI Assistant", true));
        side.add(Box.createVerticalStrut(7));
        side.add(navItem("▤", "Knowledge Base", false));
        side.add(Box.createVerticalStrut(7));
        side.add(navItem("◷", "Chat History", false));
        side.add(Box.createVerticalStrut(30));
        side.add(sideLabel("QUICK TOPICS"));
        side.add(Box.createVerticalStrut(12));
        String[] topics = {"Java Programming", "Artificial Intelligence", "Machine Learning", "Natural Language"};
        for (String t : topics) {
            JLabel item = new JLabel("  •  " + t);
            item.setFont(new Font("SansSerif", Font.PLAIN, 11));
            item.setForeground(MUTED);
            item.setBorder(new EmptyBorder(9, 2, 9, 2));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            side.add(item);
        }
        side.add(Box.createVerticalGlue());

        JPanel profile = new JPanel(new BorderLayout(9, 0));
        profile.setOpaque(false);
        JLabel avatar = new JLabel("AI", SwingConstants.CENTER);
        avatar.setOpaque(true); avatar.setBackground(new Color(53, 80, 133)); avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 12)); avatar.setPreferredSize(new Dimension(34, 34));
        avatar.setBorder(BorderFactory.createLineBorder(CYAN, 1, true));
        JPanel names = new JPanel(); names.setOpaque(false); names.setLayout(new BoxLayout(names, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Nexus Assistant"); name.setForeground(Color.WHITE); name.setFont(new Font("SansSerif", Font.BOLD, 11));
        JLabel role = new JLabel("Java • CodeAlpha Task 3"); role.setForeground(MUTED); role.setFont(new Font("SansSerif", Font.PLAIN, 9));
        names.add(name); names.add(Box.createVerticalStrut(3)); names.add(role);
        profile.add(avatar, BorderLayout.WEST); profile.add(names, BorderLayout.CENTER);
        profile.setBorder(new EmptyBorder(12, 2, 0, 2));
        side.add(profile);
        return side;
    }

    private JLabel sideLabel(String text) {
        JLabel l = new JLabel(text); l.setFont(new Font("SansSerif", Font.BOLD, 9)); l.setForeground(new Color(105, 121, 155)); l.setAlignmentX(Component.LEFT_ALIGNMENT); return l;
    }

    private JPanel navItem(String icon, String text, boolean selected) {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setMaximumSize(new Dimension(195, 39)); p.setPreferredSize(new Dimension(195, 39));
        p.setBackground(selected ? new Color(32, 48, 82) : new Color(12, 16, 33));
        p.setBorder(new EmptyBorder(0, 11, 0, 8)); p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel i = new JLabel(icon); i.setForeground(selected ? CYAN : MUTED); i.setFont(new Font("SansSerif", Font.BOLD, 15));
        JLabel t = new JLabel(text); t.setForeground(selected ? Color.WHITE : MUTED); t.setFont(new Font("SansSerif", selected ? Font.BOLD : Font.PLAIN, 11));
        p.add(i, BorderLayout.WEST); p.add(t, BorderLayout.CENTER);
        if (selected) p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, CYAN), new EmptyBorder(0, 8, 0, 8)));
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.setToolTipText("Open " + text);
        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if ("Knowledge Base".equals(text)) showKnowledgeBase();
                else if ("Chat History".equals(text)) showChatHistory();
                else if ("AI Assistant".equals(text)) input.requestFocusInWindow();
            }
            @Override public void mouseEntered(MouseEvent e) {
                if (!selected) p.setBackground(new Color(27, 37, 65));
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!selected) p.setBackground(new Color(12, 16, 33));
            }
        });
        return p;
    }

    private JPanel createTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false); top.setBorder(new EmptyBorder(17, 24, 13, 25));
        JPanel title = new JPanel(); title.setOpaque(false); title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        JLabel h = new JLabel("AI CHAT INTERFACE"); h.setForeground(Color.WHITE); h.setFont(new Font("SansSerif", Font.BOLD, 15));
        JLabel s = new JLabel("Your personal knowledge companion"); s.setForeground(MUTED); s.setFont(new Font("SansSerif", Font.PLAIN, 11));
        title.add(h); title.add(Box.createVerticalStrut(4)); title.add(s);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); right.setOpaque(false);
        status.setFont(new Font("SansSerif", Font.BOLD, 9)); status.setForeground(new Color(91, 235, 174));
        clock.setFont(new Font("Monospaced", Font.PLAIN, 11)); clock.setForeground(MUTED);
        right.add(status); right.add(new JLabel("  |  ") {{ setForeground(new Color(65, 77, 105)); }}); right.add(clock);
        top.add(title, BorderLayout.WEST); top.add(right, BorderLayout.EAST);
        top.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(38, 48, 76)), new EmptyBorder(17, 24, 13, 25)));
        return top;
    }

    private JPanel createHero() {
        JPanel hero = new JPanel(new BorderLayout(18, 0));
        hero.setBackground(new Color(19, 28, 55));
        hero.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(56, 76, 123), 1, true), new EmptyBorder(13, 20, 13, 18)));
        JPanel copy = new JPanel(); copy.setOpaque(false); copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        JLabel eyebrow = new JLabel("✦  YOUR AI COMPANION"); eyebrow.setForeground(CYAN); eyebrow.setFont(new Font("SansSerif", Font.BOLD, 9));
        JLabel headline = new JLabel("Ideas become conversations."); headline.setForeground(Color.WHITE); headline.setFont(new Font("SansSerif", Font.BOLD, 21));
        JLabel desc = new JLabel("Ask questions. Explore concepts. Learn something new."); desc.setForeground(MUTED); desc.setFont(new Font("SansSerif", Font.PLAIN, 11));
        copy.add(eyebrow); copy.add(Box.createVerticalStrut(7)); copy.add(headline); copy.add(Box.createVerticalStrut(5)); copy.add(desc);
        OrbPanel orb = new OrbPanel(); orb.setPreferredSize(new Dimension(130, 100)); orb.setOpaque(false);
        hero.add(copy, BorderLayout.CENTER); hero.add(orb, BorderLayout.EAST);
        return hero;
    }

    private JPanel createSuggestions() {
        JPanel wrap = new JPanel(new BorderLayout(0, 7)); wrap.setOpaque(false);
        JLabel label = new JLabel("  TRY ASKING"); label.setForeground(new Color(126, 143, 174)); label.setFont(new Font("SansSerif", Font.BOLD, 9));
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); row.setOpaque(false);
        String[] prompts = {"What is Java?", "Explain NLP", "How does this chatbot work?", "What is CodeAlpha?"};
        for (String prompt : prompts) {
            JButton b = new JButton(prompt); styleChip(b); b.addActionListener(e -> sendText(prompt)); row.add(b);
        }
        wrap.add(label, BorderLayout.NORTH); wrap.add(row, BorderLayout.CENTER); return wrap;
    }

    private JPanel createInputBar() {
        JPanel outer = new JPanel(new BorderLayout(10, 0)); outer.setOpaque(false);
        JPanel fieldPanel = new JPanel(new BorderLayout()); fieldPanel.setBackground(new Color(20, 27, 49));
        fieldPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(60, 75, 111), 1, true), new EmptyBorder(0, 13, 0, 13)));
        input.setBackground(new Color(20, 27, 49)); input.setForeground(Color.WHITE); input.setCaretColor(CYAN);
        input.setFont(new Font("SansSerif", Font.PLAIN, 13)); input.setBorder(null); input.setPreferredSize(new Dimension(100, 48));
        input.setToolTipText("Type your message and press Enter");
        input.addActionListener(e -> sendText(input.getText()));
        JLabel attach = new JLabel("✧  "); attach.setForeground(MUTED); attach.setFont(new Font("SansSerif", Font.BOLD, 17));
        fieldPanel.add(attach, BorderLayout.WEST); fieldPanel.add(input, BorderLayout.CENTER);
        JButton send = new JButton("SEND  ➤"); send.setFont(new Font("SansSerif", Font.BOLD, 11)); send.setForeground(new Color(8, 15, 30));
        send.setBackground(CYAN); send.setFocusPainted(false); send.setBorder(new EmptyBorder(0, 20, 0, 20)); send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        send.addActionListener(e -> sendText(input.getText()));
        outer.add(fieldPanel, BorderLayout.CENTER); outer.add(send, BorderLayout.EAST);
        return outer;
    }

    private void styleChip(JButton b) {
        b.setFont(new Font("SansSerif", Font.PLAIN, 10)); b.setForeground(new Color(188, 204, 231));
        b.setBackground(new Color(25, 34, 61)); b.setFocusPainted(false); b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(58, 75, 113), 1, true), new EmptyBorder(7, 10, 7, 10)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void sendText(String text) {
        String question = text == null ? "" : text.trim();
        if (question.isEmpty()) { input.requestFocusInWindow(); return; }
        input.setText("");
        addMessage("user", question);
        status.setText("  PROCESSING MESSAGE..."); status.setForeground(new Color(255, 205, 105));
        javax.swing.Timer timer = new javax.swing.Timer(420, e -> {
            String answer = findAnswer(question);
            addMessage("assistant", answer);
            status.setText("  ONLINE • READY TO ASSIST"); status.setForeground(new Color(91, 235, 174));
        });
        timer.setRepeats(false); timer.start();
    }

    private String findAnswer(String question) {
        String cleaned = normalize(question);
        if (cleaned.isEmpty()) return "Please type a little more so I can understand your question.";
        String bestAnswer = null; double bestScore = 0.0;
        Set<String> queryWords = tokens(cleaned);
        for (Map.Entry<String, String> entry : knowledge.entrySet()) {
            Set<String> keyWords = tokens(normalize(entry.getKey()));
            double score = similarity(queryWords, keyWords);
            if (cleaned.equals(normalize(entry.getKey()))) score = 1.0;
            if (score > bestScore) { bestScore = score; bestAnswer = entry.getValue(); }
        }
        // A small keyword-intent layer improves matching for common greetings and topics.
        if (containsAny(cleaned, "good morning", "good afternoon", "good evening")) return "Hello! I hope you're having a great day. How can I help you?";
        if (cleaned.contains("feature") || cleaned.contains("features")) return knowledge.get("what are the features");
        if (cleaned.contains("thank")) return knowledge.get("thank you");
        if (cleaned.equals("bye") || cleaned.contains("goodbye")) return knowledge.get("bye");
        if (bestScore >= 0.20 && bestAnswer != null) return bestAnswer + "\n\n▸ Match confidence: " + Math.round(bestScore * 100) + "%";
        return "I'm still learning, and I couldn't find a reliable answer to that question.\n\nTry asking about Java, OOP, arrays, Swing, AI, machine learning, NLP, chatbots, algorithms, APIs, or CodeAlpha. You can also type **help** to see suggested topics.";
    }

    private boolean containsAny(String text, String... values) { for (String v : values) if (text.contains(v)) return true; return false; }

    private String normalize(String text) {
        return text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9\\s]", " ").replaceAll("\\s+", " ").trim();
    }

    private Set<String> tokens(String text) {
        Set<String> result = new HashSet<>();
        for (String word : text.split("\\s+")) if (word.length() > 1 && !stopWords.contains(word)) result.add(word);
        return result;
    }

    private double similarity(Set<String> a, Set<String> b) {
        if (a.isEmpty() || b.isEmpty()) return 0.0;
        Set<String> intersection = new HashSet<>(a); intersection.retainAll(b);
        // Dice coefficient: simple, explainable similarity score for the FAQ matcher.
        return (2.0 * intersection.size()) / (a.size() + b.size());
    }

    private void addMessage(String who, String text) {
        boolean user = who.equals("user");
        chatHistory.add((user ? "YOU" : "NEXUS AI") + "  •  " + now() + "\n" + text);
        JPanel line = new JPanel(new BorderLayout()); line.setOpaque(false); line.setBorder(new EmptyBorder(5, 0, 9, 0));
        JPanel bubble = new JPanel(new BorderLayout(0, 6));
        bubble.setBackground(user ? new Color(39, 62, 105) : new Color(27, 36, 63));
        bubble.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(user ? new Color(66, 105, 164) : new Color(54, 72, 112), 1, true), new EmptyBorder(11, 14, 11, 14)));
        bubble.setMaximumSize(new Dimension(610, Integer.MAX_VALUE));
        JLabel whoLabel = new JLabel(user ? "YOU  •  " + now() : "✦  NEXUS AI  •  " + now());
        whoLabel.setFont(new Font("SansSerif", Font.BOLD, 9)); whoLabel.setForeground(user ? new Color(160, 205, 255) : CYAN);
        JTextArea body = new JTextArea(text); body.setWrapStyleWord(true); body.setLineWrap(true); body.setColumns(58); body.setEditable(false);
        body.setOpaque(false); body.setForeground(new Color(226, 234, 248)); body.setFont(new Font("SansSerif", Font.PLAIN, 12));
        body.setBorder(null); body.setFocusable(false);
        bubble.add(whoLabel, BorderLayout.NORTH); bubble.add(body, BorderLayout.CENTER);
        bubble.setPreferredSize(new Dimension(Math.min(600, Math.max(260, getWidth() - 410)), body.getPreferredSize().height + 48));
        if (user) { line.add(bubble, BorderLayout.EAST); }
        else { line.add(bubble, BorderLayout.WEST); }
        messages.add(line); messages.add(Box.createVerticalStrut(2));
        messages.revalidate(); messages.repaint();
        SwingUtilities.invokeLater(() -> chatScroll.getVerticalScrollBar().setValue(chatScroll.getVerticalScrollBar().getMaximum()));
    }

    private void showKnowledgeBase() {
        StringBuilder content = new StringBuilder();
        content.append("NEXUS AI  •  KNOWLEDGE BASE\n");
        content.append("Stored question-and-answer entries: ").append(knowledge.size()).append("\n");
        content.append("────────────────────────────────────────\n\n");
        int number = 1;
        for (Map.Entry<String, String> entry : knowledge.entrySet()) {
            content.append(number++).append(". ").append(entry.getKey()).append("\n");
            content.append("   ").append(entry.getValue()).append("\n\n");
        }
        JTextArea area = new JTextArea(content.toString());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBackground(PANEL);
        area.setForeground(new Color(226, 234, 248));
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(680, 480));
        scroll.getViewport().setBackground(PANEL);
        JOptionPane.showMessageDialog(this, scroll, "NEXUS AI • Knowledge Base", JOptionPane.PLAIN_MESSAGE);
    }

    private void showChatHistory() {
        StringBuilder content = new StringBuilder();
        content.append("NEXUS AI  •  CHAT HISTORY\n");
        content.append("Messages in this session: ").append(chatHistory.size()).append("\n");
        content.append("────────────────────────────────────────\n\n");
        if (chatHistory.isEmpty()) {
            content.append("No messages yet. Start a conversation with Nexus AI!");
        } else {
            for (int i = 0; i < chatHistory.size(); i++) {
                content.append(i + 1).append(". ").append(chatHistory.get(i)).append("\n\n");
            }
        }
        JTextArea area = new JTextArea(content.toString());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setBackground(PANEL);
        area.setForeground(new Color(226, 234, 248));
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(680, 480));
        scroll.getViewport().setBackground(PANEL);
        JOptionPane.showMessageDialog(this, scroll, "NEXUS AI • Chat History", JOptionPane.PLAIN_MESSAGE);
    }

    private String now() { return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")); }

    private void startClock() {
        javax.swing.Timer t = new javax.swing.Timer(1000, e -> clock.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        t.start(); clock.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private static class OrbPanel extends JPanel {
        private final java.util.List<Particle> particles = new ArrayList<>();
        private double phase = 0;
        private final Random random = new Random();
        OrbPanel() {
            for (int i = 0; i < 34; i++) particles.add(new Particle(random.nextDouble(), random.nextDouble(), 1 + random.nextDouble() * 2, random.nextDouble() * 2 - 1));
            javax.swing.Timer animation = new javax.swing.Timer(35, e -> { phase += 0.045; for (Particle p : particles) { p.y -= 0.0025 * p.speed; if (p.y < 0) { p.y = 1; p.x = random.nextDouble(); } } repaint(); });
            animation.start();
        }
        @Override protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(); double cx = w / 2.0, cy = h / 2.0;
            for (Particle p : particles) {
                float alpha = (float)(0.2 + 0.55 * (0.5 + 0.5 * Math.sin(phase + p.x * 8)));
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g.setColor(p.speed > 0 ? CYAN : PURPLE);
                g.fill(new Ellipse2D.Double(p.x * w, p.y * h, p.size, p.size));
            }
            g.setComposite(AlphaComposite.SrcOver);
            double pulse = 1 + 0.045 * Math.sin(phase * 1.7);
            int d = (int)(58 * pulse);
            for (int i = 5; i >= 1; i--) {
                g.setColor(new Color(80, 140, 255, 10 + i * 5));
                int size = d + i * 13;
                g.fill(new Ellipse2D.Double(cx - size / 2.0, cy - size / 2.0, size, size));
            }
            g.setPaint(new GradientPaint((float)(cx - d / 2), (float)(cy - d / 2), new Color(57, 228, 246), (float)(cx + d / 2), (float)(cy + d / 2), new Color(126, 77, 255)));
            g.fill(new Ellipse2D.Double(cx - d / 2.0, cy - d / 2.0, d, d));
            g.setColor(new Color(255, 255, 255, 175));
            g.fill(new Ellipse2D.Double(cx - d * .22, cy - d * .28, d * .23, d * .14));
            g.setColor(new Color(220, 250, 255)); g.setFont(new Font("SansSerif", Font.BOLD, 18));
            FontMetrics fm = g.getFontMetrics(); g.drawString("✦", (float)(cx - fm.stringWidth("✦") / 2.0), (float)(cy + 6));
            g.dispose();
        }
        private static class Particle { double x, y, size, speed; Particle(double x, double y, double size, double speed) { this.x=x; this.y=y; this.size=size; this.speed=speed; } }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { }
            new AIChatbot().setVisible(true);
        });
    }
}