import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomInputDialog extends JDialog {
    private String inputText = null;
    private JTextField textField;
    private boolean confirmed = false;

    public CustomInputDialog(Frame owner, String title, String initialText) {
        super(owner, title, true);

        setUndecorated(true);
        setSize(350, 120);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Bakgrunnsfarge som matcher todo-lista
        getContentPane().setBackground(new Color(26, 28, 35));

        // Title bar panel (kan også lage en custom tittelbar hvis du ønsker)
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(20, 22, 27));
        titleBar.setPreferredSize(new Dimension(0, 30));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setForeground(new Color(248, 248, 242));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        //JButton closeButton = getJButton();

        titleBar.add(titleLabel, BorderLayout.CENTER);
        //titleBar.add(closeButton, BorderLayout.EAST);

        // Gjør tittelbaren draggable akkurat som hovedvinduet
        final Point[] mouseDownCompCoords = {null};
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords[0] = null;
            }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });

        add(titleBar, BorderLayout.NORTH);

        // Innholdspanel med inputfelt
        JPanel contentPanel = new JPanel(new BorderLayout(5,5));
        contentPanel.setBackground(new Color(32, 35, 42));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textField = new JTextField(initialText);
        textField.setBackground(new Color(40, 44, 52));
        textField.setForeground(new Color(248, 248, 242));
        textField.setCaretColor(new Color(97, 175, 254));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        contentPanel.add(textField, BorderLayout.CENTER);

        // Knapper panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(32, 35, 42));

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(97, 175, 254));
        okButton.setForeground(Color.WHITE);
        okButton.setBorderPainted(false);
        okButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Avbryt");
        cancelButton.setBackground(new Color(224, 108, 117));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);

        // Handling OK-knapp
        okButton.addActionListener(e -> {
            inputText = textField.getText().trim();
            if (!inputText.isEmpty()) {
                confirmed = true;
                dispose();
            } else {
                // Valgfritt: Vis feilmelding hvis tom input
                JOptionPane.showMessageDialog(this, "Tekst kan ikke være tom.", "Feil", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Handling Avbryt-knapp
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        // Lytt på Enter og Escape
        textField.addActionListener(e -> okButton.doClick());
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private static JButton getJButton() {
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        closeButton.setBackground(new Color(224, 108, 117));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(59, 30));
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return closeButton;
    }

    public String getInputText() {
        return confirmed ? inputText : null;
    }

    public static String showDialog(Component parent, String title, String initialText) {
        Frame owner = JOptionPane.getFrameForComponent(parent);
        CustomInputDialog dialog = new CustomInputDialog(owner, title, initialText);
        dialog.setVisible(true);
        return dialog.getInputText();
    }
}
