import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Chat {
    private static ChatWindow window;
    public static void main(String[] args) throws InterruptedException, NamingException, JMSException {
        window = new ChatWindow();
        while(window.user.equals("")){
            Thread.sleep(50);
        }
        String name = window.user;
        Context context = new InitialContext();
        ActiveMQConnectionFactory factory= new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = (Destination) context.lookup("topic1");

        try {
            Connect(destination, connection);
            SendMessages(connection, destination, connection, name);
        }catch (JMSException e){
            window.appendMessage("Error connecting.");
        }
    }
    public static void Connect(Destination destination, Connection connection) throws JMSException{
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new GUIMessageListener(window));
    }
    public static void SendMessages(Connection conn, Destination destination, Connection connection, String name) throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        conn.start();

        TextMessage message = session.createTextMessage();
        message.setText(name + " has connected.");
        producer.send(message);

        while(true){
            if(window.messagesToBeProcessed.size() != 0){
                String newMessage = window.messagesToBeProcessed.removeLast();
                message.setText("[" + name + "]: " + newMessage);
                producer.send(message);
            }
        }
    }
}