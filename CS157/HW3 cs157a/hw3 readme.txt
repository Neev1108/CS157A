Hw3 readme

For this assignment, I began by creating the keys. In student, the primary key is studentid and class id for class. This solves question 1.

For question 2, I just made the child tables, class req and schedule cascade on delete.

For question 3, we do not want to add to schedule if the class id or student id is not in table student and table class respectively. By assigning foreign keys from the child tables referencing to the primary keys in the parent table, this disallows insertion into the child table.

For Question 4, I struggled a lot to make the trigger. I got the logic down pretty quickly. I just had a really hard time with syntax. After about 5 hours of work, I finally figured out my problem lay with the limiters. To fix this problem, I made a separate trigger script on a text file and ended all my statements with ;-- Thus there should be 5 text files I turned in, not 4.

For my drop file, I had a weird error when trying to drop my primary and foreign key constraints where it was stating that my constraint name was undefined. I'm not exactly sure why it was giving me that error, granted I even tried copy pasting the constraint name. Not really a problem, granted that dropping a table literally drops all the checks and constraints, but it was still weird nonetheless. 

Also for my trigger.sql, whenever I tried to copy paste into the vi file, it would convert my single quotes to ?~@~Y. I tried searching up solutions to this problem on google and could not find anything. I don't know how the HW will be graded, but if the single quotes are changed, then just a heads up. 

My scripts should be run in this order:
1. create.sql - create table and DDL's
2. trigger.sql - actual trigger test
3. hw3.sql -test inserts and trigger test
4. drop.sql -drops everything
