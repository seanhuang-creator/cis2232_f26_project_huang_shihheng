# For localhost
DROP DATABASE IF EXISTS cis2232_tackle_penalty;
CREATE DATABASE cis2232_tackle_penalty;
use cis2232_tackle_penalty;

-- ------------------------------------------------------------------------------
-- Table to hold the penalty records submitted by PEI football officials.
-- Fields specified by the BA (see the project topic document).
-- The real world sample data comes from the
-- "CIS2232 Penalty Tracker Real World Data.xlsx" sheet.
-- ------------------------------------------------------------------------------

CREATE TABLE TacklePenalty
(
    id              int(5),
    homeTeam        varchar(50) NOT NULL COMMENT 'Name of the home team for the game',
    awayTeam        varchar(50) NOT NULL COMMENT 'Name of the visiting team for the game',
    date            varchar(10) NOT NULL COMMENT 'yyyy-MM-dd',
    penalty         varchar(50) NOT NULL COMMENT 'Infraction type',
    quarter         int(2) NOT NULL COMMENT 'Quarter infraction occurred (1-4)',
    penalizedTeam   varchar(50) NOT NULL COMMENT 'Name of offending team',
    offendingPlayer int(3) COMMENT 'Jersey number of offending player',
    ageDivision     varchar(20) NOT NULL COMMENT 'Age division of game',
    referee         varchar(50) NOT NULL COMMENT 'Name of official who flagged the infraction',
    impactScore     double COMMENT 'Calculated impact of the penalty'
) COMMENT 'This table holds tackle football penalty details';

ALTER TABLE TacklePenalty
    ADD PRIMARY KEY (id);
ALTER TABLE TacklePenalty
    MODIFY id int(4) NOT NULL AUTO_INCREMENT COMMENT 'This is the primary key',
    AUTO_INCREMENT = 1;

-- 1-78: original real world data (2026-09-12 ~ 2026-09-20)
-- 79-200: added to make Time Range filter show clear differences
-- 79-113  : 2026-10-15 ~ 2026-11-14  Past Month  35
-- 114-143 : 2026-08-15 ~ 2026-10-14  1-3 months   30
-- 144-173 : 2026-05-15 ~ 2026-08-14  3-6 months   30
-- 174-188 : 2025-11-15 ~ 2026-05-14  6-12 months  15
-- 189-200 : 2025-09-01 ~ 2025-11-14  Older than 1 year 12
-- Expected (today 2026-11-15): All 200, Past Year 188, Past 6 Months 173, Past 3 Months 143, Past Month 35
INSERT INTO TacklePenalty (id, homeTeam, awayTeam, date, penalty, quarter, penalizedTeam, offendingPlayer, ageDivision, referee, impactScore)
VALUES
    (1, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Mouth Guard Warning', 1, 'Saint John Falcons', 10, 'AFL', 'Terry', 0.5),
    (2, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 1, 'Holland College Hurricanes', 12, 'AFL', 'Jamie', 2),
    (3, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'No Yards', 1, 'Saint John Falcons', 13, 'AFL', 'Nicholas', 1),
    (4, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Kick Out of Bounds', 1, 'Holland College Hurricanes', 82, 'AFL', 'Nathan', 1),
    (5, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Time Count Violation', 1, 'Saint John Falcons', 38, 'AFL', 'Terry', 1),
    (6, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Objectionable Conduct', 1, 'Holland College Hurricanes', 20, 'AFL', 'Chris', 2),
    (7, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 1, 'Holland College Hurricanes', 66, 'AFL', 'Nathan', 5),
    (8, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 1, 'Holland College Hurricanes', 75, 'AFL', 'Terry', 2),
    (9, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 1, 'Holland College Hurricanes', 20, 'AFL', 'Jamie', 5),
    (10, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Mouth Guard Warning', 2, 'Holland College Hurricanes', 81, 'AFL', 'Nicholas', 0.625),
    (11, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Objectionable Conduct', 2, 'Holland College Hurricanes', 24, 'AFL', 'Chris', 2.5),
    (12, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Saint John Falcons', 6, 'AFL', 'Nathan', 6.25),
    (13, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Holland College Hurricanes', 21, 'AFL', 'Chris', 2.5),
    (14, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 36, 'AFL', 'Nicholas', 6.25),
    (15, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Saint John Falcons', 35, 'AFL', 'Nicholas', 2.5),
    (16, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 35, 'AFL', 'Nicholas', 6.25),
    (17, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Holland College Hurricanes', 36, 'AFL', 'Jamie', 2.5),
    (18, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 2, 'Saint John Falcons', 37, 'AFL', 'Terry', 2.5),
    (19, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 16, 'AFL', 'Kenny', 6.25),
    (20, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Saint John Falcons', 87, 'AFL', 'Nicholas', 6.25),
    (21, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 3, 'Holland College Hurricanes', 53, 'AFL', 'Terry', 3),
    (22, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 3, 'Holland College Hurricanes', 88, 'AFL', 'Kenny', 7.5),
    (23, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 3, 'Saint John Falcons', 16, 'AFL', 'Nicholas', 7.5),
    (24, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Pass Interference', 3, 'Holland College Hurricanes', 25, 'AFL', 'Chris', 4.5),
    (25, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 4, 'Holland College Hurricanes', 75, 'AFL', 'Terry', 4),
    (26, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Procedure', 4, 'Holland College Hurricanes', 8, 'AFL', 'Jamie', 2),
    (27, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Objectionable Conduct', 4, 'Holland College Hurricanes', 15, 'AFL', 'Nathan', 4),
    (28, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 4, 'Holland College Hurricanes', 59, 'AFL', 'Terry', 4),
    (29, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Illegal Procedure', 1, 'Charlottetown Privateers', 68, 'U18', 'Chris', 1),
    (30, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Intentional Grounding', 1, 'Cornwall Timberwolves', 4, 'U18', 'Rob', 1),
    (31, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Objectionable Conduct', 2, 'Charlottetown Privateers', 3, 'U18', 'Terry', 2.5),
    (32, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Illegal Formation', 2, 'Cornwall Timberwolves', 25, 'U18', 'Mark', 1.25),
    (33, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Offside', 2, 'Charlottetown Privateers', 60, 'U18', 'Nicholas', 1.25),
    (34, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Holding', 3, 'Cornwall Timberwolves', 45, 'U18', 'Rob', 3),
    (35, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Pass Interference', 3, 'Charlottetown Privateers', 15, 'U18', 'Terry', 4.5),
    (36, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Tandem Buck Block', 4, 'Cornwall Timberwolves', 45, 'U18', 'Chris', 4),
    (37, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Unnecessary Roughness', 4, 'Charlottetown Privateers', 3, 'U18', 'Chris', 10),
    (38, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Pass Interference', 4, 'Cornwall Timberwolves', 21, 'U18', 'Mark', 6),
    (39, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 1, 'Holland College Hurricanes', 65, 'AFL', 'Terry', 1),
    (40, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'No Yards', 1, 'Holland College Hurricanes', 36, 'AFL', 'Sean', 1),
    (41, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Time Count Violation', 1, 'Holland College Hurricanes', 4, 'AFL', 'Rob', 1),
    (42, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 2, 'Holland College Hurricanes', 93, 'AFL', 'Chris', 1.25),
    (43, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 2, 'Holland College Hurricanes', 86, 'AFL', 'Sean', 2.5),
    (44, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 80, 'AFL', 'Paul', 6.25),
    (45, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 2, 'Holland College Hurricanes', 53, 'AFL', 'Nick', 1.25),
    (46, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Holland College Hurricanes', 9, 'AFL', 'Nathan', 7.5),
    (47, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 3, 'Holland College Hurricanes', 59, 'AFL', 'Robert', 1.5),
    (48, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 4, 'Holland College Hurricanes', 88, 'AFL', 'Terry', 2),
    (49, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 4, 'Holland College Hurricanes', 63, 'AFL', 'Rob', 2),
    (50, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'No Yards', 4, 'Holland College Hurricanes', 86, 'AFL', 'Sean', 2),
    (51, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 4, 'Holland College Hurricanes', 26, 'AFL', 'Sean', 10),
    (52, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Equipment', 1, 'Dalhousie Tigers', 12, 'AFL', 'Rob', 2),
    (53, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 1, 'Dalhousie Tigers', 51, 'AFL', 'Nick', 2),
    (54, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Objectionable Conduct', 1, 'Dalhousie Tigers', 3, 'AFL', 'Chris', 2),
    (55, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 2, 'Dalhousie Tigers', 51, 'AFL', 'Chris', 1.25),
    (56, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 92, 'AFL', 'Terry', 7.5),
    (57, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 83, 'AFL', 'Sean', 7.5),
    (58, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 3, 'Dalhousie Tigers', 51, 'AFL', 'Nick', 3),
    (59, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 21, 'AFL', 'Sean', 7.5),
    (60, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 4, 'Dalhousie Tigers', 21, 'AFL', 'Rob', 4),
    (61, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 4, 'Dalhousie Tigers', 92, 'AFL', 'Terry', 2),
    (62, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 1, 'Summerside Spartans', 87, 'U15', 'Jayden', 1),
    (63, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 1, 'Summerside Spartans', 15, 'U15', 'Elliott', 1),
    (64, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 2, 'Summerside Spartans', 99, 'U15', 'Rob', 2.5),
    (65, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 2, 'Summerside Spartans', 87, 'U15', 'Jayden', 1.25),
    (66, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 2, 'Summerside Spartans', 67, 'U15', 'Rob', 2.5),
    (67, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 2, 'Summerside Spartans', 12, 'U15', 'Jayden', 6.25),
    (68, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 3, 'Summerside Spartans', 87, 'U15', 'Elliott', 1.5),
    (69, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Mouth Guard Warning', 3, 'Summerside Spartans', 25, 'U15', 'Mitchell', 0.75),
    (70, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Use of Hands', 3, 'Summerside Spartans', 92, 'U15', 'Mitchell', 3),
    (71, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Holding', 4, 'Summerside Spartans', 87, 'U15', 'Rob', 4),
    (72, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Mouth Guard Warning', 1, 'Souris Wildcats', 35, 'U15', 'Jayden', 0.5),
    (73, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 1, 'Souris Wildcats', 34, 'U15', 'Mitchell', 5),
    (74, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 2, 'Souris Wildcats', 77, 'U15', 'Elliott', 6.25),
    (75, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 2, 'Souris Wildcats', 52, 'U15', 'Jayden', 1.25),
    (76, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Holding', 2, 'Souris Wildcats', 39, 'U15', 'Mitchell', 2.5),
    (77, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Procedure', 3, 'Souris Wildcats', 39, 'U15', 'Jayden', 1.5),
    (78, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 4, 'Souris Wildcats', 25, 'U15', 'Rob', 4),
    (79, 'Charlottetown Privateers', 'Holland College Hurricanes', '2026-10-20', 'Procedure', 3, 'Holland College Hurricanes', 90, 'U18', 'Nick', 1.5),
    (80, 'Saint John Falcons', 'Souris Wildcats', '2026-10-31', 'No Yards', 2, 'Saint John Falcons', 88, 'U15', 'Nicholas', 1.25),
    (81, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-10-25', 'Procedure', 3, 'Dalhousie Tigers', 99, 'U15', 'Kenny', 1.5),
    (82, 'Cornwall Timberwolves', 'Charlottetown Privateers', '2026-11-03', 'Personal Foul', 1, 'Cornwall Timberwolves', 55, 'AFL', 'Kenny', 5),
    (83, 'Dalhousie Tigers', 'Charlottetown Privateers', '2026-11-14', 'Illegal Block in the Back', 4, 'Charlottetown Privateers', 42, 'AFL', 'Robert', 4),
    (84, 'Saint John Falcons', 'Holland College Hurricanes', '2026-10-29', 'Objectionable Conduct', 1, 'Saint John Falcons', 82, 'U18', 'Kenny', 2),
    (85, 'Souris Wildcats', 'Dalhousie Tigers', '2026-10-18', 'Illegal Formation', 1, 'Souris Wildcats', 40, 'U16', 'Jayden', 1),
    (86, 'Dalhousie Tigers', 'Charlottetown Privateers', '2026-10-19', 'Offside', 3, 'Dalhousie Tigers', 40, 'U16', 'Elliott', 1.5),
    (87, 'Souris Wildcats', 'Saint John Falcons', '2026-11-04', 'Illegal Use of Hands', 2, 'Souris Wildcats', 70, 'U15', 'Kenny', 2.5),
    (88, 'Dalhousie Tigers', 'Charlottetown Privateers', '2026-10-26', 'Unnecessary Roughness', 4, 'Dalhousie Tigers', 78, 'U16', 'Sean', 10),
    (89, 'Dalhousie Tigers', 'Holland College Hurricanes', '2026-10-28', 'Illegal Kick Out of Bounds', 1, 'Holland College Hurricanes', 57, 'U15', 'Nicholas', 1),
    (90, 'Saint John Falcons', 'Summerside Spartans', '2026-10-28', 'Objectionable Conduct', 1, 'Summerside Spartans', 16, 'U16', 'Mitchell', 2),
    (91, 'Saint John Falcons', 'Cornwall Timberwolves', '2026-11-01', 'Illegal Kick Out of Bounds', 1, 'Saint John Falcons', 28, 'U15', 'Paul', 1),
    (92, 'Holland College Hurricanes', 'Charlottetown Privateers', '2026-11-09', 'Objectionable Conduct', 4, 'Holland College Hurricanes', 47, 'U18', 'Mitchell', 4),
    (93, 'Holland College Hurricanes', 'Saint John Falcons', '2026-10-25', 'Pass Interference', 1, 'Saint John Falcons', 31, 'AFL', 'Robert', 3),
    (94, 'Summerside Spartans', 'Holland College Hurricanes', '2026-11-04', 'Pass Interference', 3, 'Summerside Spartans', 29, 'U16', 'Nick', 4.5),
    (95, 'Cornwall Timberwolves', 'Holland College Hurricanes', '2026-11-08', 'Objectionable Conduct', 3, 'Cornwall Timberwolves', 2, 'U18', 'Kenny', 3),
    (96, 'Summerside Spartans', 'Holland College Hurricanes', '2026-10-16', 'Mouth Guard Warning', 2, 'Holland College Hurricanes', 31, 'U15', 'Chris', 0.625),
    (97, 'Souris Wildcats', 'Summerside Spartans', '2026-11-12', 'Mouth Guard Warning', 3, 'Souris Wildcats', 39, 'U15', 'Elliott', 0.75),
    (98, 'Saint John Falcons', 'Holland College Hurricanes', '2026-10-17', 'Procedure', 3, 'Holland College Hurricanes', 64, 'U15', 'Mark', 1.5),
    (99, 'Souris Wildcats', 'Holland College Hurricanes', '2026-11-07', 'Objectionable Conduct', 4, 'Holland College Hurricanes', 76, 'U15', 'Mitchell', 4),
    (100, 'Dalhousie Tigers', 'Saint John Falcons', '2026-10-16', 'Illegal Equipment', 3, 'Saint John Falcons', 94, 'AFL', 'Paul', 3),
    (101, 'Souris Wildcats', 'Summerside Spartans', '2026-10-26', 'Illegal Formation', 2, 'Summerside Spartans', 60, 'U16', 'Chris', 1.25),
    (102, 'Holland College Hurricanes', 'Summerside Spartans', '2026-11-13', 'Personal Foul', 4, 'Holland College Hurricanes', 9, 'U18', 'Kenny', 10),
    (103, 'Souris Wildcats', 'Holland College Hurricanes', '2026-11-13', 'Illegal Formation', 2, 'Holland College Hurricanes', 11, 'U18', 'Robert', 1.25),
    (104, 'Charlottetown Privateers', 'Holland College Hurricanes', '2026-10-26', 'Illegal Formation', 2, 'Charlottetown Privateers', 6, 'U15', 'Mitchell', 1.25),
    (105, 'Saint John Falcons', 'Souris Wildcats', '2026-11-09', 'No Yards', 2, 'Souris Wildcats', 27, 'U15', 'Mitchell', 1.25),
    (106, 'Cornwall Timberwolves', 'Summerside Spartans', '2026-10-21', 'Intentional Grounding', 3, 'Cornwall Timberwolves', 14, 'AFL', 'Sean', 1.5),
    (107, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-11-08', 'Illegal Procedure', 3, 'Cornwall Timberwolves', 46, 'U18', 'Nathan', 1.5),
    (108, 'Dalhousie Tigers', 'Souris Wildcats', '2026-11-09', 'Illegal Kick Out of Bounds', 2, 'Souris Wildcats', 31, 'U18', 'Rob', 1.25),
    (109, 'Dalhousie Tigers', 'Souris Wildcats', '2026-10-24', 'Objectionable Conduct', 1, 'Dalhousie Tigers', 12, 'U16', 'Jamie', 2),
    (110, 'Cornwall Timberwolves', 'Summerside Spartans', '2026-10-21', 'Illegal Block in the Back', 2, 'Summerside Spartans', 45, 'AFL', 'Nicholas', 2.5),
    (111, 'Holland College Hurricanes', 'Summerside Spartans', '2026-11-03', 'Illegal Equipment', 1, 'Summerside Spartans', 45, 'U15', 'Kenny', 2),
    (112, 'Dalhousie Tigers', 'Souris Wildcats', '2026-10-26', 'Illegal Procedure', 2, 'Souris Wildcats', 69, 'AFL', 'Elliott', 1.25),
    (113, 'Cornwall Timberwolves', 'Souris Wildcats', '2026-10-31', 'Time Count Violation', 1, 'Souris Wildcats', 17, 'AFL', 'Mark', 1),
    (114, 'Summerside Spartans', 'Dalhousie Tigers', '2026-09-10', 'Illegal Formation', 2, 'Summerside Spartans', 21, 'AFL', 'Rob', 1.25),
    (115, 'Saint John Falcons', 'Charlottetown Privateers', '2026-08-16', 'Illegal Equipment', 1, 'Saint John Falcons', 86, 'U16', 'Nicholas', 2),
    (116, 'Cornwall Timberwolves', 'Holland College Hurricanes', '2026-09-22', 'Mouth Guard Warning', 4, 'Cornwall Timberwolves', 76, 'U16', 'Kenny', 1),
    (117, 'Souris Wildcats', 'Cornwall Timberwolves', '2026-10-01', 'Objectionable Conduct', 2, 'Cornwall Timberwolves', 17, 'AFL', 'Jamie', 2.5),
    (118, 'Dalhousie Tigers', 'Souris Wildcats', '2026-08-29', 'Illegal Equipment', 1, 'Souris Wildcats', 86, 'U18', 'Robert', 2),
    (119, 'Saint John Falcons', 'Dalhousie Tigers', '2026-10-03', 'Intentional Grounding', 3, 'Saint John Falcons', 57, 'U15', 'Jamie', 1.5),
    (120, 'Cornwall Timberwolves', 'Souris Wildcats', '2026-10-08', 'Holding', 1, 'Souris Wildcats', 71, 'U15', 'Jamie', 2),
    (121, 'Souris Wildcats', 'Saint John Falcons', '2026-09-14', 'Unnecessary Roughness', 1, 'Souris Wildcats', 21, 'AFL', 'Sean', 5),
    (122, 'Summerside Spartans', 'Holland College Hurricanes', '2026-09-19', 'Illegal Use of Hands', 1, 'Holland College Hurricanes', 59, 'U16', 'Paul', 2),
    (123, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-08-23', 'Personal Foul', 4, 'Cornwall Timberwolves', 62, 'AFL', 'Robert', 10),
    (124, 'Cornwall Timberwolves', 'Summerside Spartans', '2026-10-11', 'Pass Interference', 1, 'Cornwall Timberwolves', 21, 'U15', 'Chris', 3),
    (125, 'Charlottetown Privateers', 'Dalhousie Tigers', '2026-09-15', 'Illegal Use of Hands', 1, 'Charlottetown Privateers', 5, 'U15', 'Paul', 2),
    (126, 'Summerside Spartans', 'Charlottetown Privateers', '2026-10-05', 'Mouth Guard Warning', 3, 'Summerside Spartans', 15, 'U16', 'Robert', 0.75),
    (127, 'Cornwall Timberwolves', 'Holland College Hurricanes', '2026-10-04', 'No Yards', 4, 'Holland College Hurricanes', 69, 'AFL', 'Nathan', 2),
    (128, 'Summerside Spartans', 'Holland College Hurricanes', '2026-09-26', 'Tandem Buck Block', 3, 'Summerside Spartans', 86, 'U16', 'Nick', 3),
    (129, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-09-19', 'Procedure', 2, 'Holland College Hurricanes', 29, 'U18', 'Mark', 1.25),
    (130, 'Cornwall Timberwolves', 'Summerside Spartans', '2026-08-17', 'Pass Interference', 1, 'Summerside Spartans', 24, 'U15', 'Mark', 3),
    (131, 'Saint John Falcons', 'Souris Wildcats', '2026-09-14', 'Holding', 2, 'Souris Wildcats', 17, 'U16', 'Robert', 2.5),
    (132, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-09-27', 'Holding', 4, 'Cornwall Timberwolves', 23, 'AFL', 'Mitchell', 4),
    (133, 'Holland College Hurricanes', 'Summerside Spartans', '2026-08-19', 'Pass Interference', 4, 'Summerside Spartans', 71, 'AFL', 'Kenny', 6),
    (134, 'Cornwall Timberwolves', 'Saint John Falcons', '2026-09-19', 'Procedure', 1, 'Cornwall Timberwolves', 29, 'AFL', 'Terry', 1),
    (135, 'Dalhousie Tigers', 'Cornwall Timberwolves', '2026-09-25', 'Personal Foul', 2, 'Dalhousie Tigers', 40, 'U15', 'Mark', 6.25),
    (136, 'Dalhousie Tigers', 'Charlottetown Privateers', '2026-10-13', 'Personal Foul', 2, 'Dalhousie Tigers', 62, 'U15', 'Mark', 6.25),
    (137, 'Saint John Falcons', 'Cornwall Timberwolves', '2026-09-05', 'Holding', 1, 'Saint John Falcons', 54, 'U15', 'Elliott', 2),
    (138, 'Souris Wildcats', 'Summerside Spartans', '2026-09-07', 'Intentional Grounding', 4, 'Souris Wildcats', 15, 'AFL', 'Elliott', 2),
    (139, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-08-23', 'Unnecessary Roughness', 1, 'Dalhousie Tigers', 18, 'AFL', 'Jamie', 5),
    (140, 'Dalhousie Tigers', 'Charlottetown Privateers', '2026-08-29', 'Procedure', 4, 'Dalhousie Tigers', 24, 'U16', 'Elliott', 2),
    (141, 'Charlottetown Privateers', 'Saint John Falcons', '2026-09-26', 'Holding', 3, 'Charlottetown Privateers', 53, 'U18', 'Mark', 3),
    (142, 'Charlottetown Privateers', 'Holland College Hurricanes', '2026-09-17', 'Mouth Guard Warning', 2, 'Charlottetown Privateers', 32, 'U18', 'Kenny', 0.625),
    (143, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-09-12', 'Personal Foul', 1, 'Cornwall Timberwolves', 26, 'U18', 'Mitchell', 5),
    (144, 'Dalhousie Tigers', 'Saint John Falcons', '2026-08-05', 'Illegal Procedure', 1, 'Dalhousie Tigers', 40, 'U16', 'Mark', 1),
    (145, 'Charlottetown Privateers', 'Souris Wildcats', '2026-08-14', 'Illegal Kick Out of Bounds', 2, 'Charlottetown Privateers', 71, 'U18', 'Mark', 1.25),
    (146, 'Holland College Hurricanes', 'Summerside Spartans', '2026-07-04', 'Unnecessary Roughness', 4, 'Holland College Hurricanes', 38, 'U15', 'Mitchell', 10),
    (147, 'Souris Wildcats', 'Saint John Falcons', '2026-08-07', 'Illegal Block in the Back', 4, 'Saint John Falcons', 39, 'AFL', 'Jamie', 4),
    (148, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-06-27', 'No Yards', 3, 'Holland College Hurricanes', 74, 'AFL', 'Nick', 1.5),
    (149, 'Summerside Spartans', 'Dalhousie Tigers', '2026-07-06', 'Intentional Grounding', 1, 'Dalhousie Tigers', 78, 'U16', 'Kenny', 1),
    (150, 'Saint John Falcons', 'Dalhousie Tigers', '2026-06-13', 'Time Count Violation', 1, 'Dalhousie Tigers', 68, 'U18', 'Nick', 1),
    (151, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-06-15', 'Unnecessary Roughness', 4, 'Holland College Hurricanes', 25, 'AFL', 'Kenny', 10),
    (152, 'Dalhousie Tigers', 'Holland College Hurricanes', '2026-06-29', 'No Yards', 1, 'Holland College Hurricanes', 37, 'U16', 'Chris', 1),
    (153, 'Saint John Falcons', 'Souris Wildcats', '2026-08-10', 'Personal Foul', 3, 'Souris Wildcats', 96, 'AFL', 'Nathan', 7.5),
    (154, 'Souris Wildcats', 'Charlottetown Privateers', '2026-06-26', 'Personal Foul', 3, 'Charlottetown Privateers', 26, 'AFL', 'Terry', 7.5),
    (155, 'Holland College Hurricanes', 'Souris Wildcats', '2026-05-16', 'Illegal Formation', 4, 'Souris Wildcats', 97, 'U16', 'Mitchell', 2),
    (156, 'Souris Wildcats', 'Charlottetown Privateers', '2026-06-10', 'Pass Interference', 3, 'Souris Wildcats', 85, 'U16', 'Mark', 4.5),
    (157, 'Cornwall Timberwolves', 'Charlottetown Privateers', '2026-07-31', 'Offside', 3, 'Charlottetown Privateers', 86, 'AFL', 'Terry', 1.5),
    (158, 'Cornwall Timberwolves', 'Charlottetown Privateers', '2026-06-07', 'Intentional Grounding', 1, 'Cornwall Timberwolves', 9, 'U15', 'Jayden', 1),
    (159, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-05-22', 'Procedure', 3, 'Dalhousie Tigers', 99, 'AFL', 'Elliott', 1.5),
    (160, 'Souris Wildcats', 'Cornwall Timberwolves', '2026-05-20', 'Unnecessary Roughness', 1, 'Souris Wildcats', 73, 'AFL', 'Nicholas', 5),
    (161, 'Summerside Spartans', 'Charlottetown Privateers', '2026-07-23', 'Procedure', 3, 'Charlottetown Privateers', 45, 'AFL', 'Nick', 1.5),
    (162, 'Cornwall Timberwolves', 'Summerside Spartans', '2026-08-02', 'Unnecessary Roughness', 1, 'Summerside Spartans', 32, 'U16', 'Nathan', 5),
    (163, 'Holland College Hurricanes', 'Summerside Spartans', '2026-05-26', 'Offside', 1, 'Summerside Spartans', 94, 'U15', 'Mitchell', 1),
    (164, 'Summerside Spartans', 'Cornwall Timberwolves', '2026-06-02', 'Illegal Formation', 3, 'Cornwall Timberwolves', 89, 'U15', 'Nathan', 1.5),
    (165, 'Souris Wildcats', 'Dalhousie Tigers', '2026-07-10', 'Pass Interference', 1, 'Souris Wildcats', 85, 'AFL', 'Kenny', 3),
    (166, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-07-09', 'Mouth Guard Warning', 4, 'Cornwall Timberwolves', 28, 'U16', 'Nathan', 1),
    (167, 'Holland College Hurricanes', 'Saint John Falcons', '2026-08-11', 'Personal Foul', 3, 'Holland College Hurricanes', 57, 'U18', 'Rob', 7.5),
    (168, 'Charlottetown Privateers', 'Summerside Spartans', '2026-07-13', 'Personal Foul', 4, 'Summerside Spartans', 89, 'U15', 'Jayden', 10),
    (169, 'Souris Wildcats', 'Charlottetown Privateers', '2026-06-30', 'Intentional Grounding', 2, 'Charlottetown Privateers', 32, 'U15', 'Jayden', 1.25),
    (170, 'Cornwall Timberwolves', 'Saint John Falcons', '2026-06-15', 'Illegal Block in the Back', 4, 'Saint John Falcons', 12, 'U16', 'Rob', 4),
    (171, 'Holland College Hurricanes', 'Saint John Falcons', '2026-06-08', 'Offside', 3, 'Saint John Falcons', 52, 'U16', 'Elliott', 1.5),
    (172, 'Saint John Falcons', 'Holland College Hurricanes', '2026-05-16', 'Unnecessary Roughness', 4, 'Saint John Falcons', 13, 'U15', 'Terry', 10),
    (173, 'Charlottetown Privateers', 'Souris Wildcats', '2026-05-25', 'Tandem Buck Block', 1, 'Charlottetown Privateers', 31, 'U15', 'Mark', 2),
    (174, 'Summerside Spartans', 'Souris Wildcats', '2026-01-12', 'Tandem Buck Block', 1, 'Summerside Spartans', 16, 'AFL', 'Paul', 2),
    (175, 'Cornwall Timberwolves', 'Holland College Hurricanes', '2026-05-02', 'Illegal Use of Hands', 4, 'Cornwall Timberwolves', 88, 'U15', 'Nick', 4),
    (176, 'Summerside Spartans', 'Souris Wildcats', '2026-01-30', 'No Yards', 4, 'Souris Wildcats', 40, 'U15', 'Nathan', 2),
    (177, 'Holland College Hurricanes', 'Cornwall Timberwolves', '2026-02-10', 'Tandem Buck Block', 2, 'Cornwall Timberwolves', 75, 'U16', 'Rob', 2.5),
    (178, 'Charlottetown Privateers', 'Souris Wildcats', '2025-12-09', 'Illegal Block in the Back', 2, 'Charlottetown Privateers', 15, 'AFL', 'Sean', 2.5),
    (179, 'Cornwall Timberwolves', 'Saint John Falcons', '2026-04-15', 'Illegal Procedure', 2, 'Cornwall Timberwolves', 85, 'U16', 'Nicholas', 1.25),
    (180, 'Holland College Hurricanes', 'Dalhousie Tigers', '2025-12-21', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 63, 'U15', 'Kenny', 6.25),
    (181, 'Charlottetown Privateers', 'Saint John Falcons', '2026-01-29', 'Pass Interference', 4, 'Charlottetown Privateers', 99, 'U16', 'Chris', 6),
    (182, 'Dalhousie Tigers', 'Saint John Falcons', '2026-02-27', 'Personal Foul', 1, 'Saint John Falcons', 80, 'U18', 'Paul', 5),
    (183, 'Summerside Spartans', 'Dalhousie Tigers', '2025-12-20', 'Time Count Violation', 2, 'Summerside Spartans', 74, 'U16', 'Nathan', 1.25),
    (184, 'Summerside Spartans', 'Charlottetown Privateers', '2026-02-11', 'Pass Interference', 2, 'Charlottetown Privateers', 56, 'U15', 'Rob', 3.75),
    (185, 'Summerside Spartans', 'Saint John Falcons', '2025-12-03', 'No Yards', 1, 'Summerside Spartans', 77, 'U15', 'Jayden', 1),
    (186, 'Souris Wildcats', 'Saint John Falcons', '2025-12-19', 'Holding', 1, 'Souris Wildcats', 80, 'AFL', 'Jamie', 2),
    (187, 'Cornwall Timberwolves', 'Summerside Spartans', '2025-11-26', 'Illegal Kick Out of Bounds', 4, 'Summerside Spartans', 62, 'U18', 'Terry', 2),
    (188, 'Souris Wildcats', 'Charlottetown Privateers', '2026-04-13', 'Procedure', 4, 'Charlottetown Privateers', 1, 'U18', 'Jayden', 2),
    (189, 'Charlottetown Privateers', 'Summerside Spartans', '2025-09-15', 'Illegal Equipment', 2, 'Summerside Spartans', 97, 'U16', 'Mark', 2.5),
    (190, 'Charlottetown Privateers', 'Saint John Falcons', '2025-09-25', 'Mouth Guard Warning', 2, 'Charlottetown Privateers', 9, 'U18', 'Paul', 0.625),
    (191, 'Charlottetown Privateers', 'Dalhousie Tigers', '2025-09-30', 'Offside', 2, 'Dalhousie Tigers', 37, 'AFL', 'Nicholas', 1.25),
    (192, 'Dalhousie Tigers', 'Cornwall Timberwolves', '2025-10-25', 'Intentional Grounding', 3, 'Dalhousie Tigers', 21, 'U18', 'Paul', 1.5),
    (193, 'Charlottetown Privateers', 'Souris Wildcats', '2025-09-13', 'Objectionable Conduct', 4, 'Charlottetown Privateers', 82, 'U15', 'Jayden', 4),
    (194, 'Dalhousie Tigers', 'Summerside Spartans', '2025-11-01', 'Illegal Use of Hands', 1, 'Dalhousie Tigers', 76, 'AFL', 'Robert', 2),
    (195, 'Saint John Falcons', 'Summerside Spartans', '2025-10-15', 'Illegal Block in the Back', 1, 'Saint John Falcons', 34, 'U16', 'Elliott', 2),
    (196, 'Dalhousie Tigers', 'Charlottetown Privateers', '2025-11-13', 'Illegal Equipment', 2, 'Dalhousie Tigers', 82, 'U18', 'Mark', 2.5),
    (197, 'Holland College Hurricanes', 'Charlottetown Privateers', '2025-11-08', 'Mouth Guard Warning', 2, 'Charlottetown Privateers', 46, 'U15', 'Robert', 0.625),
    (198, 'Dalhousie Tigers', 'Summerside Spartans', '2025-10-19', 'Illegal Equipment', 3, 'Summerside Spartans', 90, 'U15', 'Nicholas', 3),
    (199, 'Summerside Spartans', 'Souris Wildcats', '2025-10-15', 'Personal Foul', 4, 'Summerside Spartans', 87, 'U15', 'Nicholas', 10),
    (200, 'Dalhousie Tigers', 'Charlottetown Privateers', '2025-09-29', 'Illegal Equipment', 4, 'Dalhousie Tigers', 41, 'AFL', 'Nick', 4);

CREATE TABLE CodeType (codeTypeId int(3) COMMENT 'This is the primary key for code types',
                       englishDescription varchar(100) NOT NULL COMMENT 'English description',
                       frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
                       createdDateTime datetime DEFAULT NULL,
                       createdUserId varchar(20) DEFAULT NULL,
                       updatedDateTime datetime DEFAULT NULL,
                       updatedUserId varchar(20) DEFAULT NULL
) COMMENT 'This tables holds the code types that are available for the application';

ALTER TABLE CodeType
    ADD PRIMARY KEY (CodeTypeId);

INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 'User Types', 'User TypesFR', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 'Penalty Types', 'Penalty TypesFR', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (3, 'Age Divisions', 'Age DivisionsFR', sysdate(), '', CURRENT_TIMESTAMP, '');

CREATE TABLE CodeValue (
                           codeTypeId int(3) NOT NULL COMMENT 'see code_type table',
                           codeValueSequence int(3) NOT NULL,
                           englishDescription varchar(100) NOT NULL COMMENT 'English description',
                           englishDescriptionShort varchar(20) NOT NULL COMMENT 'English abbreviation for description',
                           frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
                           frenchDescriptionShort varchar(20) DEFAULT NULL COMMENT 'French abbreviation for description',
                           sortOrder int(3) DEFAULT NULL COMMENT 'Sort order if applicable',
                           createdDateTime datetime DEFAULT NULL,
                           createdUserId varchar(20) DEFAULT NULL,
                           updatedDateTime datetime DEFAULT NULL,
                           updatedUserId varchar(20) DEFAULT NULL
) COMMENT='This will hold code values for the application.';

ALTER TABLE CodeValue
    ADD PRIMARY KEY (CodeTypeId, codeValueSequence);

-- User Types
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 1, 'General', 'General', 'GeneralFR', 'GeneralFR', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 2, 'Admin', 'Admin', 'AdminFR', 'AdminFR', '2015-10-25 18:44:37', 'admin', '2015-10-25 18:44:37', 'admin');

-- Penalty Types
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 1, 'Offside', 'Offside', 'OffsideFR', 'OffsideFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 2, 'No Yards', 'No Yards', 'No YardsFR', 'No YardsFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 3, 'Time Count Violation', 'Time Count', 'Time Count ViolationFR', 'Time CountFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 4, 'Illegal Kick Out of Bounds', 'Kick Out of Bnd', 'Illegal Kick Out of BoundsFR', 'Kick Out of BndFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 5, 'Illegal Procedure', 'Illegal Proc', 'Illegal ProcedureFR', 'Illegal ProcFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 6, 'Procedure', 'Procedure', 'ProcedureFR', 'ProcedureFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 7, 'Intentional Grounding', 'Int Grounding', 'Intentional GroundingFR', 'Int GroundingFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 8, 'Illegal Formation', 'Illegal Form', 'Illegal FormationFR', 'Illegal FormFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 9, 'Mouth Guard Warning', 'Mouth Guard', 'Mouth Guard WarningFR', 'Mouth GuardFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 10, 'Holding', 'Holding', 'HoldingFR', 'HoldingFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 11, 'Illegal Use of Hands', 'Use of Hands', 'Illegal Use of HandsFR', 'Use of HandsFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 12, 'Illegal Block in the Back', 'Block in Back', 'Illegal Block in the BackFR', 'Block in BackFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 13, 'Tandem Buck Block', 'Tandem Buck', 'Tandem Buck BlockFR', 'Tandem BuckFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 14, 'Objectionable Conduct', 'Obj Conduct', 'Objectionable ConductFR', 'Obj ConductFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 15, 'Pass Interference', 'Pass Interf', 'Pass InterferenceFR', 'Pass InterfFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 16, 'Unnecessary Roughness', 'Unn Roughness', 'Unnecessary RoughnessFR', 'Unn RoughnessFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 17, 'Personal Foul', 'Personal Foul', 'Personal FoulFR', 'Personal FoulFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');

-- Age Divisions
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (3, 1, 'AFL', 'AFL', 'AFLFR', 'AFLFR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (3, 2, 'U15', 'U15', 'U15FR', 'U15FR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (3, 3, 'U16', 'U16', 'U16FR', 'U16FR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (3, 4, 'U18', 'U18', 'U18FR', 'U18FR', '2026-09-25 10:00:00', 'admin', '2026-09-25 10:00:00', 'admin');
