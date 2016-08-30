package testleak.service;

import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import testleak.util.JaxbUtil;

@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerServiceImpl.class);


    private final String replyMessage;

    @Autowired
    public MessageHandlerServiceImpl(@Value("${reply.message}") String replyMessage) {
        this.replyMessage = replyMessage;
    }

    @Override
    public MessageAcknowledgement handleMessage(Message message) {
        logger.info("About to Acknowledge");

        try {
            Thread.sleep(message.getDelayBy());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new MessageAcknowledgement(message.getId(), JaxbUtil.marshalJaxbObj(message), this.replyMessage);
    }

}
