# Introduction #

Die Datenhaltung erfolgt mit Hilfe des in Android bereit gestellten Datenbankmanagementsystem SQLite in der Version 3.5.
Folgend werden die Relationen und dessen Attribute beschrieben. Desweiteren werden auch die Abhängigkeiten der Relationen untereinander dargestellt.


# Details #
## ER - Modell ##

![https://stapp.googlecode.com/svn/wiki/images/Datenbankschemaentwurf.png](https://stapp.googlecode.com/svn/wiki/images/Datenbankschemaentwurf.png)



## Relationales Modell ##

### TrainingSessions ###
  * _**id** : int (Primary Key, auto increment, not Null)
  * Date: long (not Null)
  * DurationInMs : long (not Null)
  * DistanceInMeters : long (not Null)_

### SessionDetails ###
  * _**id** : int (Primary Key, auto increment, not Null)
  *_TrainingsSeesionsIdAsFK