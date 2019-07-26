package br.edu.ifpb.simpleevents.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingletonLog {
	private static SingletonLog instancia = null;
    private SingletonLog(){ }

    public synchronized static SingletonLog getInstance(){
        if (SingletonLog.instancia == null){
        	SingletonLog.instancia = new SingletonLog();
        }
        return SingletonLog.instancia;
    }

    public void escrever(String texto){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        String data = dateFormat.format(date);
        System.out.println(data + " log: "+ texto);
    }

    public void ler(){

    }
}
