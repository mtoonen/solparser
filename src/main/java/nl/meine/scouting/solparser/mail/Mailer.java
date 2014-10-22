/*
 * Copyright (C) 2012-2014 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.meine.scouting.solparser.mail;


import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Matthijs Laan
 * @author Meine Toonen
 */
public class Mailer {

    public static Session getMailSession(final String user, final String password, String host) throws Exception {

        Properties props = new Properties();
	props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");

	Session session = Session.getInstance(props,new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(user, password);
            }
        });
        
        return session;
    }
    
    /**
     * Sends a mail with an attachment enclosed
     * @param fromName The name which should be display as the sender.
     * @param fromEmail The replyaddress
     * @param email To which address the mail should be sent
     * @param subject Subject of the mail
     * @param mailContent The content of the message
     * @param attachment The attachment to be sent
     * @param filename Give that attachment a naem.
     * @throws Exception 
     */
    public static void sendMail(String fromName, String fromEmail, String email, String subject, String mailContent, File attachment, String filename, String user, String password, String host) throws Exception {
    
        Address from = new InternetAddress(fromEmail, fromName);
        Message msg = new MimeMessage(getMailSession(user, password, host));
        msg.setFrom(from);
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        msg.setSubject(subject);
        msg.setSentDate(new Date());        
        
        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(mailContent);
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if(attachment != null){
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

        }
        // Send the complete message parts
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
