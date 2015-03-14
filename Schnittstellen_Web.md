# Introduction #

Diese Seite beschreibt, wie der Android-Container mit der Web-App kommunizieren kann und umgekehrt.

# Allgemeiner Aufbau #
![https://stapp.googlecode.com/svn/wiki/images/Schnittstelle_Web2.png](https://stapp.googlecode.com/svn/wiki/images/Schnittstelle_Web2.png)

# Container Anwendung #

## StappWebView ##

Die StappWebView ist von der Android WebView abgeleitet und bietet somit alle bekannten Funktionen einer WebView (z.B. loadUrl). Zusätzlich beinhaltet die StappWebView noch Initialisierungsfunktionen. Dabei werden Javascript-Funktionen aktiviert. Dazu werden noch der [#StappWebChromClient](#StappWebChromClient.md) und der [#StappWebViewClient](#StappWebViewClient.md) instantiiert. Abschließend wird das übergebene [#StappWebAppInterface](#StappWebAppInterface.md) in das Javascript injiziert und an das "Android"-Javascript-Objekt gebunden.

### TrainingSessionWebView ###
Beinhaltet zusätzlich zu den geerbten Funktionen der [#StappWebView](#StappWebView.md) den Aufruf der Webseite und das einrichten und zur Verfügung stellen der [#Javascript](#Javascript.md) Funktionen. Javascript Funktionen können dabei wie folgt aufgerufen werden: "javascript:stapp.function()".

### HistoryWebView ###
Siehe [#TrainingSessionWebView](#TrainingSessionWebView.md)

## StappWebViewClient ##
Bietet die Möglichkeit auf Ereignisse in der WebView zu reagieren. Dazu zählen zum Beispiel "shouldOverrideUrlLoading", oder "onPageFinished".

## StappWebChromeClient ##
Fängt Funktionen aus dem Javascript auf. Dazu zählen "onConsoleMessage", oder "onJSAlert". Dadurch werden Log-Messages aus dem Javascript direkt in den Java-Log(logcat) geschrieben.

## StappWebAppInterface ##
Das WebAppInterface bietet Javascript die Möglichkeiten native Funktionen aufzurufen. Dazu werden Java-Funktionen definiert, welche an ein Javascript-Objekt gebunden werden. Standardmäßig ist das Erstellen eines Android-Toast in das Interface eingebaut.

### TrainingSessionWebAppInterface ###
Stellt speziell für die Trainingseinheit-Webseite native Funktionen zur Verfügung.

### HistoryWebAppInterface ###
Stellt speziell für die Verlauf-Webseite native Funktionen zur Verfügung.

# Javascript #
Im Javascript gibt es zwei für die Kommunikation wichtige Objekte: stapp und Android

## Android ##
Das Android-Objekt enthält alle Funktionen die vom WebAppInterface bereit gestellt wurden und somit nativ in der Container-Anwendung ausgeführt werden können. (Z.B.: Android.showToast("Hallo"))

## stapp ##
Alle Funktionen von stapp sind in Javascript in ein Objekt gekapselt. Dadurch wird das überschreiben von Funktionen vermieden. Diese Funktionen können dann aus der Android-Container-Anwendung aufgerufen werden. (Z.B.: loadUrl("javascript:stapp.function()"). Wenn Parameter an die Funktion übergeben werden sollen, müssen diese als JSON-Strings serialisiert werden. Dazu stellt die Klasse Javascript im Paket "de.whs.stapp.presentation.helper" eine "toJSON"-Methode zur Verfügung- Darüber hinaus bietet die Klasse auch die Möglichkeit Javascript-Funktionsaufrufe direkt generieren zu lassen.