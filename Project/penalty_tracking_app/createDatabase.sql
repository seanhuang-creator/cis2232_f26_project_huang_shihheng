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
       (10, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Mouth Guard Warning', 2, 'Holland College Hurricanes', 81, 'AFL', 'Nicholas', 1),
       (11, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Objectionable Conduct', 2, 'Holland College Hurricanes', 24, 'AFL', 'Chris', 4),
       (12, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Saint John Falcons', 6, 'AFL', 'Nathan', 10),
       (13, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Holland College Hurricanes', 21, 'AFL', 'Chris', 4),
       (14, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 36, 'AFL', 'Nicholas', 10),
       (15, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Saint John Falcons', 35, 'AFL', 'Nicholas', 4),
       (16, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 35, 'AFL', 'Nicholas', 10),
       (17, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Block in the Back', 2, 'Holland College Hurricanes', 36, 'AFL', 'Jamie', 4),
       (18, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 2, 'Saint John Falcons', 37, 'AFL', 'Terry', 4),
       (19, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 16, 'AFL', 'Kenny', 10),
       (20, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 2, 'Saint John Falcons', 87, 'AFL', 'Nicholas', 10),
       (21, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 3, 'Holland College Hurricanes', 53, 'AFL', 'Terry', 6),
       (22, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 3, 'Holland College Hurricanes', 88, 'AFL', 'Kenny', 15),
       (23, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Unnecessary Roughness', 3, 'Saint John Falcons', 16, 'AFL', 'Nicholas', 15),
       (24, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Pass Interference', 3, 'Holland College Hurricanes', 25, 'AFL', 'Chris', 9),
       (25, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 4, 'Holland College Hurricanes', 75, 'AFL', 'Terry', 8),
       (26, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Illegal Procedure', 4, 'Holland College Hurricanes', 8, 'AFL', 'Jamie', 4),
       (27, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Objectionable Conduct', 4, 'Holland College Hurricanes', 15, 'AFL', 'Nathan', 8),
       (28, 'Holland College Hurricanes', 'Saint John Falcons', '2026-09-12', 'Holding', 4, 'Holland College Hurricanes', 59, 'AFL', 'Terry', 8),
       (29, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Illegal Procedure', 1, 'Charlottetown Privateers', 68, 'U18', 'Chris', 1),
       (30, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Intentional Grounding', 1, 'Cornwall Timberwolves', 4, 'U18', 'Rob', 1),
       (31, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Objectionable Conduct', 2, 'Charlottetown Privateers', 3, 'U18', 'Terry', 4),
       (32, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Illegal Formation', 2, 'Cornwall Timberwolves', 25, 'U18', 'Mark', 2),
       (33, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Offside', 2, 'Charlottetown Privateers', 60, 'U18', 'Nicholas', 2),
       (34, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Holding', 3, 'Cornwall Timberwolves', 45, 'U18', 'Rob', 6),
       (35, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Pass Interference', 3, 'Charlottetown Privateers', 15, 'U18', 'Terry', 9),
       (36, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Tandem Buck Block', 4, 'Cornwall Timberwolves', 45, 'U18', 'Chris', 8),
       (37, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Unnecessary Roughness', 4, 'Charlottetown Privateers', 3, 'U18', 'Chris', 20),
       (38, 'Charlottetown Privateers', 'Cornwall Timberwolves', '2026-09-18', 'Pass Interference', 4, 'Cornwall Timberwolves', 21, 'U18', 'Mark', 12),
       (39, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 1, 'Holland College Hurricanes', 65, 'AFL', 'Terry', 1),
       (40, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'No Yards', 1, 'Holland College Hurricanes', 36, 'AFL', 'Sean', 1),
       (41, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Time Count Violation', 1, 'Holland College Hurricanes', 4, 'AFL', 'Rob', 1),
       (42, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 2, 'Holland College Hurricanes', 93, 'AFL', 'Chris', 2),
       (43, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 2, 'Holland College Hurricanes', 86, 'AFL', 'Sean', 4),
       (44, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 2, 'Holland College Hurricanes', 80, 'AFL', 'Paul', 10),
       (45, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 2, 'Holland College Hurricanes', 53, 'AFL', 'Nick', 2),
       (46, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Holland College Hurricanes', 9, 'AFL', 'Nathan', 15),
       (47, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 3, 'Holland College Hurricanes', 59, 'AFL', 'Robert', 3),
       (48, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 4, 'Holland College Hurricanes', 88, 'AFL', 'Terry', 4),
       (49, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Procedure', 4, 'Holland College Hurricanes', 63, 'AFL', 'Rob', 4),
       (50, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'No Yards', 4, 'Holland College Hurricanes', 86, 'AFL', 'Sean', 4),
       (51, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 4, 'Holland College Hurricanes', 26, 'AFL', 'Sean', 20),
       (52, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Illegal Equipment', 1, 'Dalhousie Tigers', 12, 'AFL', 'Rob', 2),
       (53, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 1, 'Dalhousie Tigers', 51, 'AFL', 'Nick', 2),
       (54, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Objectionable Conduct', 1, 'Dalhousie Tigers', 3, 'AFL', 'Chris', 2),
       (55, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 2, 'Dalhousie Tigers', 51, 'AFL', 'Chris', 2),
       (56, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 92, 'AFL', 'Terry', 15),
       (57, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 83, 'AFL', 'Sean', 15),
       (58, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 3, 'Dalhousie Tigers', 51, 'AFL', 'Nick', 6),
       (59, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Unnecessary Roughness', 3, 'Dalhousie Tigers', 21, 'AFL', 'Sean', 15),
       (60, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Holding', 4, 'Dalhousie Tigers', 21, 'AFL', 'Rob', 8),
       (61, 'Holland College Hurricanes', 'Dalhousie Tigers', '2026-09-19', 'Offside', 4, 'Dalhousie Tigers', 92, 'AFL', 'Terry', 4),
       (62, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 1, 'Summerside Spartans', 87, 'U15', 'Jayden', 1),
       (63, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 1, 'Summerside Spartans', 15, 'U15', 'Elliott', 1),
       (64, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 2, 'Summerside Spartans', 99, 'U15', 'Rob', 4),
       (65, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 2, 'Summerside Spartans', 87, 'U15', 'Jayden', 2),
       (66, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 2, 'Summerside Spartans', 67, 'U15', 'Rob', 4),
       (67, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 2, 'Summerside Spartans', 12, 'U15', 'Jayden', 10),
       (68, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 3, 'Summerside Spartans', 87, 'U15', 'Elliott', 3),
       (69, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Mouth Guard Warning', 3, 'Summerside Spartans', 25, 'U15', 'Mitchell', 1.5),
       (70, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Use of Hands', 3, 'Summerside Spartans', 92, 'U15', 'Mitchell', 6),
       (71, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Holding', 4, 'Summerside Spartans', 87, 'U15', 'Rob', 8),
       (72, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Mouth Guard Warning', 1, 'Souris Wildcats', 35, 'U15', 'Jayden', 0.5),
       (73, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 1, 'Souris Wildcats', 34, 'U15', 'Mitchell', 5),
       (74, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Unnecessary Roughness', 2, 'Souris Wildcats', 77, 'U15', 'Elliott', 10),
       (75, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Offside', 2, 'Souris Wildcats', 52, 'U15', 'Jayden', 2),
       (76, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Holding', 2, 'Souris Wildcats', 39, 'U15', 'Mitchell', 4),
       (77, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Procedure', 3, 'Souris Wildcats', 39, 'U15', 'Jayden', 3),
       (78, 'Souris Wildcats', 'Summerside Spartans', '2026-09-20', 'Illegal Block in the Back', 4, 'Souris Wildcats', 25, 'U15', 'Rob', 8);

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
