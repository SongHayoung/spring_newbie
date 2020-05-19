package com.newbie.Spring_Newbie.User.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailSender;

public class DummyMailSender implements MailSender {
    public void send(SimpleMailMessage simpleMailMessage) throws MailException{}
    public void send(SimpleMailMessage[] simpleMailMessage) throws MailException{}
}
