Connect to cs157a@



CREATE PROCEDURE p2.CUST_CRT(IN Name varchar(15), IN Gender char(1), IN Age integer, IN Pin integer, OUT ID integer, OUT sql_code int, OUT err_msg VARCHAR(200))
LANGUAGE SQL
Begin
DECLARE newID int;

DECLARE getID CURSOR FOR 
SELECT MAX(ID) newID FROM FINAL TABLE(INSERT INTO P2.CUSTOMER(NAME, GENDER, AGE, PIN) VALUES (Name,Gender,Age,P2.ENCRYPT(Pin)));

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
	SET sql_code = 666;
	SET err_msg = 'Your inputs are incorrect.';
RETURN;
END;

OPEN getID;
FETCH getID into ID;
CLOSE getID;
SET sql_code = 0;
SET err_msg = ':: CUSTOMER CREATE SUCCESS';

END@








CREATE PROCEDURE p2.CUST_LOGIN(IN inputID int, IN inputPin int, OUT Valid int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN
DECLARE result int;

DECLARE findPin CURSOR FOR
SELECT Pin FROM P2.Customer WHERE ID = inputID;

DECLARE EXIT HANDLER FOR NOT FOUND
BEGIN
	SET sql_code = 100;
	SET err_msg = 'Invalid ID';
	SET Valid = 0;
	RETURN;
END;

OPEN findPin;
FETCH findPin into result;
CLOSE findPin;

IF(P2.Decrypt(result) = inputPin)
THEN 
	SET Valid = 1;
	SET sql_code = 0;
	SET err_msg = ':: LOGIN SUCCESS';
ELSE 
	SET Valid = 0;
	SET sql_code = 50;
	SET err_msg = 'Invalid Pin entered';
	RETURN;
END IF;

END@







CREATE PROCEDURE P2.ACCT_OPN(IN inputID int, IN inputBalance int, IN Type char(1), OUT Number int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN
DECLARE newNum int;



DECLARE getNum CURSOR FOR
SELECT MAX(Number) newNum FROM FINAL TABLE(INSERT INTO p2.Account(ID,Balance,Type,Status) VALUES
(inputID,inputBalance,Type,'A'));

DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
BEGIN
	SET sql_code = 666;
	SET err_msg = 'Your inputs are incorrect.';
RETURN;
END;

IF (inputBalance < 0) 
THEN BEGIN 
SET sql_code = -666; 
SET err_msg = 'ERROR. Negative deposit';
RETURN; 
END; 
END IF;

OPEN getNum;
FETCH getNum into Number;
CLOSE getNum;
SET sql_code = 0;
SET err_msg = ':: OPEN ACCOUNT SUCCESS';

END@






CREATE PROCEDURE P2.ACCT_CLS(IN inputNum int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN

DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
BEGIN
SET sql_code = 100;
SET err_msg = 'Account number not found or invalid input.';
RETURN;
END;

UPDATE P2.Account SET Status = 'I', Balance = 0 WHERE Number = inputNum;

SET sql_code = 0;
SET err_msg = ':: CLOSE ACCOUNT SUCCESS';



END@




CREATE PROCEDURE P2.ACCT_DEP(IN inputNum int, IN Amt int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN


DECLARE EXIT HANDLER FOR NOT FOUND, SQLEXCEPTION
BEGIN
IF (Amt < 0)
THEN  
	SET sql_code = -25; 
	SET err_msg = 'ERROR. Your deposit is negative';
	RETURN; 
ELSE
	SET sql_code = 100;
	SET err_msg = 'Account number not found.';
	RETURN;
END IF;
END;

IF(amt < 0)
THEN 
	SET sql_code = -25;
	SET err_msg = 'Your deposit amount is a negative number.';
	RETURN;
END IF;

UPDATE P2.Account SET Balance = Balance + Amt WHERE Number = inputNum;
SET sql_code = 0;
SET err_msg = ':: ACCOUNT DEPOSIT SUCCESS';

END@





CREATE PROCEDURE P2.ACCT_WTH(IN inputNum int, IN amt int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN
DECLARE oldBalance int;


DECLARE balanceBeforeWTH CURSOR FOR 
SELECT BALANCE FROM P2.ACCOUNT WHERE Number = inputNum AND STATUS = 'A';

DECLARE EXIT HANDLER FOR NOT FOUND
BEGIN
SET sql_code = 100;
SET err_msg = 'Account number not found or is not active.';
RETURN;
END;

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
SET sql_code = 666;
SET err_msg = 'Your inputs are invalid.';
RETURN;
END;

OPEN balanceBeforeWTH;
FETCH balanceBeforeWTH into oldBalance;
CLOSE balanceBeforeWTH;

IF(amt < 0)
THEN 
	SET sql_code = -25;
	SET err_msg = 'Your withdrawal amount is a negative number.';
	RETURN;
ELSE IF(amt > oldBalance)
THEN 
	SET sql_code = 200;
	SET err_msg = 'Your balance is insufficient for your withdrawal.';
	RETURN;
ELSE
	UPDATE P2.ACCOUNT SET Balance = Balance - amt WHERE Number = inputNum;
	SET sql_code = 0;
	SET err_msg = ':: WITHDRAW SUCCESS';
END IF;
END IF;

END@




CREATE PROCEDURE P2.ACCT_TRX (IN src_acct int, IN dest_acct int, IN amt int, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN

DECLARE EXIT HANDLER FOR NOT FOUND
BEGIN
SET sql_code = 100;
SET err_msg = 'Source Account number not found or is not active.';
RETURN;
END;

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
SET sql_code = 666;
SET err_msg = 'Inputs are invalid.';
RETURN;
END;

IF(amt < 0)
THEN 
	SET sql_code = -25;
	SET err_msg = 'Your transfer amount is a negative number.';
	RETURN;
END IF;

CALL p2.ACCT_WTH(src_acct, amt, sql_code, err_msg);

IF(sql_code != 0)
THEN RETURN;
ELSE
CALL p2.ACCT_DEP(dest_acct, amt,sql_code,err_msg);
END IF;

IF (sql_code != 0) 
THEN RETURN; 
END IF;

SET sql_code = 0;
SET err_msg = ':: TRANSFER SUCCESS';

END@






CREATE PROCEDURE p2.ADD_INTEREST(IN Savings_Rate FLOAT, IN Checking_Rate FLOAT, OUT sql_code int, OUT err_msg varchar(200))
LANGUAGE SQL
BEGIN

DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
BEGIN
SET sql_code = 100;
SET err_msg = 'Account number not found or invalid input.';
RETURN;
END;

IF(Savings_Rate < 0 OR Savings_Rate > 1)
THEN
SET sql_code = -001;
SET err_msg = 'Your Savings Rate is invalid. Must be between 0-1.';
RETURN;

ELSEIF (Checking_rate < 0 OR Checking_rate > 1)
THEN
SET sql_code = '-002';
SET err_msg = 'Your Checking Rate is invalid. Must be between 0-1.';
RETURN;
END IF;

UPDATE P2.ACCOUNT SET Balance = Balance + (Balance * Savings_rate) WHERE TYPE = 'S';
UPDATE P2.ACCOUNT SET Balance = Balance + (Balance * checking_rate) WHERE TYPE = 'C';

SET sql_code = 0;
SET err_msg = ':: RATE ADDED TO ACCOUNT';
END@







Commit;
Terminate;
