# PEIFOA 2026 Tackle Penalty Tracker

Sample cis2232 / cis2250 project
Development Team

* **Business Client:** Jake Henderson
* **Lead Developer:** Sean
* **Project Manager / QA:** Jonathan

---

## Description

The Prince Edward Island Football Officials Association (PEIFOA) oversees officiating for amateur tackle football across the province. Every week, officials refereeing these island matchups submit paper or digital penalty sheets documenting every flag thrown during a game. Currently, these sheets are compiled and emailed to the association at the end of each week.

While this data is invaluable, managing it through static emails makes it incredibly difficult to analyze trends, track team discipline, or evaluate officiating consistency. To modernize this process, this project introduces a centralized Penalty Tracker Application designed to streamline data entry and transform raw game summaries into actionable insights.

Whenever a user inputs data from a weekly penalty sheet, the application captures vital context, including the game’s date, participating teams, age division, the specific infraction, and the official who made the call. A key innovation of this tracker is the automated Impact Score calculated field. By evaluating the severity of a penalty against the quarter in which it occurred, the app mathematically weighs each infraction. A minor offside in the first quarter yields a low score, whereas a safety-related personal foul in the fourth quarter generates a high impact rating.

Ultimately, this project replaces a cumbersome email trail with a dynamic database. It provides PEIFOA with a clear, data-driven window into PEI football, highlighting high-leverage game moments, tracking referee workload, and identifying safety trends to improve the game for players and officials alike.

---

## Color

* **Main Color:** Black
* **Secondary Color:** White / Gray (Standard UI Contrast)

---

## Required Fields

| Field Name | Data Type | Description |
| --- | --- | --- |
| `id` | `int` | Unique identifier for database table |
| `homeTeam` | `String` | Name of the home team for the game |
| `awayTeam` | `String` | Name of the visiting team for the game |
| `date` | `String` | Date of game |
| `penalty` | `String` | Infraction type |
| `quarter` | `int` | Quarter infraction occurred |
| `penalizedTeam` | `String` | Name of offending team |
| `ageDivision` | `String` | Age division of game |
| `referee` | `String` | Name of official who flagged the infraction |
| `impactScore` | `Double` | Calculated impact penalty had |

---

## Calculation

The calculation / processing needed when the user enters a new record involves determining the **Impact Score**. Certain penalties carry a higher base impact on the game, and the later the quarter in which the penalty occurs, the higher the weight it carries. The application uses the penalty type and the quarter to calculate and assign an overall impact score to that specific infraction before writing the record to the database.

---

## Report Details

To be determined in future sprint
