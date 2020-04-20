#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sqlenv.h>

const char DB_NAME_KEY[16] = "db2.name";
const char DB_USER_KEY[16] = "db2.username";
const char DB_PASS_KEY[16] = "db2.password";
const char BATCH_KEY[16] = "p3.batch.input";

struct sqlca sqlca;

EXEC SQL BEGIN DECLARE SECTION;
  char db_name[20];
  char db_user[20];
  char db_pass[20];

  int cus_id;
  char cus_name[15];
  char cus_gender;
  int cus_age;
  int cus_pin;
  int acc_num;
  int acc_id;
  int acc_bal;
  char acc_type;
  char acc_status;

  int src_cid;
  int dest_cid;
  int trans_amt;
  int a_total;
  int b_min;
  int b_max;
  int b_avg;


char nameInput[15];
char genderInput;
int ageInput;
int pinInput;
int resultID;
EXEC SQL END DECLARE SECTION;


/**
 * Create a new customer.
 * @param name customer name
 * @param gender customer gender
 * @param age customer age
 * @param pin customer pin
 */
static void newCustomer(char* name, char* gender, char* age, char* pin)
{
    
printf(":: CREATE NEW CUSTOMER - RUNNING \n");
    struct sqlca sqlca;
     printf(":: CREATE NEW CUSTOMER - RUNNING\n");
    
     strcpy(nameInput,name);
     genderInput = *gender;
     ageInput = atoi(age);
     pinInput = atoi(pin);
     EXEC SQL DECLARE newCus CURSOR FOR SELECT ID FROM FINAL TABLE(INSERT INTO P3.CUSTOMER(NAME, GENDER, AGE, PIN) VALUES (:nameInput,:genderInput,:ageInput,:pinInput));
     EXEC SQL OPEN newCus;
     EXEC SQL FETCH newCus INTO :cus_id;
    
     if(sqlca.sqlcode < 0 ){
         printf("ERROR WRONG Input\n" );
     }
      else if (sqlca.sqlcode >= 0)
      {
        printf(":: NEW CUSTOMER ID: %d\n", cus_id);
        printf(":: CREATE NEW CUSTOMER - SUCCESS\n");
      }
}
    
    /*
strcpy(inputName, name);
inputGender = *gender;
inputAge = atoi(age);
inputPin = atoi(pin);
int newID;
    
    EXEC SQL DECLARE newCus CURSOR FOR SELECT ID FROM FINAL TABLE(INSERT INTO P3.Customer(NAME, GENDER, AGE, PIN) Values(:inputName,:inputGender,:inputAge,:inputPin));
    EXEC SQL OPEN newCus;
    EXEC SQL FETCH newCus INTO :newID;

if (sqlca.sqlcode >= 0) {
  printf("\n:: CREATE NEW CUSTOMER #%s - SUCCESS", newID);
} else {
  printf("\n:: CREATE NEW CUSTOMER - FAILURE");
  return;
}
    EXEC SQL CLOSE newCus;
    */


/**
 * Open a new account.
 * @param id customer id
 * @param type type of account
 * @param amount initial deposit amount
 */

static void openAccount(char* id, char* type, char* amount)
{
 /*
    printf(":: OPEN NEW ACCOUNT - RUNNING \n");
    strcpy(cus_id, id);
    strcpy(acc_type, type);
    strcpy(acc_bal, amount);
    
     if(acc_bal < 0){
         printf("\n:: OPEN ACCOUNT - FAILURE -> Account balance is negative\n");
         return;
    }
    else{
    EXEC SQL SELECT NUMBER INTO :acc_id FROM FINAL TABLE (INSERT INTO P3.ACCOUNT(ID, Balance, Type) VALUES (:cus_id, :acc_bal, :acc_type));
    }
    if (sqlca.sqlcode == 0) {
       printf("\n:: OPEN ACCOUNT #%s - SUCCESS\n",acc_id);
     } else {
       printf("\n:: OPEN ACCOUNT - FAILURE\n");
     }
  */
}

/**
 * Close an account.
 * @param accNum account number
 */

static void closeAccount(char* accNum)
{
    /*
 
    printf("\n:: CLOSE ACCOUNT - RUNNING\n");
    strcpy(acc_num, accNum);

    EXEC SQL UPDATE P3.ACCOUNT SET BALANCE=0, STATUS='I' WHERE (NUMBER=:acc_num);

    if (sqlca.sqlcode == 0) {
      printf("\n:: CLOSE ACCOUNT - SUCCESS\n");
    } else {
      printf("\n:: CLOSE ACCOUNT - FAILURE\n");
    }
     */
     
}

/**
 * Deposit into an account.
 * @param accNum account number
 * @param amount deposit amount
 */

static void deposit(char* accNum, char* amount)
{
    /*
    printf("\n:: DEPOSIT INTO ACCOUNT - RUNNING\n");
  strcpy(acc_num, accNum);
  strcpy(acc_bal, amount);

    if(acc_bal < 0){
     printf("\n:: DEPOSIT - FAILURE -> Account balance is negative\n");
     return;
}
else{
  EXEC SQL UPDATE P3.ACCOUNT SET BALANCE=BALANCE+:acc_bal WHERE (NUMBER=:acc_num AND STATUS='A');
}
  if (sqlca.sqlcode == 0) {
    printf("\n:: DEPOSIT INTO ACCOUNT #%s - SUCCESS\n", accNum);
  } else {
    printf("\n:: DEPOSIT - FAILURE\n");
}
     */
}

/**
 * Withdraw from an account.
 * @param accNum account number
 * @param amount withdraw amount
 */

static void withdraw(char* accNum, char* amount)
{
    /*
    printf("\n:: WITHDRAW - RUNNING\n");
strcpy(acc_num, accNum);
strcpy(acc_bal, amount);

if(acc_bal < 0){
     printf("\n:: WITHDRAW - FAILURE -> Account balance is negative\n");
     return;
}
else{
EXEC SQL UPDATE P3.ACCOUNT SET BALANCE=BALANCE-:acc_bal WHERE (NUMBER=:acc_num AND STATUS='A');
}

if (sqlca.sqlcode == 0) {
  printf("\n:: WITHDRAW FROM ACCOUNT #%s - SUCCESS\n", accNum);
} else {
  printf("\n:: WITHDRAW - FAILURE\n");
}
     */
}

/**
 * Transfer amount from source account to destination account. 
 * @param srcAccNum source account number
 * @param destAccNum destination account number
 * @param amount transfer amount
 */

static void transfer(char* srcAccNum, char* destAccNum, char* amount)
{
    /*
    printf("\n:: TRANSFER - RUNNING\n");

strcpy(acc_num, srcAccNum);
 strcpy(acc_id, destAccNum);
 strcpy(acc_bal, amount);

if(acc_bal < 0){
 EXEC SQL UPDATE P3.ACCOUNT SET BALANCE=BALANCE-:acc_bal WHERE (NUMBER=:acc_num AND STATUS='A');
}
else {
printf("\n:: TRANSFER - FAILURE -> Transfer amount is negative.\n");
return;
}

 if (sqlca.sqlcode == 0) { //for any sql errors in the withdrawing
   EXEC SQL UPDATE P3.ACCOUNT SET BALANCE=BALANCE+:acc_bal WHERE (NUMBER=:acc_id AND STATUS='A');
   
   if (sqlca.sqlcode == 0) {//for any sql errors in the depositing
     printf("\n:: TRANSFER FROM ACCOUNT #%s TO ACCOUNT #%s - SUCCESS\n", srcAccNum, destAccNum);
   }
   else {
     printf("\n:: TRANSFER - FAILURE\n");
     return;
   }
 } else {
   printf("\n:: TRANSFER - FAILURE -> cannot find account numbers or wrong input.\n");
   return;
 }
     */
}


/**
 * Display account summary.
 * @param cusID customer ID
 */

static void accountSummary(char* cusID)
{
    /*
    printf("\n:: ACCOUNT SUMMARY - RUNNING");

strcpy(cus_id, cusID);
    
EXEC SQL DECLARE accSum CURSOR FOR
SELECT NUMBER, BALANCE
FROM P1.ACCOUNT
WHERE (ID=:cus_id AND STATUS='A');
           
EXEC SQL OPEN accSum;
    
for(;;){
    EXEC SQL FETCH accSum
    INTO :acc_num, :acc_bal;
    
    if(sqlca.sqlcode == 0) {
         printf("\n%-6s %-12s",acc_num,acc_bal);
    }
    else{
     printf("\n:: ACCOUNT SUMMARY - FAILURE");
     return;
     }
}

EXEC SQL CLOSE accSum;
printf("\n:: ACCOUNT SUMMARY FOR CUSTOMER #%s - SUCCESS", cusID);
     */
}


/* Display Report A - Customer Information with Total Balance in Decreasing Order.
 */

static void reportA()
//{
/*
EXEC SQL DECLARE reportA CURSOR FOR
SELECT p3.Customer.ID, p3.total_balance.NAME, p3.Customer.AGE, p3.Customer.GENDER, p3.total_balance.BALANCE
FROM p3.total_balance, p3.CUSTOMER;
EXEC SQL OPEN reportA;

for(;;){
EXEC SQL FETCH reportA
    INTO :cus_id, :cus_name, :cus_age, :cus_gender, :acc_bal;
    
    if (sqlca.sqlcode == 0) {
     printf("\n%-5s %-12s %-4s %-6s %-12s",cus_id,cus_name,cus_age,cus_gender,acc_bal);
     }
    else{
    printf("\n:: ACCOUNT SUMMARY - FAILURE");
    return;
    
}
EXEC SQL CLOSE reportA;
printf(":: REPORT A - SUCCESS\n");
    */
}


/**
 * Display Report B - Customer Information with Total Balance in Decreasing Order.
 * @param min minimum age
 * @param max maximum age
 */

static void reportB(char* min, char* max)
{
    /*
strcpy(b_min, min);
 strcpy(b_max, max);

 EXEC SQL DECLARE reportB CURSOR FOR
   SELECT AVG(BALANCE)
   FROM p3.TOTAL_BALANCE, p3.customer
   WHERE AGE>=:b_min AND AGE<=:b_max;
 EXEC SQL OPEN reportB;
    */
    
 
  //printf(":: REPORT B - SUCCESS\n");
}

/**
 * Retrieve database connection info from properties file.
 * @param filename name of properties file
 */
static void init(char filename[])
{
  // open file
  FILE *fp = fopen(filename, "r");
  // print error if file cannot be opened
  if (fp == NULL)
  {
    printf("Cannot open properties file %s\n", filename);
  }
  else
  {
    char *cur_line = (char *) malloc(256);
    // read each line
    while ( fscanf(fp, "%s", cur_line) != EOF)
    {
      char *line_copy = (char *) malloc(256);
      char *token;
      // copy current line
      strcpy(line_copy, cur_line);
      // tokenize line and save connection values
      while ( (token = strsep(&line_copy, "=")) != NULL )
      {
        if ( strcmp(token, DB_NAME_KEY) == 0)
        {
          token = strsep(&line_copy, "=");
          strcpy(db_name, token);
        }
        else if ( strcmp(token, DB_USER_KEY) == 0)
        {
          token = strsep(&line_copy, "=");
          strcpy(db_user, token);
        }
        else if ( strcmp(token, DB_PASS_KEY) == 0)
        {
          token = strsep(&line_copy, "=");
          strcpy(db_pass, token);
        }
        else
        {
          return;
        }
      }
    }
  }
  // close file
  fclose(fp);
}


/**
 * Open database connection.
 */
static void open_db()
{
  EXEC SQL CONNECT TO :db_name USER :db_user USING :db_pass;
}

/**
 * Close database connection.
 */
static void close_db()
{
  EXEC SQL CONNECT RESET;
}

/**
 * Test database connection.
 */
static void test_connection()
{
  printf(":: TEST - CONNECTING TO DATABASE\n");
  open_db();
  // check returned sqlcode
  if (sqlca.sqlcode == 0)
  {
    printf(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE\n");
    close_db();
    printf(":: TEST - DISCONNECTED FROM DATABASE\n");
  }
  else
  {
    printf(":: TEST - FAILED CONNECTED TO DATABASE\n");
    printf(":::: ERROR SQLCODE %i\n", sqlca.sqlcode);
  }  
}

/**
 * Execute function from batch input.
 * @param arr array of strings
 * @param size length of p array
 */
void batch_execute(char** arr, int size)
{
  if (size < 0)
  {
    printf("Invalid parameter count");
  }
  else
  {
    printf("\n");    
    if (strcmp(arr[0], "#newCustomer") == 0)
    {
      newCustomer(arr[1], arr[2], arr[3], arr[4]);
    }
    else if (strcmp(arr[0], "#openAccount") == 0)
    {
      openAccount(arr[1], arr[2], arr[3]);
    }
    else if (strcmp(arr[0], "#closeAccount") == 0)
    {
      closeAccount(arr[1]);
    }
    else if (strcmp(arr[0], "#deposit") == 0)
    {
      deposit(arr[1], arr[2]);
    }
    else if (strcmp(arr[0], "#withdraw") == 0)
    {
      withdraw(arr[1], arr[2]);
    }
    else if (strcmp(arr[0], "#transfer") == 0)
    {
      transfer(arr[1], arr[2], arr[3]);
    }
    else if (strcmp(arr[0], "#accountSummary") == 0)
    {
      accountSummary(arr[1]);
    }
    else if (strcmp(arr[0], "#reportA") == 0)
    {
      reportA();
    }
    else if (strcmp(arr[0], "#reportB") == 0)
    {
      reportB(arr[1], arr[2]);
    }
    else
    {
      printf("Could not find function to batch_execute: %s", arr[0]);
    }
    printf("\n");
  }
}

/**
 * Run batch input given filename.
 * @param filename name of properties file
 */
static void batch_run(char filename[])
{
  // open file
  FILE *fp = fopen(filename, "r");
  // print error if file cannot be opened
  if (fp == NULL)
  {
    printf("Cannot open properties file %s\n", filename);
  }
  else
  {
    int batch_mode = 0;
    char *cur_line = (char *) malloc(256);
    // read each line
    while ( fscanf(fp, "%s", cur_line) != EOF)
    {
      char *line_copy = (char *) malloc(256);
      char *token;
      // copy current line
      strcpy(line_copy, cur_line);
      if (batch_mode == 0)
      {
        // tokenize line and find batch key
        while ( (token = strsep(&line_copy, "=")) != NULL )
        {
          if ( strcmp(token, BATCH_KEY) == 0)
          {
            batch_mode = 1;
          }
          else
          {
            break;
          }
        }
      }
      else if (batch_mode == 1)
      {
        char *token;
        char **token_arr = (char **) malloc(sizeof(char*) * 1);
        int token_cnt = 0;
        // get each token and save to array
        while ( (token = strsep(&line_copy, ",")) != NULL )
        {
          token_arr = (char **) realloc(token_arr, (token_cnt + 1) * sizeof(char*));
          token_arr[token_cnt] = (char *) malloc( strlen(token) + 1 );
          strcpy(token_arr[token_cnt], token);
          token_cnt++;
        }
        // run function
        batch_execute(token_arr, token_cnt);
      }
    }
  }
  // close file
  fclose(fp);
}

int main(int argc, char *argv[])
{
  if (argc < 2)
  {
    printf("Need properties file\n");
  }
  else
  {
    init(argv[1]);
    test_connection();

    open_db();
    batch_run(argv[1]);
    close_db();
  }
  return 0;
}
