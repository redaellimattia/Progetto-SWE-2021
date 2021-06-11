
# Progetto di Ingegneria Del Software - A.A. 2020/2021

<p>
  <img src="/utilities/coverage/coverage 10-06.png">
</p>

Scopo del progetto è quello di implementare il gioco da tavola [Masters of Renaissance](http://craniointernational.com/products/masters-of-renaissance/) utilizzando il pattern architetturale `MVC` Model View Controller.  
Sono implementate le regole complete del gioco, e sono presenti due interfacce utente, sia a linea di comando (CLI) che grafica (GUI).  
La rete è stata gestita tramite socket. Sono inoltre presenti le funzionalià aggiuntive per le partite multiple e per la resilienza alle disconnessioni

## Funzionalità

- Regole Complete
- CLI
- GUI
- Socket

## Funzionalità aggiuntive

- Partite multiple
- Resilienza alle disconnessioni

## Esecuzione dei JAR
Per l'avvio é necessario solamente aver installato il `JDK` di JAVA.   
Per lo sviluppo abbiamo utilizzato il `JDK 15` .

I JAR sono disponibili [qui](https://github.com/).  

### Server
- `GC47-server.jar`  
Il comando da eseguire da command line é il seguente:
```bash 
  java -jar ./GC47-server.jar [-p port]
```
#### Parametri
- `-p`: porta che si vuole utilizzare in ascolto.

### Client
- `GC47-client.jar`  
Il comando da eseguire da command line é il seguente:
```bash 
  java -jar ./GC47-client.jar [-ip serverip] [-p serverport] [-cli]
```
#### Parametri
- `-ip`: ip del server al quale ci si vuole connettere.
- `-p`: porta del server al quale ci si vuole connettere.
- `-cli`: opzione necessaria per lanciare il client con interfaccia a linea di comando, di default viene avviato con `GUI`.
    
## Coverage dei casi di test
Aggiornata al 10 Giugno.
<p>
  <img src="/utilities/coverage/coverage 10-06.png">
</p>

## Gruppo GC47

- [Redaelli Mattia](https://github.com/redaellimattia)
- [Rivi Gabriele](https://github.com/GabrieleRivi)
- [Rondini Luca](https://github.com/LucaRondini)
