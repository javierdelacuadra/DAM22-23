package dao.impl;

import config.Configuration;
import dao.common.DAOConstants;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class SendEmail {
    private final Configuration configuration;

    @Inject
    public SendEmail(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put(DAOConstants.MAIL_SMTP_PORT, Integer.parseInt(configuration.getPort()));
        mailServerProperties.put(DAOConstants.MAIL_SMTP_AUTH, configuration.getAuth());
        mailServerProperties.put(DAOConstants.MAIL_SMTP_SSL_TRUST, configuration.getSslTrust());
        mailServerProperties.put(DAOConstants.MAIL_SMTP_STARTTLS_ENABLE, configuration.getStarttls());

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(msg, DAOConstants.TEXT_HTML);


        // Step3

        Transport transport = getMailSession.getTransport(configuration.getProtocol());

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(configuration.getHost(),
                configuration.getFrom(),
                configuration.getPassword());

        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
