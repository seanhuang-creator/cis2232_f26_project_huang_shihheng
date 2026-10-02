# PEIFOA 2026 Tackle Penalty Tracker

**Course:** CIS 2232 - Object-Orient Programming I

## Development Team

| Role | Name |
|------|------|
| Business Client | Jake Henderson |
| Lead Developer | Sean Huang |
| Project Manager / QA | Jonathan |

---

## Description

The Prince Edward Island Football Officials Association (PEIFOA) oversees officiating for amateur tackle football across the province. Every week, officials refereeing these island matchups submit penalty sheets documenting every flag thrown during a game.

While this data is invaluable, managing it through static emails makes it difficult to analyze trends, track team discipline, or evaluate officiating consistency. This project introduces a **centralized Penalty Tracker Application** designed to streamline data entry and transform raw game summaries into actionable insights.

### Key Features

- **Automated Data Entry:** Captures vital game context including date, teams, age division, infraction type, and the official who made the call
- **Impact Score Calculation:** Automatically calculates a weighted impact score based on penalty severity and game quarter
- **JSON Storage:** Persist penalty records to local JSON files for easy data management
- **History Integration:** Loads and displays historical penalty data from real PEIFOA game records

---

## Impact Score Calculation Formula

The application calculates an **Impact Score** for each penalty based on two factors:

### 1. Penalty Severity (Base Score)

| Infraction Category | Base Score | Example Penalties |
|---------------------|------------|-------------------|
| Mouth Guard Warning | 0.5 | Mouth Guard Warning, Equipment Warning |
| Minor | 1.0 | Offside, Procedure, No Yards, Time Count Violation, Illegal Formation, Illegal Equipment, Illegal Kick Out of Bounds, Intentional Grounding |
| Moderate | 2.0 | Holding, Illegal Use of Hands, Illegal Block in the Back, Tandem Buck Block, Objectionable Conduct, Default |
| Pass Interference | 3.0 | Pass Interference |
| Safety Related | 5.0 | Unnecessary Roughness, Personal Foul |

### 2. Quarter Multiplier

The later in the game a penalty occurs, the higher its potential impact on the outcome:

| Quarter | Multiplier |
|---------|------------|
| Q1 | 1.0x |
| Q2 | 1.25x |
| Q3 | 1.5x |
| Q4 | 2.0x |

### Formula

```
Impact Score = Base Score × Quarter Multiplier
```

### Examples

| Penalty | Quarter | Calculation | Impact Score |
|---------|---------|-------------|--------------|
| Offside | Q1 | 1.0 × 1.0 | 1.0 |
| Holding | Q2 | 2.0 × 1.25 | 2.5 |
| Pass Interference | Q3 | 3.0 × 1.5 | 4.5 |
| Personal Foul | Q4 | 5.0 × 2.0 | 10.0 |

---

## Project Structure

```
cis2232_assignment2_penalty_tracker_huang_shihheng/
├── src/
│   ├── main/
│   │   ├── java/ca/hccis/penaltytracker/
│   │   │   ├── Controller.java          # Main entry point, menu navigation
│   │   │   ├── bo/
│   │   │   │   └── PenaltyTrackerBO.java # Business logic, calculate() method
│   │   │   ├── entity/
│   │   │   │   └── PenaltyTracker.java   # Data model
│   │   │   └── util/
│   │   │       └── CisUtility.java       # Utility helper methods
│   │   └── resources/
│   │       └── penaltiesHistory.json     # Historical penalty data
│   └── test/
│       └── java/ca/hccis/penaltytracker/
│           └── bo/
│               └── PenaltyTrackerBOTest.java # JUnit test suite
└── pom.xml                               # Maven build configuration
```

---

## Requirements Implemented

### Requirement 1: Test Driven Development (TDD)

- Developed using the **Test Driven Development** approach (Red-Green-Refactor)
- Created **3 JUnit tests** for the `calculate()` method in `PenaltyTrackerBO`
- Each test includes Javadoc notes indicating TDD methodology
- Utilizes multiple assertion methods: `assertEquals`, `assertTrue`, `assertNotNull`

### Requirement 2: AI for Test Generation

- AI-generated test suite provides comprehensive unit testing coverage
- Tests include edge cases: null handling, unknown penalties, invalid quarters
- Data-driven integration tests using real `penaltiesHistory.json` dataset

---

## How to Run

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven 3.6+

### Build and Run

```bash
# Compile the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="ca.hccis.penaltytracker.Controller"

# Run tests
mvn test
```

### Usage

Upon running, the application presents a simple menu:

```
A) Add
V) View
X) eXit
Option:
```

- **A)** Add a new penalty record (auto-assigns ID, calculates Impact Score)
- **V)** View all penalty records (historical + user-added)
- **X)** Exit the application

---

## Unit Test Summary

### TDD Tests (Requirement 1)

| Test Name | Description | Expected Result |
|-----------|-------------|-----------------|
| `testCalculateMinorPenaltyInQ1` | Offside penalty in Q1 | 1.0 × 1.0 = 1.0 |
| `testCalculateSeverePenaltyInQ4` | Personal Foul in Q4 | 5.0 × 2.0 = 10.0 |
| `testCalculateUpdatesEntityImpactScore` | Holding in Q2 | 2.0 × 1.25 = 2.5 |

### AI-Generated Tests (Requirement 2)

| Test Name | Description | Expected Result |
|-----------|-------------|-----------------|
| `testCalculateNullEntity` | Null entity handling | Returns 0.0 |
| `testCalculateUnknownPenalty` | Unrecognized penalty type | Default 2.0 × multiplier |
| `testCalculateInvalidQuarter` | Invalid quarter value | Defaults to 1.0x multiplier |

### Data Integration Test

| Test Name | Description |
|-----------|-------------|
| `testCalculateAllHistoryRecordsFromJson` | Validates calculation against all 78 real PEIFOA penalty records |

---

## Data Fields

| Field | Type | Description |
|-------|------|-------------|
| `id` | `int` | Unique identifier (auto-assigned) |
| `homeTeam` | `String` | Name of the home team |
| `awayTeam` | `String` | Name of the away team |
| `date` | `String` | Game date (yyyy-MM-dd) |
| `penalty` | `String` | Infraction type |
| `quarter` | `int` | Quarter (1-4) |
| `penalizedTeam` | `String` | Team that committed the penalty |
| `ageDivision` | `String` | Age division (e.g., AFL, U18, U15) |
| `referee` | `String` | Official who made the call |
| `impactScore` | `double` | Calculated impact score |

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 8+ | Programming language |
| Maven | 3.6+ | Build automation |
| JUnit 5 | 5.10.0 | Unit testing framework |
| Gson | 2.8.5 | JSON serialization/deserialization |

---

## Programming Standards

This project follows strict Java programming standards:

- **Naming Conventions:** CamelCase for classes, camelCase for methods/variables
- **Commenting:** Comprehensive Javadoc for all public classes and methods
- **Formatting:** Consistent indentation (4 spaces), proper brace placement
- **Encapsulation:** Private fields with public getters/setters
- **Error Handling:** Graceful handling of null values and invalid inputs

---

## Future Enhancements

- [ ] GUI interface (JavaFX)
- [ ] Database integration (SQLite/MySQL)
- [ ] Advanced analytics and reporting
- [ ] Export to CSV/PDF
- [ ] Team discipline leaderboard
- [ ] Referee workload dashboard

---

## Report Details (Sprint 2)

### Referee Report
Enter a referee name and the report will return any penalty records called by that official (matching or containing the entered referee name).

---

## License

Sample project for CIS 2232 educational purposes.

---

## Acknowledgments

- **Course Instructor:** CIS 2232 Teaching Team
- **Business Client:** Jake Henderson
- **Official Dataset:** Prince Edward Island Football Officials Association (PEIFOA)

---
