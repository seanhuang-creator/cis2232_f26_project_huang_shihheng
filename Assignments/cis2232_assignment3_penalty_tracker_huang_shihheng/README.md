# Assignment #3 - Multi-Threading Penalty Tracker
### PEIFOA 2026 Tackle Penalty Tracker

* **Course:** CIS 2232 - Object-Oriented Programming I
* **Instructor:** BJ MacLean
* **Author:** Sean Huang (Shih Heng Huang)
* **Date:** October 10th, 2026
* **Topic:** Multi-Threading Implementation for Penalty Tracking

---

## 📋 Assignment Overview

This assignment extends Assignment #1 and #2 to apply **multi-threading** in a Java application. The application must run in **two separate threads** with identical functionality, demonstrating thread safety and shared data accumulation.

> **Core Requirement:** One thread works completely in the console, the other completely using `JOptionPane`. Both share the same accumulated data.

---

## 🏗️ Project Structure

```text
cis2232_assignment3_penalty_tracker_huang_shihheng/
├── data/
│   └── data_huang_shihheng.json # Shared accumulator file (both threads)
├── src/main/java/ca/hccis/penaltytracker/
│   ├── Controller.java          # Main entry point - launches 2 threads
│   ├── entity/
│   │   └── PenaltyTracker.java  # Entity with static accumulator
│   ├── bo/
│   │   └── PenaltyTrackerBO.java# Business logic + calculate() from A2
│   └── threads/
│       ├── ConsoleRunner.java   # Thread 1: Console A/V/X menu
│       └── GuiRunner.java       # Thread 2: JOptionPane A/V/X menu
├── pom.xml
└── README.md
```

### Key Classes

- **Controller.java**: Creates `./data/` directory, ensures JSON file exists, starts `GuiThread` first (with 500ms delay) then `ConsoleThread`. Contains thread-safe `synchronized` methods `saveRecord()` and `getAllRecords()`.
- **ConsoleRunner**: Implements `Runnable`, uses `CisUtility` for console input. Menu: `A) Add, V) View, X) eXit`.
- **GuiRunner**: Implements `Runnable`, uses `JOptionPane.showInputDialog` and `showMessageDialog` for all interaction. Same A/V/X menu logic.
- **PenaltyTracker**: Entity with static attributes `totalRecords` and `totalImpactScore` updated via `synchronized` method. Shared by both threads.

---

## ⚙️ Features & Functionality

Same functionality as Assignment #1 + `calculate()` from Assignment #2, implemented in **both** threads:

- **A) Add**: Collects Penalty ID, Home/Away Team, Date, Infraction Type, Quarter, Penalized Team, Age Division, Referee. Calls `bo.calculate()` to compute Impact Score.
- **V) View**: Displays all records from **both threads** (file accumulation). Shows `Total records: X` and `Shared Accumulator: X records, Score=Y`.
- **X) eXit**: Thread independently exits with `Goodbye!` message. Other thread continues running.

### Thread Safety

- `Controller.saveRecord()` is `synchronized` - file append is thread-safe.
- `Controller.getAllRecords()` is `synchronized` - file read is thread-safe.
- `PenaltyTracker` static accumulator is updated in a `synchronized` method.

---

## ✅ Testing & Verification (Tested Oct 12, 2026)

All requirements verified:

- [x] **Relative Path `./data/`** - Works on any computer, not hard-coded `D:\cis2232\`. Verified in `Controller.java` line: `public static final String PATH = "./data/";`
- [x] **A/V/X in Both Threads** - Console thread uses `System.out.println` + `CisUtility`, GUI thread uses `JOptionPane` dialogs exclusively.
- [x] **Shared Accumulator File** - File `data_huang_shihheng.json` accumulates entries from both threads. Tested with 5 records:
    - Console: 101 (Offside Q1, Score 1.0), 102 (Mouth Guard Q2, Score 0.625), 103 (Holding Q3, Score 3.0)
    - GUI: 201 (Pass Interference Q4, Score 6.0), 202 (Unnecessary Roughness Q4, Score 10.0)
    - Both Views show: `Total records: 5 / Shared Accumulator: 5 records, Score=20.625`
- [x] **Independent Execution & Termination** - Tested: Console `X` -> `Console Thread Exiting - Goodbye!` while GUI menu still alive. GUI `X` -> `GUI Thread Exiting - Goodbye!` while console still waiting. Both `X` -> JVM exits with `exit code 0`.

### Sample Run

```text
Multi-threading Penalty Tracker - A3
File: ./data/data_huang_shihheng.json

GUI Thread Started - ID: 53
Console Thread Started - ID: 55

[Accumulator Updated - Shared by Both Threads]
Total Records (Console + GUI): 5
Total Impact Score (Console + GUI): 20.625
```

---

## 🚀 How to Run

1. Ensure Maven dependencies installed (`gson 2.8.5`)
2. Run `ca.hccis.penaltytracker.Controller`
3. Console menu appears in Run window, GUI menu appears as popup dialog
4. Test Add/View in both, then X to exit each thread independently

---

## 📝 Programming Standards

- English comments and Javadoc
- `synchronized` for shared resources
- Relative path for portability
- No `System.exit(0)` in individual threads - allows independent lifecycle
- Welcome dialog on startup to show GUI is alive

---
**Status: Completed & Tested**