# RedditScalaWebScrapper

Scrapt die Reddit /r/all Seite und
gibt alles gefundenen Subreddits ohne dublikate als Liste wieder.



## Inhaltsverzeichnis

1. [Voraussetzungen](#voraussetzungen)
2. [Installation und Setup](#installation-und-setup)
3. [Ausführung des Programms](#ausführung-des-programms)


## Voraussetzungen

- **Scala**: Dieses Projekt wurde mit Scala 2.13 entwickelt.
- **Java**: Eine kompatible Java Virtual Machine ist erforderlich.
- **sbt (Scala Build Tool)**: Zum Kompilieren und Ausführen des Programms.
  "brew install sbt"
-**Browser-Driver** es muss ein Browser Driver installiert sein passend zum Betriebssystem


## Installation und Setup

1. Klone das Repository auf deinen lokalen Computer.
2. herunterladen eines Browserdriver
3. im Wahlverzeichnis entpacken
4. setzen der Browserdrivertyp und Browserdriverlokation Variable

Downloadlocation für denn Browserdriver,
Chrom recommended:
https://chromedriver.chromium.org/downloads


## Ausführung des Programms

1. Öffne ein Terminal im Hauptverzeichnis des Projekts.
2. Führe `sbt run` aus, um das Programm zu starten.
