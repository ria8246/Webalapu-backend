/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.email;

import java.util.Properties;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import webshop.service.newsletter.EMailMessageInfo;

/**
 *
 * @author webbaser
 */
@Stateless
public class EMailBean
{

    private static final String mailer = "JavaMailer";
    private static Logger logger = Logger.getLogger ("webshop.service.email");
    @Resource (name = "mail/BackendSession")
    private Session session;

    public EMailBean ()
    {
    }

    public void sendEmail (EMailMessageInfo eMailMessageInfo)
    {
	String recipient = eMailMessageInfo.getRecipient ();
	String subject = eMailMessageInfo.getSubject ();
	String emailMessage = eMailMessageInfo.getMessage ();

	/*
	String username = KristofData.getUsername ();
	String password = KristofData.getPassword ();
	Properties properties = new Properties ();
	properties.setProperty ("mail.smtp.host", "smtp.gmail.com");
	properties.setProperty ("mail.smtp.port", "465");
	properties.setProperty ("mail.smtp.user", username);
	properties.setProperty ("mail.smtp.password", password);
	properties.setProperty ("mail.smtp.auth", "true");
	properties.setProperty ("mail.smtp.socketFactory.port", "465");
	properties.setProperty ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	Authenticator authenticator = new GMailAuthenticator (username, password);
	Session session = Session.getInstance (properties, authenticator);

	*/
	try
	{
	    MimeMessage mimeMessage = new MimeMessage (session);
	    // God damn "session" is null... It ruins my day and night and all the remaining of my life...
	    mimeMessage.setFrom ();
	    mimeMessage.addRecipient (Message.RecipientType.TO, new InternetAddress (recipient));
	    mimeMessage.setSubject (subject);
	    mimeMessage.setText (emailMessage);

	    Transport.send (mimeMessage);
	    logger.info ("Mail sent to " + recipient + ".");
	}
	catch (MessagingException me)
	{
	    me.printStackTrace ();
	    logger.info ("Error in EmailBean for " + recipient);
	}

	return;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
