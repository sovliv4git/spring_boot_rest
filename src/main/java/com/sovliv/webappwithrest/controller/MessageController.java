package com.sovliv.webappwithrest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sovliv.webappwithrest.domain.Message;
import com.sovliv.webappwithrest.domain.Views;
import com.sovliv.webappwithrest.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Класс контроллер.
 */
@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;

    /**
     * Аннотация @Autowired отмечает конструктор, поле или метод как требующий
     * автозаполнения инъекцией зависимости Spring.
     * @param messageRepo
     */
    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list() {
        return messageRepo.findAll();
    }

    /**
     * Поиск записи по id.
     * Аннотация @GetMapping говорит о том, что метод getOne() должен быть вызван, когда
     * кто-то вызовет метод GET на пути /message. Имя пути берется из параметра @RequestMapping.
     * @return message.
     */
    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    /**
     * Добавление новой записи.
     * Аннотация @PostMapping говорит о том, что метод create() должен быть вызван, когда
     * кто-то вызовет метод POST на пути /message. Имя пути берется из параметра @RequestMapping.
     * Аннотация @RequestBody используется для чтения тела запроса и десериализовывания в Object
     * через HttpMessageConverter.
     * @return message.
     */
    @PostMapping
    Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    /**
     * Обновление текущей записи.
     * Аннотация @PutMapping говорит о том, что метод update() будет вызван, когда
     * кто-то вызовет метод PUT на пути /message. Имя пути берется из параметра @RequestMapping.
     * @return message.
     */
    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDB,
                          @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDB, "id");
        return messageRepo.save(messageFromDB);
    }

    /**
     * Удаление записи по id.
     * Аннотация @DeleteMapping говорит о том, что метод delete() будет вызван, когда
     * кто-то вызовет метод DELETE на пути /message. Имя пути берется из параметра @RequestMapping.
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
