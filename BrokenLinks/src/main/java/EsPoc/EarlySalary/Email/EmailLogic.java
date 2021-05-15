
package EsPoc.EarlySalary.Email;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import EsPoc.EarlySalary.Common.CommonAction.ConfigurationManager;

public class EmailLogic 
{
	 public void sendMail(String emailContent) {
	        //Setting up configurations for the email connection to the Google SMTP server using TLS
	        Properties props = new Properties();
	        
	        ConfigurationManager rd = new ConfigurationManager();
	        String Version = rd.read_Configfile("Version");
	      //added on 29-10-2020
	        
	        
	        props.put("mail.smtp.host", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587"); //587 596 443 995 465
	        props.put("mail.smtp.auth", "true");
	        
	        //added by Swapnil 6-11-19
//	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//	        props.put("mail.smtp.socketFactory.fallback", "false");
	        //Establishing a session with required user detailsswapnil.khobragade
	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("wasim.shaikh@earlysalary.com", "786wasim786");
	            }
	        });
	        try {
	            //Creating a Message object to set the email content
	            MimeMessage message = new MimeMessage(session);
	            //Storing the comma seperated values to email addresses
	            String to = "wasim.shaikh@earlysalary.com, devops@earlysalary.com, anup.borde@earlysalary.com, anil.sinha@earlysalary.com, nikhil.jain@earlysalary.com, vinay.dubey@earlysalary.com, om.kourav@earlysalary.com, swapnil.khobragade@earlysalary.com, pankaj.marghade@earlysalary.com";

	            // String to = "wasim.shaikh@earlysalary.com";

	            
	            //  Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email
	            //addresses in an array of InternetAddress objects
	            InternetAddress[] address = InternetAddress.parse(to, true); 
	            
	            //Setting the recepients from the address variable
	            message.setRecipients(Message.RecipientType.TO, address);

					
					message.setSubject("Website Broken Link  Report");
					

					// Create the message part
					BodyPart messageBodyPart = new MimeBodyPart();
					// Now set the actual message
					//messageBodyPart.setText(emailContent);
					messageBodyPart.setContent(emailContent,"text/html");
					// Create a multipar message
					Multipart multipart = new MimeMultipart();
					// Set text message part
					multipart.addBodyPart(messageBodyPart);

					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					Thread.sleep(2000);
					

					// Part three is HTML attachment
					messageBodyPart = new MimeBodyPart();
					String filenametext = System.getProperty("user.dir")+"/Report/AutomationTestReport.html";
					DataSource source1 = new FileDataSource(filenametext);
					messageBodyPart.setDataHandler(new DataHandler(source1));
					messageBodyPart.setFileName("AutomationTestReport.html");
					multipart.addBodyPart(messageBodyPart);
					
					
					// Part three is LOG attachment
					messageBodyPart = new MimeBodyPart();
					String filenametext_log = System.getProperty("user.dir")+"/Logs/ES_BrokenLinks.log";
					DataSource source2 = new FileDataSource(filenametext_log);
					messageBodyPart.setDataHandler(new DataHandler(source2));
					messageBodyPart.setFileName("ES_BrokenLinks.log");
					multipart.addBodyPart(messageBodyPart);
					

					// Send the complete message parts
					message.setContent(multipart);
					Transport.send(message);


					System.out.println("Email Sent Successfully....");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}catch (InterruptedException Int)
				{
				//	Setup.log.debug("Exception in thread.sleep");
				}


				
			}

			private String getDateTime() {
				return null;
			}

			
	}

