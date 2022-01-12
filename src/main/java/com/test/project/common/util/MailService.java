package com.test.project.common.util;

import java.io.Serializable;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


public interface MailService extends Serializable {
	 void sendMail(String subject, String text, String fromUser, String toUser, String[]toCC);
}
