package domain.utils;

import domain.utils.common.ConstantesUtils;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MandarMail {

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put(ConstantesUtils.MAIL_SMTP_PORT, Integer.parseInt(ConstantesUtils.PORT_NUMBER));
        mailServerProperties.put(ConstantesUtils.MAIL_SMTP_AUTH, ConstantesUtils.TRUE);
        mailServerProperties.put(ConstantesUtils.MAIL_SMTP_SSL_TRUST, ConstantesUtils.SMTP_GMAIL_COM);
//        mailServerProperties.put("mail.smtp.ssl.trust", "smtp01.educa.madrid.org");
        mailServerProperties.put(ConstantesUtils.MAIL_SMTP_STARTTLS_ENABLE, ConstantesUtils.TRUE);

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, ConstantesUtils.TEXT_HTML);


        // Step3

        Transport transport = getMailSession.getTransport(ConstantesUtils.SMTP);

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(ConstantesUtils.SMTP_GMAIL_COM,
                ConstantesUtils.ALUMNOSDAMQUEVEDO_GMAIL_COM,
                ConstantesUtils.AYUAKLCKGXBBOOPH);

        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
