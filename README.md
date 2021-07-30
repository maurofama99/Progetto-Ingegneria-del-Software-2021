# Prova Finale Ingegneria del Sofware 2020/21

Obiettivo del progetto è lo sviluppo di un complesso artefatto software a conclusione del percorso formativo. Lo studente deve dimostrare, tramite l’elaborato e opportune prove in laboratorio, la propria conoscenza dei linguaggi, metodi e strumenti introdotti nelle esercitazioni e nell'ambito degli altri insegnamenti del corso di studi, con particolare riferimento all'insegnamento di ingegneria del software, e la propria capacità di utilizzarli per la progettazione orientata agli oggetti del software. 
La prova finale consiste nella preparazione, in autonomia, di un elaborato software da svolgere in Java. Il tema dell'elaborato, proposto dal docente, sarà di carattere interdisciplinare e riassuntivo dell'intero triennio, ma con particolare riferimento alle metodologie dell'ingegneria del software.

## Funzionalità implementate

* Regole complete
* CLI + GUI
* Socket
* __2 Funzionalità avanzate__
    * Partite multiple
    * Partita locale

## Istruzioni per l'esecuzione

### Server
Per lanciare il server di Maestri del Rinascimento, posizionarsi nella directory /deliverables/final/jar e lanciare da terminale il seguente comando:
```bash
java -jar server.jar -port <port>
```
In cui portNumber indica la porta in cui il server si metterà in ascolto.

### Client
Per lanciare il client di Maestri del Rinascimento posizionarsi nella directory /deliverables/final/jar e, a seconda della modalità di gioco desiderata, lanciare da terminale uno dei seguenti comandi:
#### CLI 
```bash
java -jar client.jar -cli -port <port>
```
In cui portNumber indica la porta alla quale si connetterà il client
#### GUI
```bash
java -jar client.jar -gui
```
#### Partita locale
##### CLI
```bash
java -jar client.jar -local -cli
```
##### GUI
```bash
java -jar client.jar -local -gui
```
## Sviluppatori

[Valeria Detomas](https://github.com/valeriadetomas)

[Mauro Famà](https://github.com/maurofama99)

[Christian Fabio Grazian](https://github.com/grazcri)







