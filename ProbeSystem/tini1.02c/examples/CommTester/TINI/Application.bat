@echo off
javac -bootclasspath ..\..\..\bin\tiniclasses.jar -d bin src\*.java
java -classpath ..\..\..\bin\tini.jar;%classpath% TINIConvertor -f bin -o bin\CommTester.tini -d ..\..\..\bin\tini.db



