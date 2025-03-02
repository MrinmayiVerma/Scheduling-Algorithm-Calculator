import javax.swing.*;
import java.awt.event.*;

public class SchedulerUI extends JFrame {
    private JButton calculateButton;
    private JComboBox<String> algorithmBox;
    private JTextArea inputArea, resultArea;
    private JTextField timeQuantumField;
    private SchedulingAlgorithms scheduler;

    public SchedulerUI() {
        setTitle("CPU Scheduling Algorithms");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        scheduler = new SchedulingAlgorithms();  // Backend instance

        // Dropdown for algorithms
        String[] algorithms = {"FCFS", "SJF", "SRTF", "Round Robin", "Priority (Preemptive)", "Priority (Non-Preemptive)"};
        algorithmBox = new JComboBox<>(algorithms);

        // Input field for process data
        JLabel inputLabel = new JLabel("Enter processes (Arrival Time, Burst Time, Priority [if applicable]):");
        inputArea = new JTextArea(10, 40);
        JScrollPane inputScroll = new JScrollPane(inputArea);

        // Input field for time quantum (only used for Round Robin)
        JLabel timeQuantumLabel = new JLabel("Time Quantum (for Round Robin only): ");
        timeQuantumField = new JTextField(5);
        timeQuantumField.setEnabled(false);  // Disabled by default, only enabled for Round Robin

        // Result area to display scheduling output
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        // Calculate button
        calculateButton = new JButton("Calculate");

        // Panel to hold components
        JPanel panel = new JPanel();
        panel.add(algorithmBox);
        panel.add(inputLabel);
        panel.add(inputScroll);
        panel.add(timeQuantumLabel);
        panel.add(timeQuantumField);
        panel.add(calculateButton);
        panel.add(resultScroll);

        // Add panel to frame
        add(panel);

        // Add event listener for algorithm selection
        algorithmBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
                if (selectedAlgorithm.equals("Round Robin")) {
                    timeQuantumField.setEnabled(true);
                } else {
                    timeQuantumField.setEnabled(false);
                }
            }
        });

        // Add event listener for calculate button
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
                String input = inputArea.getText().trim();

                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter process details.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Send input to backend
                    scheduler.inputProcesses(input, selectedAlgorithm);

                    if (selectedAlgorithm.equals("Round Robin")) {
                        // Handle time quantum for Round Robin
                        int timeQuantum = Integer.parseInt(timeQuantumField.getText().trim());
                        if (timeQuantum <= 0) {
                            throw new NumberFormatException();
                        }
                        scheduler.RoundRobin(timeQuantum);
                    }

                    // Get and display results from backend
                    String result = scheduler.getResults(selectedAlgorithm);
                    resultArea.setText(result);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for process data or time quantum.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SchedulerUI().setVisible(true);
            }
        });
    }
}
