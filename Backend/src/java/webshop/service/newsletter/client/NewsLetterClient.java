/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webshop.service.newsletter.client;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import webshop.service.email.EMailBean;
import webshop.service.newsletter.EMailMessageInfo;
import webshop.service.newsletter.NewsLetter;
import webshop.service.newsletter.NewsLetterBean;

/**
 *
 * @author webbaser
 */
public class NewsLetterClient
{

    @EJB
    private static NewsLetter newsletter;

    @EJB
    private static EMailBean eMailBean;

    public NewsLetterClient ()
    {
    }

    public static void main (String[] args)
    {
	NewsLetterClient nlc = new NewsLetterClient ();
	// newsletter = new NewsLetterBean (); // It should be automatically initiated but for some odd reason it does not
	eMailBean = new EMailBean (); // It should be automatically initiated but for some odd reason it does not
	List<EMailMessageInfo> list = nlc.createMessageInfoItems ();
	Iterator<EMailMessageInfo> listIterator = list.iterator ();

	while (listIterator.hasNext ())
	{
	    EMailMessageInfo current = listIterator.next ();
	    // newsletter.sendNewsLetter (current);

	    eMailBean.sendEmail (current); // Uncomment to send e-mails
	}

	return;
    }

    public List<EMailMessageInfo> createMessageInfoItems ()
    {

	String subject = "Test message from backend server";
	StringBuilder stringBuilder = new StringBuilder ();
	stringBuilder.append ("Dear Recipient,");
	stringBuilder.append ("\n");
	stringBuilder.append ("Thank you for the interest you made for our products. ");
	stringBuilder.append ("Soon you will receive our discount items due to our webshop opening sale.");
	stringBuilder.append ("\n");
	stringBuilder.append ("(This is just a test message. Don't have high expectations...)");
	String text = stringBuilder.toString ();
	List<EMailMessageInfo> messageInfoItems = new LinkedList<> ();

	// Add recipients like this:
	// messageInfoItems.add (new EMailMessageInfo ("VALID EMAIL ADDRESS", subject, text));
	messageInfoItems.add (new EMailMessageInfo ("ria8246@gmail.com", subject, text));
	messageInfoItems.add (new EMailMessageInfo ("sipczi@gmail.com", subject, text));
	messageInfoItems.add (new EMailMessageInfo ("baricsz@gmail.com", subject, text));
	messageInfoItems.add (new EMailMessageInfo ("zeppelin.hindenburg@postino.hu", subject, text));

	return messageInfoItems;
    }
}
