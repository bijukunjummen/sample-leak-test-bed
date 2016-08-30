package testleak.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import testleak.domain.Message;
import testleak.domain.MessageAcknowledgement;
import testleak.service.NativeMemoryLeakService;

@RestController
public class NativeMemoryLeakController {

    private final NativeMemoryLeakService nativeMemoryLeakService;

    @Autowired
    public NativeMemoryLeakController(NativeMemoryLeakService nativeMemoryLeakService) {
        this.nativeMemoryLeakService = nativeMemoryLeakService;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public Resource<MessageAcknowledgement> pongMessage(@RequestBody Message input) {
        return new Resource<>(nativeMemoryLeakService.handleMessage(input));
    }

}
