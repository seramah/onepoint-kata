package com.onepoint.kata.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * The Class MessageByLocaleService.
 *
 * @author srh
 */
@Component
public class MessageByLocaleService {

	/** The message source. */
	@Autowired
    MessageSource messageSource;
	
	/**
	 * Gets the message.
	 *
	 * @param id the id
	 * @return the message
	 */
	public String getMessage(String id) {
		return getMessage(id, null);
	}
	
	/**
	 * Gets the message.
	 *
	 * @param id the id
	 * @param args the args
	 * @return the message
	 */
	public String getMessage(String id, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(id, args, locale);
	}

    
}
