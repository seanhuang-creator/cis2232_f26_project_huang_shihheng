# PEIFOA 2026 Tackle Penalty Tracker

An app for PEIFOA officials to track tackle penalties and view team discipline.

For each penalty, it will keep track of which teams played, which team was penalized, which referee made the call, and how much impact the penalty had.

Officials will be able to see team discipline based on total impact score, and run reports by referee / team or by impact score range.

## Development Team

BA / Business Client: Jake Henderson

Developer: Sean Huang

Project Manager / QA: Jonathan

## Fields

| Name | Data Type | Description |
| --- | --- | --- |
| id | int | Unique id for the penalty. |
| homeTeam | String | Name of the home team. |
| awayTeam | String | Name of the away team. |
| date | String | Game date (yyyy-MM-dd). |
| penalty | String | Infraction type. |
| quarter | int | Quarter (1-4). |
| penalizedTeam | String | Team that committed the penalty. |
| offendingPlayer | int | Player number who committed the penalty. |
| ageDivision | String | Age division (e.g. AFL, U18, U15). |
| referee | String | Official who made the call. |
| impactScore | double | Calculated impact score. |

## Calculation

Impact Score = Severity x Quarter Multiplier

### Severity

| Infraction Category | Base Score | Example Penalties |
| --- | --- | --- |
| Mouth Guard Warning | 0.5 | Mouth Guard Warning |
| Minor | 1.0 | Offside, Procedure, No Yards, Time Count Violation, Illegal Formation, Illegal Kick Out of Bounds, Intentional Grounding |
| Moderate | 2.0 | Holding, Illegal Use of Hands, Illegal Block in the Back, Tandem Buck Block, Objectionable Conduct, Default |
| Pass Interference | 3.0 | Pass Interference |
| Safety Related | 5.0 | Unnecessary Roughness, Personal Foul |

### Quarter Multiplier

| Quarter | Multiplier |
| --- | --- |
| Q1 | 1.0x |
| Q2 | 1.25x |
| Q3 | 1.5x |
| Q4 | 2.0x |

### Examples

| Penalty | Quarter | Calculation | Impact Score |
| --- | --- | --- | --- |
| Offside | Q1 | 1.0 x 1.0 | 1.0 |
| Holding | Q2 | 2.0 x 1.25 | 2.5 |
| Pass Interference | Q3 | 3.0 x 1.5 | 4.5 |
| Personal Foul | Q4 | 5.0 x 2.0 | 10.0 |

## Report

### Home Page

On the home page, teams are displayed in order of total impact score (sorted in descending order).

A chart displays total impact score by penalized team. A time range filter is available: All, 1 Year, 6 Months, 3 Months, 1 Month.

### Report 1: Referee / Team Report

Select a name from the dropdown and get a list of all penalties for that referee or team.

The dropdown lists referees A-Z first, then teams A-Z.

Input: One referee name or team name from the dropdown.

Search: Partial keyword matching with `LIKE %name%`. The search checks referee, homeTeam, awayTeam, and penalizedTeam.

Processing: `TacklePenaltyBO.processSelectAllByRefereeTeamName()` -> `TacklePenaltyDAO` using JDBC (DriverManager, PreparedStatement, ResultSet). No JPA for this report.

SQL: `SELECT * FROM TacklePenalty WHERE referee LIKE '%name%' OR homeTeam LIKE '%name%' OR awayTeam LIKE '%name%' OR penalizedTeam LIKE '%name%'`

Display: Table with all matching penalty records + KPI (Total Count, Average Impact, Max Impact) + 6 Charts (Penalty Type Distribution, Avg Impact by Type, Quarter Distribution, Penalized Team Count, Date Trend, Age Division Analysis).

File: Auto export to `c:\cis2232\PenaltyByReferee_<name>_<yyyyMMddHHmmss>.txt`. The folder is created with `mkdirs()` if it does not exist. One file per report run.

### Report 2: Impact Score Range Report

Enter a minimum and maximum impact score and get a list of all penalties within that range.

Input: Minimum impact score and maximum impact score (e.g. 3.0 to 4.0).

Search: `WHERE impactScore BETWEEN min AND max`.

Processing: `TacklePenaltyDAO.selectAllByImpactScoreMinMax()` using JDBC. No JPA for this report.

Display: Table with all penalties within the score range + KPI (Total Count, Average Impact, Max Impact) + Same 6 Charts as Report 1.

File: Auto export to `c:\cis2232\PenaltyByScore_<min>-<max>_<yyyyMMddHHmmss>.txt`. The folder is created with `mkdirs()` if it does not exist. One file per report run.

### File Format (Report 1 and Report 2)

Each file contains all 11 fields for each record. Impact score is shown with 2 decimal places. There is a blank line between records.

## How to Run

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL

### Steps

1. Create the database and run the seed file to load penalty records.
2. Check `application.properties` for the database URL, username, and password.
3. Run the Spring Boot application.
4. Open the home page, add / view penalties, and run Report 1 or Report 2.

## Technologies Used

| Technology | Purpose |
| --- | --- |
| Java | Programming language |
| Spring Boot | Web application |
| MySQL | Database |
| JDBC / DAO | Data access for reports |
| Chart.js | Charts for reports and home page |
| Thymeleaf | Web pages |
| JUnit | Unit testing |
