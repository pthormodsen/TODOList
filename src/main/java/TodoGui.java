import javax.swing.*;
import java.awt.*;

public class TodoGui extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private TodoList todoList;

    public TodoGui(){
        todoList = new TodoList();

        setTitle("TODO List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Legg til");
        JButton doneButton = new JButton("Fullfør");
        JButton removeButton = new JButton("Slett");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInput, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(doneButton);
        buttonPanel.add(removeButton);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);


        //Legger til tasks
        addButton.addActionListener(e -> {
            String desc = taskInput.getText().trim();
            if(!desc.isEmpty()){
                todoList.addTask(desc);
                updateTaskList();
                taskInput.setText("");
            }
        });

        //Marker task som fullført
        doneButton.addActionListener(e -> {
           int selectedIndex = taskList.getSelectedIndex();
           if(selectedIndex != -1){
               todoList.markTaskDone(selectedIndex);
               updateTaskList();
           }
        });

        //Sletter oppgave
        removeButton.addActionListener(e -> {
           int selectedIndex = taskList.getSelectedIndex();
           if(selectedIndex != -1){
               todoList.removeTask(selectedIndex);
               updateTaskList();
           }
        });
    }

    private void updateTaskList() {
        listModel.clear();
        for(Task task : todoList.getTasks()){
            listModel.addElement(task.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           new TodoGui().setVisible(true);
        });
    }
}
