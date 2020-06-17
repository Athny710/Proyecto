package com.example.proyecto.controller;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    private final String host= "smtp.gmail.com";
    private String user="sorware.grupo1@gmail.com";
    private String pass= "grupo1.sorware";
    private String to;
    private InternetAddress[] address;


    void emailRecuperarCuenta(String emailTo, String hasheado, String ipAdd, int localPort, String context) throws MessagingException {
        //Propiedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
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
                localPort + "/proyecto/system/cambiarCont?hasheado=" + hasheado);

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    }


    void emailEnviarPrimeraContraseña(String emailTo, String contrasenia, String usuario) throws MessagingException {

        //Propiedades
        Properties properties1 = new Properties();
        properties1.put("mail.smtp.auth","true");
        properties1.put("mail.smtp.starttls.enable","true");
        properties1.put("mail.smtp.host",host);
        properties1.put("mail.smtp.port","587");

        //Sesion con autenticador
        Authenticator authenticator = new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,pass);
            }
        };

        //Sesion que le paso las propiedades
        Session mailSession = Session.getInstance(properties1,authenticator);

        //Crea mensaje y emisor
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(user));

        //Receptor toma la llamada de la función
        to=emailTo;
        address= new InternetAddress[]{new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO,address);

        //Asunto y mensaje
        msg.setSubject("BIENVENID@ A MOSQOY");
        msg.setText("Gracias por unirte a la familia Mosqoy! En este correo te enviamos el usuario y la contraseña de tu cuenta.\n" +
                "Recuerda que puedes cambiarla desde tu perfil.\n" +
                "Usuario: "+ usuario + "\n" + "Contraseña: "+ contrasenia);

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    }


    void emailAlertaConsignacion(String emailTo, String producto, String comunidad) throws MessagingException {

        //Propiedades
        Properties properties2 = new Properties();
        properties2.put("mail.smtp.auth","true");
        properties2.put("mail.smtp.starttls.enable","true");
        properties2.put("mail.smtp.host",host);
        properties2.put("mail.smtp.port","587");

        //Sesion con autenticador
        Authenticator authenticator = new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,pass);
            }
        };

        //Sesion que le paso las propiedades
        Session mailSession = Session.getInstance(properties2,authenticator);

        //Crea mensaje y emisor
        MimeMessage msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(user));

        //Receptor toma la llamada de la función
        to=emailTo;
        address= new InternetAddress[]{new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO,address);

        //Asunto y mensaje
        msg.setSubject("ALERTA DE CONSIGNACIÓN");
        msg.setText("Atención, la consignación del producto " + producto + " vencerá en exactamente una semana.\n" +
                "Este deberá ser devuelto a la comunidad " + comunidad +"\n"+
                "");

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    }

}