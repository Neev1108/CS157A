connect to cs157a;
ALTER TABLE HW3.Class
ADD CONSTRAINT validClassID 
CHECK(classid LIKE '%[^0123456789]%');
terminate;

