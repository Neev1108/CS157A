
connect to cs157a;
Create Trigger CannotTake
BEFORE INSERT ON HW3.Schedule
REFERENCING NEW ROW AS newClass
FOR EACH ROW
BEGIN ATOMIC

DECLARE PreReqHOLD INTEGER;--
DECLARE CoReqHold char(1);--

SET PreReqHOLD = (SELECT PreReqID FROM HW3.ClassReq where PreReqID = newClass.ClassID);--
SET CoReqHold = (SELECT CoReq FROM HW3.ClassReq where PreReqID = newClass.ClassID);--

IF (NOT EXISTS(SELECT ClassID from HW3.Schedule where ClassID = PreReqHOLD))

THEN SIGNAL SQLSTATE '78001' 
SET MESSAGE_TEXT = 'Missing Pre_req';--
END IF;--

IF (CoReqHold = 'T' AND NOT EXISTS(SELECT Semester From HW3.Schedule WHERE newClass.semester = Semester))
THEN SIGNAL SQLSTATE '78002' 
SET MESSAGE_TEXT = 'Missing Co-Req';--
END IF;--




END;
terminate;



