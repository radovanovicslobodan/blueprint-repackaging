package cucumber_blueprint.common.helpers.mail;


import cucumber_blueprint.common.helpers.font_converter.FontConverter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

/**
 * Class will store methods for email helpers
 */
public class EmailHelpers {

    public static Logger logger = LoggerFactory.getLogger(EmailHelpers.class);
    public static String host = "mailHost";
    public static String userName = "username";
    public static String password = "password";

    /**
     * Set email properties
     *
     * @param host email host
     * @return preset mail properties
     * @author Dino
     */
    public static Properties setMailProperties(String host) {
        Properties sysProps = new Properties();
        sysProps.put("mail.imap.host", host);
        sysProps.put("mail.imap.port", "143");
        sysProps.put("mail.imap.starttls.enable", "true");
        sysProps.put("mail.imap.ssl.trust", host);
        return sysProps;
    }

    /**
     * Connect and create session to mailbox
     *
     * @param sysProps properties obtained via method setMailProperties
     * @param host     email host
     * @param userName email username
     * @param password email password
     * @return store connection to mailbox
     */
    @SneakyThrows
    public static Store connectToMailbox(Properties sysProps, String host,
                                         String userName, String password) {
        //Create session to server
        Session session = Session.getInstance(sysProps);

        //Store yours messages:
        Store store = session.getStore("imap");
        store.connect(host, userName, password);
        return store;
    }


    /**
     * Method will find email when all parameters are inserted
     *
     * @param host        email host
     * @param userName    email userName for login
     * @param password    password
     * @param mailSubject subject
     * @param recipient   recipient mail since we are using +randomNumber in email (availtesterkovic+1234@gamil.com)
     * @return Message type email for desired user
     * @throws MessagingException
     * @author Dino
     */
    public static Message searchEmailsBySubject(String host, String userName,
                                                String password, final String mailSubject, String recipient, String folderName) throws MessagingException {


        //Define protocol
        Properties sysProps = setMailProperties(host);
        Message[] foundMessages = new Message[0];
        Message mail = null;

        Store store = null;
        Folder folderInbox = null;

        try {

            store = connectToMailbox(sysProps, host, userName, password);
            // opens the inbox folder
            folderInbox = store.getFolder(folderName);
            folderInbox.open(Folder.READ_ONLY);

            // creates a search criterion
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains(mailSubject)) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };

            // performs search through the folder
            foundMessages = folderInbox.search(searchCondition);

            for (int i = 0; i < foundMessages.length; i++) {
                Message message = foundMessages[i];
                String subject = message.getSubject();
                logger.info("Found message #" + i + ": " + subject);
            }


        } catch (NoSuchProviderException ex) {
            logger.error("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            logger.error("Could not connect to the message store.");
            ex.printStackTrace();
        }

        for (int i = 0; i < foundMessages.length; i++) {
            if (foundMessages[i].getRecipients(Message.RecipientType.TO)[0].toString().equals(recipient)) {
                mail = foundMessages[i];
            }
        }
        // disconnect
//        folderInbox.close(false);
//        store.close();
        return mail;
    }

    /**
     * Method will wait for desired mail to be received
     *
     * @param emailHostName email host
     * @param emailUserName email userName for login
     * @param emailPassword password
     * @param mailSubject   subject
     * @param mailRecipient recipient mail since we are using +randomNumber in email (availtesterkovic+1234@gamil.com)
     * @param waitTime      set waiting period in milliseconds
     * @return Message when email is received
     * @throws MessagingException
     * @author Dino
     */
    public static Message waitMailToBeObtained(String emailHostName, String emailUserName, String emailPassword,

                                               String mailSubject, String mailRecipient, long waitTime, String folderName) throws MessagingException, InterruptedException {
        Message welcomeMail = null;
        Thread.sleep(2000);

        long t = System.nanoTime();
        long end = t + TimeUnit.SECONDS.toNanos(waitTime);
        while ((welcomeMail == null || welcomeMail.getRecipients(Message.RecipientType.TO) == null || !welcomeMail.getRecipients(Message.RecipientType.TO)[0].toString().equals(mailRecipient)) && end > System.nanoTime()) {
            welcomeMail = searchEmailsBySubject(emailHostName, emailUserName, emailPassword,
                    mailSubject, mailRecipient, folderName);

        }
        if (welcomeMail == null) {
            logger.error("Mail is not obtained");
        }
        return welcomeMail;
    }

    /**
     * Email can be obtained with just email and subject, other necessary properties are set
     *
     * @param email
     * @param subject
     * @return
     * @throws MessagingException
     * @throws InterruptedException
     */
    public static Message waitMailToBeObtained(String email, String subject, String folderName)
            throws MessagingException, InterruptedException {

        Message message = waitMailToBeObtained(host,
                userName, password,
                subject, email, 2, folderName);

        return message;
    }

    public static String getTextFromMimeMultipart(MimeMultipart mimeMultipart)
            throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    public static Message[] getMail(String email, String subject, String folderName) {

        final Message[] mail = {null};
        await("Wait condition to be true")
                .atMost(120, TimeUnit.SECONDS)
                .pollDelay(15, TimeUnit.SECONDS)
                .pollInterval(5, TimeUnit.SECONDS)
                .until(() -> {
                    mail[0] = EmailHelpers.waitMailToBeObtained(email,
                            new FontConverter().replaceLatinCharacters(subject),
                            folderName);
                    return !mail[0].equals(null);
                });
        return mail;
    }

    @SneakyThrows
    public static void emptyMailbox(String host, String userName,
                                    String password, String folderName) {
        //Define protocol
        var sysProps = setMailProperties(host);
        //Create connection to mailbox
        var store = connectToMailbox(sysProps, host, userName, password);

        // opens the inbox folder
        var folderInbox = store.getFolder(folderName);
        folderInbox.open(Folder.READ_WRITE);

        //Delete all messages
        for (Message m : folderInbox.getMessages()) {
            m.setFlag(Flags.Flag.DELETED, true);
        }
        // disconnect from mailbox to confirm deletion
        folderInbox.close(true);
        store.close();

    }

    public static void emptyMailbox(String folderName){
        emptyMailbox(host,
                userName, password,folderName);
    }

}
