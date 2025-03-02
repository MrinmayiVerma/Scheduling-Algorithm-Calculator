# Scheduling-Algorithm-Calculator
# Description:
This project presents a Java Spring-based scheduling application that enables users to analyze various CPU scheduling algorithms, including FCFS, SJF, SRTF, Round Robin, and both preemptive and non-preemptive Priority scheduling. With a user-friendly interface, users can input process arrival time, burst time, and priority as needed. The application calculates and displays key metrics, such as average turnaround time and average waiting time, facilitating a practical understanding of CPU scheduling dynamics. This tool serves as an educational resource for students and professionals seeking to optimize process management and system efficiency.
# LITERATURE OVERVIEW:
CPU scheduling is a crucial field in optimizing process management within operating systems through various algorithms. First-Come, First-Served (FCFS) is a straightforward approach, processing jobs by arrival order but often experiences inefficiencies, such as the "convoy effect," where short processes are delayed by longer ones. Shortest Job First (SJF) reduces average waiting time by prioritizing shorter burst times, though it risks starving longer processes. Shortest Remaining Time First (SRTF), a preemptive variant, improves responsiveness by switching to new processes with shorter remaining times but adds scheduling overhead. Round Robin (RR) allocates time slices to each process for fairness, but performance depends on the time quantum. Priority scheduling boosts critical task responsiveness but may starve lower-priority tasks. Comparative analyses reveal varying effectiveness of these algorithms under different workloads, and simulation tools provide hands-on experience, enriching educational insights and decision-making in OS design. This proposed simulator aims to support an interactive understanding of scheduling techniques, enhancing practical and theoretical knowledge.

# SYSTEM ANALYSIS:
  The scheduling program facilitates CPU scheduling algorithm analysis through an intuitive GUI where users can select algorithms, input parameters, and view results. The backend implements logic for FCFS, SJF, SRTF, Round Robin, and Priority scheduling (both preemptive and non-preemptive), calculating metrics like average turnaround and waiting times. Data processing is efficient, providing real-time feedback, and output is displayed clearly to support user comprehension. Comparative analysis features allow users to simulate various scenarios and understand trade-offs. Scalable and extensible, the program serves as both a practical and educational tool for studying CPU scheduling algorithms.


# SYSTEM DESIGN:
![FlowChart](https://github.com/MrinmayiVerma/Scheduling-Algorithm-Calculator/blob/main/JAVA%20FLOWCHART.png)
![Use Case Diagram](https://github.com/MrinmayiVerma/Scheduling-Algorithm-Calculator/blob/main/USE%20CASE%20DIAGRAM.png)

# Target Audience:

- Students in high school or college
- Teachers or educators 


## Technologies Used

-Java

