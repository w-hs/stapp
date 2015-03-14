# Coding Conventions #

## Warum Coding Conventions? ##
Bei der Softwareentwicklung im Team sollen Code Konventionen dazu beitragen, im einheitlichen Stiel zu coden. Zudem erhöhen die Konventionen die Lesbarkeit und damit auch die Verständlichkeit des Codes, was für die Arbeit im Team enorm wichtig ist.

## Checkstyle ##
Damit die Konventionen nicht zur Last und automatisch von jedem Developer eingehalten werden, werden wir in diesem Projekt Checkstyle einsetzen.


### Checkstyle einrichten ###
Um Checkstyle in Eurem Eclipse installieren zu können geht Ihr wie gewohnt auf _Install New Software_ und tragt folgende Update Site ein:
http://eclipse-cs.sf.net/update/

Die Checkstyle Konfiguration nehme ich dann selbst am Projekt vor.


### Checkstyle abschnittsweise deaktivieren ###
Unter Umständen gibt es Bereiche im Code, die von Checkstyle nicht überprüft werden sollen. Beispielsweise, weil die Namensgebung von einem SDK vorgegeben sind...
In diesem Fall kann die Checkstyle Überprüfung wie folgt deaktiviert werden:
```
//CHECKSTYLE:OFF
Code Abschnitt
//CHECKSTYLE:ON
```
## Code Conventions ##

### Language ###
Code wird ausschließlich in englischer Sprache verfasst. Deutsche Bezeichner sind nicht erlaubt. Kommentare werden in Deutsch geschrieben.

### Packages ###
Paketnamen sind ebenfalls Bezeichner und werden daher in Englisch verfasst. Pakete gruppieren logische bzw. zusammenhängende Bereiche.

Als Namespace wird folgender Bezeichner verwendet: `de.whs.stapp.PackageName`

### Source Files ###
Pro Source File wird genau eine Typ- bzw. Klassendeklaration abgelegt. Damit sind mehrere Klassen in einer Datei nicht erlaubt.

### Comments ###
Kommentare werden mit JavaDoc in deutsch verfasst. Dokumentiert werden alle Klassen, Funktionen und Enums unabhängig von ihrem Zugriffsmodifikator!
Nicht dokumentiert werden Variablen bzw. Membervariablen.

**Kommentare im Code:**
Allgemein gilt, dass sich gut geschriebener (sauberer) Code selbst erklärt. Sollte es dennoch Code Stellen geben, an denen dies aus bestimmten Gründen nicht der Fall ist, wird das in einem Kommentar begründet.

### Naming / Wording ###
**Sprechende Namen verwenden:** Bitte denkt daran, eure Klassen und Funktionen so zu benennen, dass man ihren Nutzen bzw. ihre Aufgabe, sowie ihre Daseinsberechtigung am Namen erkennen kann.

Allgemein wollen wir uns bei der Namensgebung an den Java Code Conventions orientieren:
http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-135099.html#367

### Preconditions ###
Parameter sollten vor ihrer Verwendung validiert werden.

http://docs.oracle.com/javase/1.4.2/docs/guide/lang/assert.html#preconditions
Öffentlichen Schnittstellen werfen also entsprechende Exceptions und verwenden keine Assertions.

## Check Ins ##
Bei jedem Check In **muss** ein Kommentar angegeben werden! Der Kommentar wird auf Deutsch verfasst. Bezieht sich der Check In auf einen Technical Task so wird seine Id in der Form #id "Task Caption" angegeben. Wichtig ist, dass neben der Id auch die Bezeichnung für den Task angegeben wird, da der Text wesentlich sprechender ist, als die Id.