package com.example.proyecto.controller;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    private final String host= "smtp.gmail.com";
    private String user="sorware.grupo1@gmail.com";
    private String pass= "grupo1.sorware";
    private String to;
    private InternetAddress[] address;


    public int emailRecuperarCuenta(String emailTo, String hash, String ipAdd, int localPort, String context) throws MessagingException {
        //Propiedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.starttls.requierd","true");
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","587");

        //Sesion con autenticador
        Authenticator authenticator = new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,pass);
            }
        };

        //Sesion que le paso las propiedades
        Session mailSession = Session.getInstance(properties,authenticator);

        //Crea mensaje y emisor
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(user));

        //Receptor toma la llamada de la función
        to=emailTo;
        address= new InternetAddress[]{new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO,address);

        //Asunto y mensaje
        msg.setSubject("SOLICITUD DE RECUPERACIÓN DE CUENTA");
        msg.setText("Usted ha pedido recuperar su cuenta del sistema cambiando su contraseña \n" +
                "Ingrese a este link para actualizar su contraseña:" + ipAdd + ":" +
                localPort + "/Proyecto/controller/SystemController?hasheado=" + hash);

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
        transporte.close();

    return 0;
    }
}
