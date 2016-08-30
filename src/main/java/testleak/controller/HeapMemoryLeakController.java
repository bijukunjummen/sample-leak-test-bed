package testleak.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;
import testleak.service.HeapMemoryService;

@RestController
public class HeapMemoryLeakController {

    private final HeapMemoryService heapMemoryService;

    @Autowired
    public HeapMemoryLeakController(HeapMemoryService heapMemoryService) {
        this.heapMemoryService = heapMemoryService;
    }

    @RequestMapping(value = "/heap", method = RequestMethod.POST)
    public Resource<MessageAcknowledgement> pongMessage(@RequestBody Message input) {
        return new Resource<>(heapMemoryService.addToListAndReturn(input));
    }

}
