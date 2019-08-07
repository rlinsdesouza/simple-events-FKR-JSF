package br.edu.ifpb.simpleevents.entity.pattern.singleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogSingleton {
	private static LogSingleton instancia = null;
    private LogSingleton(){ }

    public synchronized static LogSingleton getInstance(){
        if (LogSingleton.instancia == null){
        	LogSingleton.instancia = new LogSingleton();
        }
        return LogSingleton.instancia;
    }

    public void escrever(String texto){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        String data = dateFormat.format(date);
        System.out.println(data + " log: "+ texto);
    }
}
