# Transactions

---
## How to run
Come prima cosa è necessaria una build del progetto con `mvn clean package`
- ##### IntelliJ
È fornita la run configuration `Compose up` per creare le immagini automaticamente e fare partire i container
- ##### Terminale
Eseguire i comandi `docker compose build` e `docker compose up -d`, `docker compose down` per arrestare i container.

---
Una volta up and running sarà possibile raggiungere il servizio sulla porta 8080

Sono esposte [swagger-ui](http://localhost:8080/swagger-ui.html) per semplificare chiamate di test e un'istanza di [adminer](http://localhost:8090/) per poter osserver le tabelle comodamente.
è fornita anche 

Il database è già inizializzato con dei dati aleatori.