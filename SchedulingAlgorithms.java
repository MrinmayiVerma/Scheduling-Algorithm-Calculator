import java.util.*;

class Process {
    int id, arrivalTime, burstTime, remainingTime, finishTime, turnaroundTime, waitingTime, priority;

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}

public class SchedulingAlgorithms {
    List<Process> processes = new ArrayList<>();

    public void inputProcesses(String input, String algorithm) {
        processes.clear();  // Clear previous data
        String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(",");
            int at = Integer.parseInt(parts[0].trim());
            int bt = Integer.parseInt(parts[1].trim());
            int pr = (algorithm.contains("Priority")) ? Integer.parseInt(parts[2].trim()) : 0;
            processes.add(new Process(i + 1, at, bt, pr));
        }
    }

    public String getResults(String algorithm) {
        switch (algorithm) {
            case "FCFS":
                FCFS();
                break;
            case "SJF":
                SJF();
                break;
            case "SRTF":
                SRTF();
                break;
            case "Priority (Preemptive)":
                PriorityPreemptive();
                break;
            case "Priority (Non-Preemptive)":
                PriorityNonPreemptive();
                break;
            default:
                break;
        }

        return generateResults();
    }

    public void RoundRobin(int timeQuantum) {
        Queue<Process> queue = new LinkedList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0, completed = 0, n = processes.size();
        int index = 0;

        while (completed < n) {
            while (index < n && processes.get(index).arrivalTime <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }
            if (!queue.isEmpty()) {
                Process current = queue.poll();
                int execTime = Math.min(current.remainingTime, timeQuantum);
                currentTime += execTime;
                current.remainingTime -= execTime;

                if (current.remainingTime == 0) {
                    current.finishTime = currentTime;
                    current.turnaroundTime = current.finishTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                    completed++;
                } else {
                    queue.add(current);
                }
            } else {
                currentTime++;
            }
        }
    }

    private String generateResults() {
        StringBuilder result = new StringBuilder();
        result.append("Job\tAT\tBT\tFT\tTAT\tWT\n");
        double totalTAT = 0, totalWT = 0;
        for (Process p : processes) {
            totalTAT += p.turnaroundTime;
            totalWT += p.waitingTime;
            result.append(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.finishTime + "\t" + p.turnaroundTime + "\t" + p.waitingTime + "\n");
        }
        result.append("Average Turnaround Time: ").append(totalTAT / processes.size()).append("\n");
        result.append("Average Waiting Time: ").append(totalWT / processes.size()).append("\n");
        return result.toString();
    }

    // FCFS Implementation
    public void FCFS() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        for (Process p : processes) {
            currentTime = Math.max(currentTime, p.arrivalTime);
            p.finishTime = currentTime + p.burstTime;
            p.turnaroundTime = p.finishTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime = p.finishTime;
        }
    }

    // SJF Implementation (Non-Preemptive)
    public void SJF() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0, completed = 0;
        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }
            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));
                Process currentProcess = readyQueue.remove(0);
                currentTime = Math.max(currentTime, currentProcess.arrivalTime);
                currentProcess.finishTime = currentTime + currentProcess.burstTime;
                currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                currentTime = currentProcess.finishTime;
                completed++;
            } else {
                currentTime++;
            }
        }
    }

    // SRTF (Shortest Remaining Time First)
    public void SRTF() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0, completed = 0, n = processes.size();
        Process currentProcess = null;
        while (completed < n) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (currentProcess == null || p.remainingTime < currentProcess.remainingTime) {
                        currentProcess = p;
                    }
                }
            }
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime + 1;
                    currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completed++;
                    currentProcess = null;
                }
                currentTime++;
            } else {
                currentTime++;
            }
        }
    }

    // Priority Preemptive Scheduling
    public void PriorityPreemptive() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0, completed = 0, n = processes.size();
        Process currentProcess = null;

        while (completed < n) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (currentProcess == null || p.priority < currentProcess.priority) {
                        currentProcess = p;
                    }
                }
            }
            if (currentProcess != null) {
                currentProcess.remainingTime--;
                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime + 1;
                    currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completed++;
                    currentProcess = null;
                }
                currentTime++;
            } else {
                currentTime++;
            }
        }
    }

    // Priority Non-Preemptive Scheduling
    public void PriorityNonPreemptive() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0, completed = 0;
        List<Process> readyQueue = new ArrayList<>();

        while (completed < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !readyQueue.contains(p) && p.remainingTime > 0) {
                    readyQueue.add(p);
                }
            }
            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(p -> p.priority));
                Process currentProcess = readyQueue.remove(0);
                currentTime = Math.max(currentTime, currentProcess.arrivalTime);
                currentProcess.finishTime = currentTime + currentProcess.burstTime;
                currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                currentTime = currentProcess.finishTime;
                completed++;
            } else {
                currentTime++;
            }
        }
    }
}
