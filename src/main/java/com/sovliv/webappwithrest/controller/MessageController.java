package com.sovliv.webappwithrest.controller;

import com.sovliv.webappwithrest.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс контроллер.
 */
@RestController
@RequestMapping("message")
public class MessageController {

    /**
     * Счетчик записей.
     */
    private int counter = 4;

    /**
     * Коллекция, хранящая в себе сообщения.
     */
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{put("id", "1"); put("text", "first message");}});
        add(new HashMap<String, String>(){{put("id", "2"); put("text", "second message");}});
        add(new HashMap<String, String>(){{put("id", "3"); put("text", "third message");}});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    /**
     * Метод получения записи по id.
     * Добавлен фильт сообщений по id.
     * Добавлен exception при не соответствии id какому-либо сообщению.
     * Аннотация @PathVariable предназначена для работы с параметрами, передаваемыми
     * через адрес запроса в Spring WebMVC.
     * @param id
     * @return
     */
    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst().orElseThrow(NotFoundException::new);
    }

    /**
     * Поиск записи по id.
     * Аннотация @GetMapping говорит о том, что метод getOne() должен быть вызван, когда
     * кто-то вызовет метод GET на пути /message. Имя пути берется из параметра @RequestMapping.
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    /**
     * Добавление новой записи.
     * Аннотация @PostMapping говорит о том, что метод create() должен быть вызван, когда
     * кто-то вызовет метод POST на пути /message. Имя пути берется из параметра @RequestMapping.
     * Аннотация @RequestBody используется для чтения тела запроса и десериализовывания в Object
     * через HttpMessageConverter.
     */
    @PostMapping
    Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    /**
     * Обновление текущей записи.
     * Аннотация @PutMapping говорит о том, что метод update() будет вызван, когда
     * кто-то вызовет метод PUT на пути /message. Имя пути берется из параметра @RequestMapping.
     * @param id
     */
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);

        return messageFromDB;
    }

    /**
     * Удаление записи по id.
     * Аннотация @DeleteMapping говорит о том, что метод delete() будет вызван, когда
     * кто-то вызовет метод DELETE на пути /message. Имя пути берется из параметра @RequestMapping.
     * @param id
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
