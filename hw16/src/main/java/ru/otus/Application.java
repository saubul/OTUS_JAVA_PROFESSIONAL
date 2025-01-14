package ru.otus;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.entities.*;
import ru.otus.exceptions.TechnicalException;
import ru.otus.serializers.JsonSerializer;
import ru.otus.serializers.Serializer;
import ru.otus.serializers.XmlSerializer;
import ru.otus.serializers.YamlSerializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private static final String INPUT_FILE_NAME = "sms.json";


    public static void main(String[] args) {
        ChatSessions chatSessions = readChatSessions();
        PhoneMessages phoneMessages = processChatSessionsToPhoneMessages(chatSessions);
        writePhoneMessages(phoneMessages);
    }

    private static ChatSessions readChatSessions() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(Application.class.getClassLoader().getResourceAsStream("sms.json"), ChatSessions.class);
        } catch (IOException e) {
            throw new TechnicalException("При попытке прочесть файл \"%s\" произошла ошибка: %s".formatted(INPUT_FILE_NAME, e.getMessage()));
        }
    }

    private static PhoneMessages processChatSessionsToPhoneMessages(ChatSessions chatSessions) {
        PhoneMessages phoneMessages = new PhoneMessages();

        List<PhoneMessage> phoneMessageList = chatSessions.getChatSessions().stream()
                .flatMap(chatSession -> {
                    // Как связывать members и messages указано не было, поэтому считаем, что member всегда один
                    String last = chatSession.getMembers().get(0).getLast();
                    return chatSession.getMessages()
                            .stream().collect(Collectors.groupingBy(Message::getBelongNumber)).entrySet()
                            .stream().map(entry -> {
                                PhoneMessage phoneMessage = new PhoneMessage();
                                phoneMessage.setBelongNumber(entry.getKey());
                                phoneMessage.setLiteMessageList(
                                        entry.getValue().stream()
                                                .map(
                                                        message -> {
                                                            LiteMessage liteMessage = new LiteMessage();
                                                            liteMessage.setChatIdentifier(chatSession.getChatIdentifier());
                                                            liteMessage.setLast(last);
                                                            liteMessage.setSendDate(message.getSendDate());
                                                            liteMessage.setText(message.getText());
                                                            return liteMessage;
                                                        }
                                                )
                                                .collect(Collectors.toList())
                                );
                                return phoneMessage;
                            });
                })
                .collect(Collectors.groupingBy(PhoneMessage::getBelongNumber)).entrySet().stream()
                .map(entry -> {
                    PhoneMessage phoneMessage = new PhoneMessage();
                    phoneMessage.setBelongNumber(entry.getKey());

                    List<LiteMessage> finalLiteMessageList = new ArrayList<>();
                    entry.getValue().forEach(pm -> finalLiteMessageList.addAll(pm.getLiteMessageList()));
                    finalLiteMessageList.sort(Comparator.comparing(LiteMessage::getSendDate));
                    phoneMessage.setLiteMessageList(finalLiteMessageList);
                    return phoneMessage;
                })
                .toList();
        phoneMessages.setPhoneMessages(phoneMessageList);
        return phoneMessages;
    }

    private static void writePhoneMessages(PhoneMessages phoneMessages) {
        writePhoneMessages(phoneMessages, new JsonSerializer(), "./hw16/output/json.json");
        writePhoneMessages(phoneMessages, new XmlSerializer(), "./hw16/output/xml.xml");
        writePhoneMessages(phoneMessages, new YamlSerializer(), "./hw16/output/yaml.yml");
    }

    private static void writePhoneMessages(PhoneMessages phoneMessages, Serializer serializer, String outputFileName) {
        String serializedObjectString = serializer.serialize(phoneMessages);
        LOGGER.info(serializedObjectString);
        try (PrintWriter printWriter = new PrintWriter(outputFileName)) {
            printWriter.println(serializedObjectString);
        } catch (FileNotFoundException e) {
            throw new TechnicalException(e.getMessage());
        }
    }

}
