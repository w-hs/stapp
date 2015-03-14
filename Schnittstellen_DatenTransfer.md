# Intro #

Diese Seite beschreibt wie die getrackten Daten an gemeldete Listener übertragen werden.

## Konstellation ##


https://stapp.googlecode.com/svn/wiki/images/DatenTransfer.PNG

# Details #

Ein TrackedDataListener empfängt die geparsten Daten vom DataTracker, nachdem dieser sich bei dem DataTracker registriert hat.

## DataTracker ##
Das Interface DataTracker bringt die Methoden **registerListener(TrackedDataListener)** und **unregisterListener(TrackedDataListener)** für den Registrierungsvorgang mit.
Der DataTracker ist die Klasse, in der unsere Daten vom Sensor empfangen und in unser System eingespeist werden. In dieser Klasse wird ein Daten-Container (TrackedDataItem) an alle registrierten Listener gereicht.
Die Klasse _HxMConnectedListener_ implementiert dieses Interface.

## TrackedDataListener ##
Das Interface TrackedDataListener bringt die Methode **trackData(TrackedDataItem)** mit sich.
In dieser Methode wird der Daten-Container aufgenommen und ist damit im System zur weiteren Verarbeitung vorhanden.
Die Klasse _Training_ implementiert dieses Interface.