import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaskCellRenderer extends JPanel implements ListCellRenderer<Task> {
    private JLabel label;
    private JButton editButton;

    public TaskCellRenderer() {
        setLayout(new BorderLayout(5, 5));
        label = new JLabel();
        editButton = new JButton("Endre");
        editButton.setFocusable(false);
        editButton.setPreferredSize(new Dimension(70, 25));
        add(label, BorderLayout.CENTER);
        add(editButton, BorderLayout.EAST);

        setBorder(new EmptyBorder(0, 12, 0, 5));
    }

    public JButton getEditButton() {
        return editButton;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        label.setText(value.toString());

        // Hent hoveredIndex fra TodoGui som ligger over JList
        int hoveredIndex = -1;
        Window window = SwingUtilities.getWindowAncestor(list);
        if (window instanceof TodoGui) {
            hoveredIndex = ((TodoGui) window).getHoveredIndex();
        }

        // Vis knapp kun på hovered rad eller valgt rad
        boolean anySelected = list.getSelectedIndex() != -1;
        if (anySelected) {
            editButton.setVisible(isSelected);
        } else {
            editButton.setVisible(index == hoveredIndex);
        }

        // Fargevalg ved seleksjon
        if (isSelected) {
            setBackground(new Color(135, 206, 250));  // Lys himmelblå
            label.setForeground(Color.BLACK);        // Mørk tekst på lys bakgrunn
        } else {
            setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }

        // Sett fast høyde på cellen for å unngå dynamisk høydeendring
        // Bredde settes til listens bredde for riktig layout
        setPreferredSize(new Dimension(list.getWidth(), 40));

        return this;
    }
}
