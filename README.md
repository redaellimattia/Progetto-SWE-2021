
# Progetto di Ingegneria Del Software - A.A. 2020/2021

<p>
  <img src="src/main/resources/img/javaFX/background.jpg">
</p>

Scopo del progetto è quello di implementare il gioco da tavola [Masters of Renaissance](http://craniointernational.com/products/masters-of-renaissance/) utilizzando il pattern architetturale `MVC` Model View Controller.  
Sono implementate le regole complete del gioco, e sono presenti due interfacce utente, sia a linea di comando (CLI) che grafica (GUI).  
La rete è stata gestita tramite socket. Sono inoltre presenti le funzionalià aggiuntive per le partite multiple e per la resilienza alle disconnessioni

## Documentazione

### UML
- [UML Alto Livello](/deliverables/UML/UML)
- [UML Dettaglio](/deliverables/UML/Detailed%20UML)

### JavaDoc
Documentazione di metodi Java: [Javadoc](/deliverables/JavaDoc)

### Librerie e Plugins
|Libreria/Plugin|Descrizione|
|---------------|-----------|
|__maven__|strumento di gestione build automation|
|__junit__|Java unit testing|
|__gson__|libreria per il supporto al parsing di file in formato json|
|__JavaFx__|libreria grafica di Java|

## Funzionalità

- Regole Complete
- CLI
- GUI
- Socket

## Funzionalità aggiuntive

- Partite multiple
- Resilienza alle disconnessioni

## Coverage dei casi di test
Aggiornata al 25 Giugno.
<p>
  <img src="/deliverables/coverage/coverage 25-06.png">
</p>

## Esecuzione dei JAR
Per l'avvio é necessario solamente aver installato il `JDK` di JAVA.   
Per lo sviluppo abbiamo utilizzato il `JDK 15` .

I JAR sono disponibili [qui](/deliverables/jar).  

### Server
- `GC47-server.jar`  
Il comando da eseguire da command line é il seguente:
```bash 
  java -jar ./GC47-server.jar [-p port]
```
#### Parametri
- `-p`: porta che si vuole utilizzare in ascolto, di default `65500`.  
Assicurarsi che la porta da utilizzare in ascolto sia disponibile.

### Client
- `GC47-client.jar`  
Il comando da eseguire da command line é il seguente:
```bash 
  java -jar ./GC47-client.jar [-ip serverip] [-p serverport] [-cli]
```
#### Parametri
- `-ip`: ip del server al quale ci si vuole connettere, di default `localhost`.
- `-p`: porta del server al quale ci si vuole connettere, di default `65500`.
- `-cli`: necessaria per lanciare il client con interfaccia a linea di comando, di default viene avviato con `GUI`.  
É preferibile, in caso di `CLI`, avviare il client con un terminale che supporti la codifica UTF-8 e gli ANSI escape, per una migliore esperienza.

## Gruppo GC47

- [Redaelli Mattia](https://github.com/redaellimattia)
- [Rivi Gabriele](https://github.com/GabrieleRivi)
- [Rondini Luca](https://github.com/LucaRondini)
