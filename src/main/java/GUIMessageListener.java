import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class GUIMessageListener implements MessageListener {
    ChatWindow window;
    public GUIMessageListener(ChatWindow window){
        this.window = window;
    }

    @Override
    public void onMessage(Message message) {
        try {
            window.appendMessage(((TextMessage) message).getText());
        }catch (JMSException e){
            window.appendMessage("error receivng a incoming message.");
        }
    }
}
