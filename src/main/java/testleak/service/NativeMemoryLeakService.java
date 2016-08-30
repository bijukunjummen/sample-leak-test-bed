package testleak.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;
import testleak.util.JaxbUtil;

@Service
public class NativeMemoryLeakService {
    private static final Logger logger = LoggerFactory.getLogger(NativeMemoryLeakService.class);


    public MessageAcknowledgement handleMessage(Message message) {

        return new MessageAcknowledgement(message.getId(), message.getPayload(),
                JaxbUtil.leakingMarshalObject(message));
    }

}
