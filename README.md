## PEIFOA 2026 Tackle Penalty Tracker

Create an application that can be used by the Prince Edward Island Football Officials Association (PEIFOA) to track and analyze football penalty statistics across amateur tackle football games in the province.

Requirements & Functional Specifications:
This application will need to track the following fields:

* Game Date (Date of the game)
* Home Team (Name of the home team)
* Away Team (Name of the visiting team)
* Age Division (Age group/division of the game)
* Penalized Team (Name of the offending team)
* Penalty Type (Type of infraction called)
* Quarter (Quarter in which the penalty occurred)
* Referee (Name of the official who flagged the infraction)
* Impact Score (Calculated weight/severity of the penalty)

Application Logic & Calculations:

* Prompt the user to enter the game context (teams, date, age division) along with the penalty details, including the specific infraction, quarter, penalized team, and referee.
* Calculate the Impact Score automatically based on the penalty severity and timing:
Certain penalties have a higher impact on the game, and penalties occurring later in the game carry more weight (e.g., a minor offside in the 1st quarter generates a lower score, while a safety-related personal foul in the 4th quarter yields a high impact rating).
* Store each penalty entry to build a centralized penalty log, replacing static paper/email penalty sheets.
* Display, filter, and sort penalty data to allow PEIFOA to track team discipline, high-leverage game moments, official workload, and player safety trends across the province.

---

Project Metadata & Group Roles:

* Course: CIS2232 (Web Application) / CIS2250 (Mobile Application)
* BA / Business Client: Jake Henderson
* Developer: Sean
* Project Manager / QA: Jonathan
* Theme / Base Color: Black
