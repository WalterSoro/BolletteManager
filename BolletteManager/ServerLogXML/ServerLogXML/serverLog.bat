@echo off
mkdir C:\prg\myapps\ServerLogXML\build\classes
C:\prg\jdk8\bin\javac C:\prg\myapps\ServerLogXML\src\*.java -cp C:\prg\myapps\ServerLogXML\build\classes -d C:\prg\myapps\ServerLogXML\build\classes

cd C:\prg\myapps\ServerLogXML\build\classes
C:\prg\jdk8\bin\java ServerLogXML
cd C:\prg\myapps\ServerLogXML

pause
