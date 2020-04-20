 
Connect to cs157a;
insert into hw3.class values ( '10000', 'CS46A', 'Intro to Prog' );
insert into hw3.class values ( '10001', 'CS46B', 'Intro to Data Str' );
insert into hw3.class values ( '10002', 'CS47', 'Intro to Comp Sys' );
insert into hw3.class values ( '10003', 'CS49J', 'Java Prog' );
insert into hw3.class values ( '20000', 'CS146', 'Data Str & Algorithm' );
insert into hw3.class values ( '20001', 'CS157A', 'Intro to DBMS' );
insert into hw3.class values ( '30000', 'MATH46', 'Another math class' );
insert into hw3.classreq values ( '10001', '10000', 'F' );
insert into hw3.classreq values ( '10001', '10003', 'F' );
insert into hw3.classreq values ( '10001', '30000', 'T' );
insert into hw3.classreq values ( '10002', '10001', 'F' );
insert into hw3.classreq values ( '20000', '10001', 'F' );
insert into hw3.classreq values ( '20001', '20000', 'F' );

insert into hw3.student values('01187','Neeval', 'Kumar');

--should activate trigger for Pre Req
Insert into hw3.schedule values('01187', '20000', 'F', 2017);

--should activate trigger for Co-Req
Insert into hw3.schedule values('01187', '10001', 'F', 2016);  
Terminate;


Terminate;




