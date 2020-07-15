package com.example.proyecto.controller;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    private final String host= "smtp.gmail.com";
    private String user="sorware.grupo1@gmail.com";
    private String pass= "ngzesxyehvjymfia";
    private String to;
    private InternetAddress[] address;
    //Contra aplicacion otros: geoartqzovotonsk
    //Contra aplicacion correo: ngzesxyehvjymfia


    void emailRecuperarCuenta(String emailTo, String hasheado, int localPort, String context) throws MessagingException, IOException {
        //Propiedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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


        //Obtener la IP Elastica (Se tiene que cambiar)
        String ipAdd= "18.208.12.225";

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

    /* The following method will return the EC2 Instance ID.

    public String retrieveInstanceId() throws IOException {
        String EC2Id = null;
        String inputLine;
        URL EC2MetaData = new URL("http://169.254.169.254/latest/meta-data/instance-id");
        URLConnection EC2MD = EC2MetaData.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(EC2MD.getInputStream()));
        while ((inputLine = in.readLine()) != null) {
            EC2Id = inputLine;
        }
        in.close();
        return EC2Id;
    }

     */

    void emailEnviarPrimeraContraseña(String emailTo, String contrasenia, String usuario) throws MessagingException {

        //Propiedades
        Properties properties1 = new Properties();
        properties1.put("mail.smtp.auth","true");
        properties1.put("mail.smtp.starttls.enable","true");
        properties1.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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


    void emailAlertaConsignacionParaVender(String emailTo, List<String> productos) throws MessagingException {

        //Propiedades
        Properties properties2 = new Properties();
        properties2.put("mail.smtp.auth","true");
        properties2.put("mail.smtp.starttls.enable","true");
        properties2.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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

        //Acomodamos los códigos:
        String arreglito = "CÓDIGOS DE PRODUCTOS : ";
        for (String item:productos){
            arreglito = arreglito + "//" + item;
        }


        //Asunto y mensaje
        msg.setSubject("ALERTA DE CONSIGNACIÓN");
        msg.setText("Atención, la consignación de los siguientes productos está por vencer: \n" + arreglito);

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    }

    void emailAlertaConsignacionGestor(String emailTo) throws MessagingException {

        //Propiedades
        Properties properties2 = new Properties();
        properties2.put("mail.smtp.auth","true");
        properties2.put("mail.smtp.starttls.enable","true");
        properties2.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
        msg.setSubject("ALERTA DE PROXIMO VENCIMIENTO DE CONSIGNACIÓN");
        msg.setText("Atención, favor de revisar la pestaña 'Gestionar Consignación' y entrar al apartado 'Próximas a vencer', este mes vencen algunas consignaciones que necesita dar tratamiento.");

        //Enviar el correo electronico
        Transport transporte = mailSession.getTransport("smtp");
        transporte.connect(host, user, pass);
        transporte.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    }



}
