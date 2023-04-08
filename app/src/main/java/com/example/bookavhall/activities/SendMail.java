package com.example.bookavhall.activities;

import android.util.Log;

import com.example.bookavhall.ui.bookavhall.Booking;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {
    Properties properties;
    String senderEmail = "bookavhall@gmail.com";
    String username = "bookavhall";
    String senderPassword = "duubqeshaspakclh";
    String recieverEmail = "";
    public SendMail(){
        properties =new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }

    public void sendBookingMailToThisUser(Booking booking, String avHallName){

        this.recieverEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        String message= "Dear " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + ",\n\n"
                + "I hope this email finds you well. This is to confirm that you have successfully booked the AV Hall at " + avHallName + " for the time slot of " + booking.getTime() + " on " + booking.getDate() + ".\n\n"
                + "We are excited to have you as our guest and look forward to hosting your event at our facility. The AV Hall is equipped with state-of-the-art audio-visual equipment, including projectors, screens, microphones, and sound systems, to meet your event requirements.\n\n"
                + "Please note the following important details regarding your AV Hall booking:\n\n"
                + "- AV Hall: " + avHallName + "\n"
                + "- Booking Date: " + booking.getDate() + "\n"
                + "- Booking Time: " + booking.getTime() + "\n"
                + "- Duration: 1 hour\n" // Updated duration to 1 hour
                + "\n"
                + "As a reminder, kindly review and adhere to our facility's policies and guidelines, including any restrictions on food and beverage, smoking, and noise levels, to ensure a smooth and successful event.\n\n"
                + "If you have any questions, requests for additional services or equipment, or need to make any changes to your booking, please do not hesitate to contact our events team at [Contact Information]. We are here to assist you and make your event a memorable one.\n\n"
                + "We look forward to welcoming you to our venue and wish you a successful and enjoyable event in the AV Hall. Thank you for choosing our facility for your event.\n\n"
                + "Best regards,\n"
                + "book av hall";



        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, senderPassword);
            }
        });
        Log.i("yes","Entering");


        Thread thread = new Thread(() -> {
            try {
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmail));
                mimeMessage.setFrom(new InternetAddress(senderEmail));
                mimeMessage.setSubject("Confirmation of AV Hall Booking");
                mimeMessage.setText(message);
                Transport.send(mimeMessage);
                Log.i("send","successful");
            } catch (MessagingException e) {
                Log.i("unsuccess","yes");
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private void sendmail(String message){

    }
}
