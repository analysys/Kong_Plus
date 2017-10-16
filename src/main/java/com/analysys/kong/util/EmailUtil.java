package com.analysys.kong.util;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;

/**
 * Email客户端
 * @author Administrator
 */
public final class EmailUtil {
	private static final String host = "smtp.exmail.qq.com";
	private static final String port = "465";
	private static final String auth = "true";
	private static final String protocol = "smtp";
	private static final String account = PropertyUtil.getInstance().getString("email.from.account");
	private static final String pass = PropertyUtil.getInstance().getString("email.from.pwd");
	private static final String nick = PropertyUtil.getInstance().getString("email.from.nick");

	public static void sendMail(String toAddresse, String subject, String content) {
		Transport transport = null;
		try {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", host);
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", port);
			props.setProperty("mail.smtp.socketFactory.port", port);
			props.setProperty("mail.smtp.auth", auth);
			Session session = Session.getDefaultInstance(props,
					new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(account, pass);
						}
			});
			MimeMessage message = new MimeMessage(session);
			if(StringUtils.isEmpty(toAddresse))
				toAddresse = PropertyUtil.getInstance().getString("email.to", "");
			Address[] toAddress = InternetAddress.parse(toAddresse);
			if(StringUtils.isNotEmpty(nick)){
				message.setFrom(new InternetAddress(MimeUtility.encodeText(nick).concat("<").concat(account).concat(">")));
			} else {
				message.setFrom(new InternetAddress(account));
			}
			message.setRecipients(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setSentDate(new Date());
			Multipart mm = new MimeMultipart();
			BodyPart mdp = new MimeBodyPart();
			mdp.setContent(content, "text/html;charset=UTF-8");
			mm.addBodyPart(mdp);
			message.setContent(mm);
			transport = session.getTransport(protocol);
			transport.connect(host, account, pass);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(transport != null)
				try {
					transport.close();
				} catch (MessagingException e) {}
		}
	}
}
