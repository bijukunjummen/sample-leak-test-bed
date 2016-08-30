package testleak.service;

import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;

public interface MessageHandlerService {
    MessageAcknowledgement handleMessage(Message message);
}
