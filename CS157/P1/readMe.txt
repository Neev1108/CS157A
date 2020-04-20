To run my program, just go to the directory and then run: 
java -cp :./db2jcc4.jar ProgramLauncher sample-db.properties

This should show results of the batch input provided by the professor and then followed by the prompts for the userInterface. If just the UI needs to be tested, then comment out BatchInputProcess.run() in the program launcher. I also put a show customers method on the top so the table is viewable when grading the code. 

Confessions: I was not able to create a view for my reportA and then select from that view. Instead I just selected from the table. The reason was because I kept getting an error for the same exact statement I used in my java code, but it would not work on my view for some reason.


I also could not rename my ProgramLauncher class to p1 because of some weird error. When I changed the name, it would not be able to find the class, no matter how much I did. 