package com.shavindu.sqsmessages.controllers;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private AmazonSQS sqs;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        SendMessageRequest sendRequest = new SendMessageRequest(queueUrl, message);
        sqs.sendMessage(sendRequest);
    }

    @GetMapping
    public List<Message> receiveMessages() {
        ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest(queueUrl);
        return sqs.receiveMessage(receiveRequest).getMessages();
    }

    @DeleteMapping("/{receipt-handle}")
    public void deleteMessage(@PathVariable("receipt-handle") String receiptHandle) {
        sqs.deleteMessage(queueUrl, receiptHandle);
    }

}
