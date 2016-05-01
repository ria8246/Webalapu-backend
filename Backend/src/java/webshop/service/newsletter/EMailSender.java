/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.newsletter;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author webbaser
 */
public class EMailSender
{

    private EMailSessionInfo sessionInfo;
    private EMailMessageInfo messageInfo;

    public EMailSender (EMailSessionInfo sessionInfo, EMailMessageInfo messageInfo)
    {
	this.sessionInfo = sessionInfo;
	this.messageInfo = messageInfo;
    }

    // TODO rewrite after clarifying the requirements
    public void sendMessage () throws MessagingException
    {
	String toRecipient = messageInfo.getRecipient ();
	String subject = messageInfo.getSubject ();
	String emailMessage = messageInfo.getMessage ();
	String fromSender = sessionInfo.getUsername ();
	String password = sessionInfo.getPassword ();
	String host = sessionInfo.getHostURL ();
	int port = sessionInfo.getHostPort ();

	Properties properties = new Properties ();
	properties.setProperty ("mail.smtp.host", host);
	properties.setProperty ("mail.smtp.port", Integer.toString (port));
	properties.setProperty ("mail.smtp.user", fromSender);
	properties.setProperty ("mail.smtp.password", password);
	properties.setProperty ("mail.smtp.auth", "true");
	// Might be GMail specific
	properties.setProperty ("mail.smtp.socketFactory.port", "465");
	properties.setProperty ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	Session session = Session.getInstance (properties);

	MimeMessage message = new MimeMessage (session);
	message.setFrom (new InternetAddress (fromSender));
	message.addRecipient (Message.RecipientType.TO, new InternetAddress (toRecipient));
	message.setSubject (subject);
	message.setText (emailMessage);
	Transport.send (message);

	return;
    }
}
