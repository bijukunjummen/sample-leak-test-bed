package testleak.service;

import org.springframework.stereotype.Service;
import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeapMemoryService {
    private List<String> largeList = new ArrayList<>();


    public MessageAcknowledgement addToListAndReturn(Message message) {
        largeList.add(message.getPayload());
        return new MessageAcknowledgement(message.getId(), message.getPayload(),
                "Added " + message.getPayload());
    }

}
