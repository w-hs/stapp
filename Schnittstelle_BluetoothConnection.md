# Intro #

Diese Seite beschreibt alles rund um die BluetoothConnection.

## Konstellation ##

https://stapp.googlecode.com/svn/wiki/images/BluetoothConnection.PNG

# Details #

Die Klasse _BluetoothConnection_ kümmert sich um den Verbindungsaufbau mit dem BluetoothDevice (Sensor) und interagiert diesbzgl. mit dem Anwender (z.B. das Einschalten des Bluetooth).
Die Klasse wird in der Activity instanziiert und verfügt über den angehängten **DataTracker** _HxMConnectedListener_.

## BluetoothConnection(Context) ##
Dem Konstruktor ist der App-Context mitzugeben, damit die Klasse Interaktionen mit dem Benutzer initieren kann.

## isOpen() ##
Übergibt den aktuellen Status der Verbindung.

## open() ##
Diese Methode ist wohl das Herzstück der Klasse. In folgender Reihenfolge werden Aktionen durchgeführt:
  * sämtliche BroadcastReceiver werden angelegt.
  * BluetoothAdapter wird auf Existenz geprüft.
  * Bluetooth ggfs. eingeschaltet.
  * BluetoothDevice in gepaarten Geräten gesucht und ggfs. Verbindung aufgebaut.
  * Ansonsten der DiscoveryScan angestoßen.

Sind alle Aktionen durchgeführt, existiert hoffentlich eine Verbindung mit dem Device. Treten Fehler auf werden diese geloggt oder teilweise an die Oberfläche gebracht (Toastmessage).

## close() ##
Diese Methode räumt am Ende wieder auf. Deregistiert BroadcastReceiver, kappt die Verbindung zum DataTracker, schließt den BtClient und Schalter ggfs. das Bluetooth wieder ab.

## REQUEST\_CODE ##
Dieser Code wird bei dem Einschalten des Bluetooth als RequestCode mit übergeben. Mittels diesen RequestCodes kann in der StappActivity in der Methode OnActivityResult(requestCode, resultCode, intent) auf die Interaktion mit dem Benutzer reagiert werden.

## Sonstige Methoden ##
Die restlichen Methoden haben absolut sprechende Namen und bedürfen keiner zusätzlichen Erläuterung.