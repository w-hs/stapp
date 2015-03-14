# Das Interface DataAccess #
Das Interface `DataAccess` ermöglicht sowohl den Zugriff auf bereits protokollierte Daten`, als auch die Möglichkeit ein neues `Training` zu erstellen.

![https://stapp.googlecode.com/svn/wiki/images/InterfaceDataAccess.png](https://stapp.googlecode.com/svn/wiki/images/InterfaceDataAccess.png)

## DataAccessFactory ##
Die Klasse `DataAccessFactory` hat lediglich die Methode `newDataAccess(DataTracker, Context)` und liefert eine neue Instanz des Interfaces `DataAccess` zurück.
Dabei muss der Methode der Android Context und eine Instanz des DataTracker Interfaces übergeben werden.

## getSessionHistory() ##
Die Methode `getSessionHistory()` liefert eine Liste von `TrainingSession` Objekten zurück. Die Liste beinhaltet dabei die in der Datenbank gespeicherten Training Sessions.

## getSessionDetails(int) ##
Die Methode `getSessionDetails(int)` liefert zu einer gegebenen `TrainingSessionId` eine Liste von `SessionDetail` Objekten zurück. Die Liste beinhaltet dabei die in der Datenbank gespeicherten Session Details zur entsprechenden Training Session.

## newTraining() ##
Die Methode `newTraining()` liefert eine neue Instanz der Klasse `Training` zurück.


# Die Klasse Training #
Die Klasse `Training` dient dazu ein Training zu verwalten. Ein Training befindet sich immer in einem definierten Status. Die möglichen Stati werden durch die Enumeration `TrainingState` repräsentiert.

![https://stapp.googlecode.com/svn/wiki/images/ClassTraining2.png](https://stapp.googlecode.com/svn/wiki/images/ClassTraining2.png)

## Training Methoden Überblick: ##
  * `start`: Startet ein neues Training.
  * `stop`: Beendet eine laufendes Training.
  * `pause`: Unterbricht ein laufendes Training.
  * `resume`: Setzt ein unterbrochenes Training vor.
  * `getState`: Liefert den Status des Trainings zurück.
  * `getCurrentSession`: Liefert zum aktuellen Training zugehörige `TrainingSession` Objekt zurück

## Der SessionDetailListener ##
Der `SessionDetailListener` ist ein Interface, das lediglich die Methode `listen(SessionDetail)` definiert. Die Methode dient zur Bereitstellung, der live Daten, die vom Bluetooth Sensor geliefert werden.

Über die Methoden `registerListener` und `unregisterListener` können die Listener registriert und wieder entfernt werden.