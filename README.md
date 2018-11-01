# Home Energy Trading System (COS30018-HETS)


* [Introduction](#introduction)
* [Setup](#setup)
* [Libaries Used](#libraries-used)

## Introduction

In this project, we implemented a Home Energy Trading System that is able to forecast its energy demand
and then negotiate with different retailers to buy or sell energy. Main objective of the system is to
minimize the cost of energy for your home by using smart forecasts and energy usage predictions of the
home appliances to negotiate the best offers/prices with multiple retailers. This project is implemented
in Java using JADE and GUI using Java Swing framework.

## Set Up

### Configuration for Testing


## Libraries Used

 * JADE:  is a Framework which simplifies the implementation of multi-agent systems through a middle-ware
 * JFreeChart:  is Java chart library that makes it easy for developers to display professional quality charts in applications.
 * OpenCSV:  is an easy-to-use CSV (comma-separated values) parser library for Java.
 * Jackson-XML

 ## Build
 Make sure you have Java 8 set up correctly. Then run:
 
 1. ./mvnw clean package
 2. java -jar target/hets-1.0-SNAPSHOT-jar-with-dependencies.jar
 or 
 2. java -jar target/hets-1.0-SNAPSHOT-jar-with-dependencies.jar show-jade-gui 
 to also launch the JADE GUI
	

