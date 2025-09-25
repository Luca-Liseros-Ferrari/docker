#!/bin/sh

# Esegui l'inizializzazione del database (esempio)
java -cp /usr/local/tomcat/MIDORIBEATS-jar-with-dependencies.jar com.midoriPol.MainApplication

# Avvia Tomcat dopo un breve ritardo
sleep 10
catalina.sh run
