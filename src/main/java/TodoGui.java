import javax.swing.*;
import java.awt.*;

public class TodoGui extends JFrame {
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private TodoList todoList;
    private TaskCellRenderer renderer;
    private int hoveredIndex = -1;  // Hvilken rad musa er over

    public TodoGui(){
        todoList = new TodoList();

        setTitle("TODO List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setUndecorated(true);  // Remove system title bar

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(20, 22, 27));  // Darker than main background
        titleBar.setPreferredSize(new Dimension(0, 35));

        JLabel titleLabel = new JLabel("TODO List", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(248, 248, 242));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton closeButton = getJButton();

        JPanel closePanel = new JPanel(new BorderLayout());
        closePanel.setOpaque(false);
        closePanel.add(closeButton, BorderLayout.CENTER);
        closePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        //Legg til et tomt panel i vest med samme bredde som closePanel for balansering
        JPanel leftSpacer = new JPanel();
        leftSpacer.setOpaque(false);
        leftSpacer.setPreferredSize(closePanel.getPreferredSize());

        titleBar.add(leftSpacer, BorderLayout.WEST);  // Tom panel til venstre
        titleBar.add(titleLabel, BorderLayout.CENTER); // Label i midten
        titleBar.add(closePanel, BorderLayout.EAST);   // Close-knapp til høyre

        // Make window draggable
        final Point[] mouseDownCompCoords = {null};
        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                mouseDownCompCoords[0] = null;
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });
        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });

        // Modern background color - deep dark blue-gray
        Color backgroundColor = new Color(26, 28, 35);
        getContentPane().setBackground(backgroundColor);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        renderer = new TaskCellRenderer();
        taskList.setCellRenderer(renderer);

        // Modern list colors
        taskList.setBackground(new Color(40, 44, 52));  // Slightly lighter surface
        taskList.setForeground(new Color(248, 248, 242));  // Off-white text
        taskList.setSelectionBackground(new Color(60, 64, 73));  // Darker selection
        taskList.setSelectionForeground(new Color(248, 248, 242));  // Same text color when selected

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Modern scroll pane background
        scrollPane.getViewport().setBackground(new Color(40, 44, 52));
        scrollPane.setBorder(null);  // Remove scroll pane border

        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Legg til");
        JButton doneButton = new JButton("Fullfør");
        JButton removeButton = new JButton("Slett");

        // Modern input field colors
        taskInput.setBackground(new Color(40, 44, 52));
        taskInput.setForeground(new Color(248, 248, 242));
        taskInput.setCaretColor(new Color(97, 175, 254));  // Modern blue caret
        taskInput.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));  // Remove default border

        // Modern button colors
        addButton.setBackground(new Color(97, 175, 254));  // Modern blue
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);  // Remove button border
        addButton.setFocusPainted(false);   // Remove focus border

        doneButton.setBackground(new Color(152, 195, 121));  // Soft green
        doneButton.setForeground(Color.WHITE);
        doneButton.setBorderPainted(false);  // Remove button border
        doneButton.setFocusPainted(false);   // Remove focus border

        removeButton.setBackground(new Color(224, 108, 117));  // Soft red
        removeButton.setForeground(Color.WHITE);
        removeButton.setBorderPainted(false);  // Remove button border
        removeButton.setFocusPainted(false);   // Remove focus border

        doneButton.setEnabled(false);
        removeButton.setEnabled(false);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInput, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(doneButton);
        buttonPanel.add(removeButton);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        // Modern panel colors - slightly different from main background
        inputPanel.setBackground(new Color(32, 35, 42));
        buttonPanel.setBackground(new Color(32, 35, 42));

        add(titleBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);


        // Legger til task
        addButton.addActionListener(e -> {
            String desc = taskInput.getText().trim();
            if (!desc.isEmpty()) {
                todoList.addTask(desc);
                updateTaskList();
                taskInput.setText("");
            }
        });

        //Check/uncheck
        doneButton.addActionListener(e -> {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                if (!selectedTask.isCompleted()) {
                    todoList.markTaskDoneById(selectedTask.getId());
                } else {
                    todoList.markTaskUndoneById(selectedTask.getId());
                }
                updateTaskList();
            }
        });

        // Slett task
        removeButton.addActionListener(e -> {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                todoList.removeTaskById(selectedTask.getId());
                updateTaskList();
            }
        });

        taskList.addListSelectionListener(e -> {
            boolean isSelected = taskList.getSelectedIndex() != -1;
            doneButton.setEnabled(isSelected);
            removeButton.setEnabled(isSelected);

            if (isSelected) {
                Task selectedTask = taskList.getSelectedValue();
                if (selectedTask.isCompleted()) {
                    doneButton.setText("Angre");
                } else {
                    doneButton.setText("Fullfør");
                }
            } else {
                doneButton.setText("Fullfør");
            }
        });

        updateTaskList();

        taskList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index != hoveredIndex) {
                    hoveredIndex = index;
                    taskList.repaint();
                }
            }
        });

        taskList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hoveredIndex = -1;
                taskList.repaint();
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Rectangle cellBounds = taskList.getCellBounds(index, index);
                    Point ptInCell = new Point(e.getX() - cellBounds.x, e.getY() - cellBounds.y);

                    Component rendererComponent = taskList.getCellRenderer()
                            .getListCellRendererComponent(taskList, listModel.get(index), index, false, false);
                    if (rendererComponent instanceof TaskCellRenderer tcr) {
                        JButton editBtn = tcr.getEditButton();
                        Rectangle btnBounds = editBtn.getBounds();

                        if (btnBounds.contains(ptInCell)) {
                            // Klikket på endre-knappen!
                            Task task = listModel.get(index);
                            String nyBeskrivelse = CustomInputDialog.showDialog(TodoGui.this, "Endre oppgave", task.getDescription());
                            if (nyBeskrivelse != null && !nyBeskrivelse.trim().isEmpty()) {
                                todoList.updateTaskDescription(task.getId(), nyBeskrivelse.trim());
                                updateTaskList();
                            }
                        }
                    }
                }
            }
        });

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

    private void updateTaskList() {
        listModel.clear();
        for (Task task : todoList.getTasks()) {
            listModel.addElement(task);
        }
        taskList.revalidate();
        taskList.repaint();

    }


    public int getHoveredIndex() {
        return hoveredIndex;
    }


    public static void main(String[] args) {
//        UIManager.put("ScrollBar.thumb", new Color(60, 64, 73));
//        UIManager.put("ScrollBar.track", new Color(40, 44, 52));
//        UIManager.put("ScrollBar.thumbHighlight", new Color(97, 175, 254));
//        UIManager.put("ScrollBar.thumbShadow", new Color(32, 35, 42));
//        UIManager.put("ScrollBar.trackHighlight", new Color(26, 28, 35));

        SwingUtilities.invokeLater(() -> new TodoGui().setVisible(true));
    }

}