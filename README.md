# WebApplication-EvolutionEngine Timetable


An effortless way to find the best schedule that's meet your school workers and classes requirements.



#####   SYSTEM REQUIREMENTS  #####

# make sure you already install Apache Tomcat ver 8.5.x



##### RUNNING #####

1. Download and unpack the war file from git

2. Copy the WEB_war.war file to %CATALINA_HOME%\webapps ( E.g.: C:\tomcat\webapps )

3. Run %CATALINA_HOME%\bin\startup.bat

4. Go to %CATALINA_HOME%\conf\server.xml and take the port for the HTTP protocol. <Connector port="8080" ... />. The default value is 8080.

5. Access the following URL: http://localhost:8080/WEB_war/



* ALGORITHM INFO
individual - a TT solution
fitness - the grade of a solution (range of 0 – 100)
population - a group of solutions
each generation contains a population



