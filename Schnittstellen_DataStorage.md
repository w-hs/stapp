Es werden alle Schnittstellen beschrieben welche den Zugriff auf die Datenbank unterstützt.

# Schnittstellen DataStorage #

![https://stapp.googlecode.com/svn/wiki/images/DataStorage.png](https://stapp.googlecode.com/svn/wiki/images/DataStorage.png)


## DatabaseConnector ##
### Konstanten ###
Die Konstanten enthalten die Namen der zugehörigen Tabellen mit deren Attributen. Die Definition dieser als Konstanten vereinfachen das aktualisieren der Implementierung bei Änderungen innerhalb der DB.

### Methoden der Klasse ###

**Konstruktor**

Es erfolgt der Aufruf des Konstruktors der Parent Klasse SQLIteOpenHelper. Ob die Datenbank bereits vorhanden oder diese zu erstellen ist, hängt vom übergebenen Namen ab.

**onCreate(SQLiteDatabase)**

Die Methode wird gerufen wenn die mit Namen angegebene Datenbank noch nicht im Application Storage des Mobiltelefons vorhanden ist. In diesem Fall wird die Datenbank angelegt und der Code innerhalb der onCreate() Methode ausgeführt. Darum sind hier die CREATE TABLE - Anweisungen implementiert.

**onUpgrade(SQLiteDatabase)**

Die Methode wird ausgeführt sobald sich die Version der Datenbank ändert.
Die Methode wird von uns zur Zeit nicht verwendet.


## StappDbAdapter / Interface: DatabaseAdapter ##
Die Klasse StappDbAdapter implementiert das Interface DatabaseAdapter.

### Methoden ###
**Konstruktor**

Erstellt eine Verbindung zur Datenbank indem eine Instanz der Klasse DatabseConnector erstellt wird. Mit den Methoden openDatabase() und closeDatabase() kann der Datenbank Zugriff entsprechend geöffnet oder geschlossen werden.

**openDatabase()**

Es wird eine Datenbankverbindung mit schreibendem Zugriff geöffnet.

**closeDatabase()**

Schließt die Verbindung welche mit openDatabase() geöffnet wurde.

**newTrainingSession()**

Diese Methode erzeugt einen Eintrag in der Relation TrainingsSessions. Dieser beinhaltet lediglich das aktuelle Datum und belegt die restlichen Felder mit Null, die _id wird von der Datenbank automatisch generiert. Nach erfolgreichem Eintrag wird eine TrainingSession Objekt mit den Werten aus der Datenbank instantiiert und zurückgegeben._

**updateTrainingSession(TrainingSession)**

In dieser Methode wird eine existierende Trainingseinheit um die fehlenden Werte (Dauer, Distanz) ergänzt.

**storeSessionDetail(SessionDetail)**

Legt zu einer bestehenden Trainingseinheit ein Datenpaket mit Detailinformationen (u.a. Herzrate, Zeit, Schritte) an.

**deleteTrainingSession(int)**

Löscht die TrainingSession zur übergebenen _id. Zuvor werden alle zugehörigen Einträge in der Relation SessionDetails entfernt._

**getSessionHistory()**

Liefert eine Liste mit allen Einträgen aus der Relation TrainingSessions.

**getSessionDetails(int)**

Liefert eine Liste mit allen Einträgen aus der Relation SessionDetails zur entsprechenden _id. Die_id wird der Methode als int übergeben.