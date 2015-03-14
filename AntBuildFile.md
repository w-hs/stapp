# Introduction #

1. Ant

1.1 Installation

1.2 Konfiguration


2. Automatisierte APK-Erstellung

2.1 signed apk

2.2 unsigned apk


3. Javadoc


4. Reverse build process

5. Update Build-File


# 1 Ant #
In der Seminarphase zum Thema Build-Werkzeuge entschieden wir uns für das Werkzeug Apache Ant. Ant bietet den Vorteil, dass wir auf die vorgegebene Projektstruktur von Android reagieren können. Im Gegensatz dazu hätten wir komplexe Konfigurationen beim Werkzeug Maven durchführen müssen.

## 1.1 Installation ##

http://ftp.halifax.rwth-aachen.de/apache//ant/binaries/apache-ant-1.9.0-bin.zip

Entpacken z.B. nach C:\Program Files\apache-ant-1.9.0

## 1.2 Konfiguration ##

Umgebungsvariablen einstellen:

1.2.1) Durch Semikolon getrennt an PATH anhängen: C:\Program Files\apache-ant-1.9.0\bin

1.2.2) ANT\_HOME = C:\Program Files\apache-ant-1.9.0

1.2.3) ANT\_OPS = -Xmx256M

1.2.4) Testen in Konsole mit **ant -version**


# 2 Automatisierte APK-Erstellung #
## 2.1 signed apk ##
In der Konsole ins Projekt-Verzeichnis von _STAPP_ wechseln und den Befehl **ant debug** eingeben.

File ist im erstellten Ordner bin enthalten.

## 2.2 unsigned apk ##
In der Konsole ins Projekt-Verzeichnis von _STAPP_ wechseln und den Befehl **ant release** eingeben.

File ist im erstellten Ordner bin enthalten.


# 3 Javadoc #
In der Konsole ins Projekt-Verzeichnis von _STAPP_ wechseln und den Befehl **ant Javadoc** eingeben.

Javadoc wurde im Verzeichnis bin/Javadoc erstellt.


# 4 Reverse build process #
Um den Build-Process rückgängig zu machen, in der Konsole im Projekverzeichnis den Befehl **ant clean** eingeben.

# 5 Update Build-File #

**android update project --path .**

Dieser Befehl updated das Build-File. Zum erstmaligen anlegen eines Projektes, musste ich ebenso diesen Befehl nutzen.