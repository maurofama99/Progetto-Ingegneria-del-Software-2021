# Prova Finale Ingegneria del Sofware 2020/21

Obiettivo del progetto è lo sviluppo di un complesso artefatto software a conclusione del percorso formativo. Lo studente deve dimostrare, tramite l’elaborato e opportune prove in laboratorio, la propria conoscenza dei linguaggi, metodi e strumenti introdotti nelle esercitazioni e nell'ambito degli altri insegnamenti del corso di studi, con particolare riferimento all'insegnamento di ingegneria del software, e la propria capacità di utilizzarli per la progettazione orientata agli oggetti del software. 
La prova finale consiste nella preparazione, in autonomia, di un elaborato software da svolgere in Java. Il tema dell'elaborato, proposto dal docente, sarà di carattere interdisciplinare e riassuntivo dell'intero triennio, ma con particolare riferimento alle metodologie dell'ingegneria del software.

## Implementazione

Il progetto consiste nello sviluppo di una versione software del gioco da tavolo [Maestri del Rinascimento](http://www.craniocreations.it/wp-content/uploads/2021/04/Lorenzo_Cardgame_Rules_ITA_small-3.pdf).
Il progetto include:
* diagramma UML ad alto livello dell’applicazione ([link](https://github.com/maurofama99/Progetto-Ingegneria-del-Software-2021/blob/main/deliverables/final/UML/Summary_UML.png));
* diagrammi UML finali che mostrino come è stato progettato il software ([link](https://github.com/maurofama99/Progetto-Ingegneria-del-Software-2021/tree/main/deliverables/final/UML/Final%20UML));
* implementazione funzionante del gioco conforme alle regole del gioco;
* codice sorgente dell’implementazione ([link](https://github.com/maurofama99/Progetto-Ingegneria-del-Software-2021/tree/main/src/main/java/it/polimi/ingsw));
* codice sorgente dei test di unità ([link](https://github.com/maurofama99/Progetto-Ingegneria-del-Software-2021/tree/main/src/test/java/it/polimi/ingsw)).

### Funzionalità implementate

* Regole complete
* CLI + GUI
* Socket
* __2 Funzionalità avanzate__
    * Partite multiple
    * Partita locale

## Istruzioni per l'esecuzione

### Server
Per lanciare il server di Maestri del Rinascimento, posizionarsi nella directory /deliverables/final/jar e lanciare da terminale il seguente comando:

``
      java -jar server.jar -port <port>
``

In cui portNumber indica la porta in cui il server si metterà in ascolto.

### Client
Per lanciare il client di Maestri del Rinascimento posizionarsi nella directory /deliverables/final/jar e, a seconda della modalità di gioco desiderata, lanciare il seguente comando:

``
java -jar client.jar [-cli -port portNumber | -gui | -local [-cli | -gui]]
``

In cui portNumber indica la porta alla quale il client si connetterà.

## Sviluppatori

[Valeria Detomas](https://github.com/valeriadetomas)

[Mauro Famà](https://github.com/maurofama99)

[Christian Fabio Grazian](https://github.com/grazcri)



*Tutti i diritti delle risorse grafiche sono riservati e appartengono alla Cranio Creations


