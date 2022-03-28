# Guida al progetto

In questo progetto, è stato prodotto un compilatore completo che prenda in input un codice MyFun e lo compili in un
programma C.

## Download e Guida

### Download

- [Intellij Community Edition](https://www.jetbrains.com/idea/download/#section=windows)
- [JFlex](https://jflex.de/download.html)
- [Cup](http://www2.cs.tum.edu/projects/cup/install.php)
- 

### Plugin Intellij

- In IntelliJ -> Andare in Preferences/Plugin ]
- In IntelliJ -> Ricercare "Grammar-Kit" ed attivarlo (permette di semplificare editing in IntelliJ di specifiche jflex)


### Project SDK e Project Language Level
Project SDK: 16 version (16.0.2)

Project Language Level: SDK Default(16 - Records,patterns, local enums and interfaces)

### Guida configurazione JFlex

- Su S.O. (MacOS o Linux) -> Porre la cartella di jflex nella stessa cartella dove è memorizzato il proprio progetto. (
  Per trovare tale cartella, da IntelliJ basta fare right-click su nome progetto e poi selezionare voce "Open
  in”/”Finder".)
- Su S.O. (Windows) -> Rinominare la cartella jflex-1.8.2 in JFLEX e spostarla in C:/
- In IntelliJ – caricare nel progetto questa libreria come external library in IntelliJ - aprire finestra in basso a
  sinistra corrispondente al tab "Terminal"
- In IntelliJ_Terminal -> si lanci il comando ../jflex-1.8.2/bin/jflex -d src srcjflexcup/<nomefile>.flex (MacOS o
  Linux) C:\JFLEX\bin\jflex -d src srcjflexcup\<nomefile>.flex (Windows) ogniqualvolta si vuole compilare il proprio
  srcjflexcup/<nomefile>.flex e porre il lexer prodotto nella cartella src (-d src)

### Guida configurazione Cup

- Su S.O. (MacOS o Linux)- Porre la cartella CUP nella stessa cartella dove è memorizzato il proprio progetto. (Per
  trovare tale cartella, da IntelliJ basta fare right-click su nome progetto e poi selezionare voce "Reveal in Finder"
  all'inizio della seconda metà del menu.)
- Su S.O. (Windows)- Porre la cartella CUP in C:/ Azioni per la generazione del Parser e Symbol
- In IntelliJ – caricare nel progetto il file CUP/java-cup-11b-runtime.jar come external library
- In IntelliJ - Creare una cartella srcjflexcup in modo che risulti fratello della cartella src. In questa cartella
  andranno memorizzati i file di specifica <nomefile>.flex (SI NOTI ESTENSIONE flex e non jflex) e <nomefile>.cup
- In IntelliJ - aprire finestra in basso a sinistra corrispondente al tab "Terminal"
- In IntelliJ_Terminal - si lanci il comando (dopo aver compilato il .flex **)
-

### Guida esecuzione del  Cup

In una prima fase sono stati aggiunte le precedenze e le associatività e sono state aggiunte le regole per la grammatica
presente. Una volta avviato il parser ci siamo resi conto che la grammatica generava il seguente conflitto.

Le seguenti produzioni:StatList -> Stat e Stat -> /*empty*/, producevano un conflitto che è stato risolto rimuovendo quest'ultima produzione (Stat -> /*empty*) e cambiando la prima con StatList -> /*empty*/, in questo modo invece di arrivare a
Stat per andare in /*empty*/ è possibile anticipare restituendo la lista vuota di Stat. A seguito di ciò, il numero dei
conflitti si è azzerato.

## Documentazione

### XMLVisitor

Dopo aver generato L'AST tramite il parser è stato implmenetato XMLVisitor per andare a mostrare l'alberatura che viene
generata leggendo il file test. L'Albero viene generato a partire dalla radice ProgramNode e man mano scorre i vari nodi
fino ad arrivare agli ExpreNode. Ogni volta che incontriamo un nodo viene creato il seguente tag  `<NAMENODE>  </NAMENODE>`.

### ScopingVisitor

Nello Scoping andiamo a inserire le varie tabelle di scope, gli identificatori di variabili e le funzioni. Per quanto
riguarda lo scoping sono state rispettare le seguenti regole:

- A: Se il nodo dell’AST è legato ad un costrutto di creazione di nuovo scope (ProgramOp, FunOp e BlockOp quando figlio
  di ProgramOp, o secondo figlio e terzo figlio di IfOp o figlio di WhileOp)  allora se il nodo è visitato per la prima
  volta crea una nuova tabella, la lega al nodo corrente e la fa puntare alla tabella precedente (fai in modo di
  passare in seguito il riferimento di questa tabella a tutti i suoi figli, per il suo aggiornamento, qui si può anche
  usare uno stack)
- B: Se il nodo è legato ad un costrutto di dichiarazione variabile o funzione (VarDeclOp, ParDeclOp, FunOp) allora se
  la tabella corrente* contiene già la dichiarazione dell’identificatore coinvolto restituisci “errore di
  dichiarazione multipla” altrimenti aggiungi dichiarazione alla tabella.

  *Nota: Se si sceglie di usare lo stack la tabella corrente è quella sul top dello stack.

Nel nostro caso abbiamo utilizzato uno Stack dove andavamo a memorizzare Map<String, RecordTable>.
Durante questa fase è stata anche gestita l'infernza di tipo attraverso una classe InferenceType per andare ad
effettuare l'inferenza tra i vari tipi.

### SemanticVisitor

Le classiche operazioni di Type Checking vengono effettuate su tutte i nodi expr. Come per lo ScopingVisitor, abbiamo
utilizzato uno stack per pushare le tabelle dei simboli legati ai nodi e verificare il tipo del nodo se è presente nella
classe la stessa navigazione dello ScopingVisitor ma in questo caso abbiamo navigato tutta l'alberatura per settare il
tipo di tutti i nodi che poi sarà indispensabile nella fase successiva cioè quella della generazione del codice C.
Durante questa fase siamo andati a modificare spesso la grammatica (parser.cup) perchè avevamo gestito con
il nodo BinOpNode le operazioni di: OR, AND, CONCAT, e RELOP e  questo ha portato dei problemi nella
loro gestione ma poi abbiamo deciso di gestire il tutto con nuovi nodi infatti da come si evince nel cup è possibile
visualizzare i nuovi nodi OrAndNodeOp, RelopNode ed infine RelopNod. E' stata anche realizzata una classe statica per la
gestione del type checking -> TypeCheker.java

### CGenerator Visitor

Nella fase di CGenerator siamo andati a visualizzare tutti i nodi e man mano che si entrava nei nodi siamo andati a
concatenare le stringe necessarie per la generazione del codice. Per quanto riguarda la gestione dei booleani sono stati trattati tramite
l'utilizzo della libreria #include <stdbool.h> perchè il C non gestisce i booleani.

