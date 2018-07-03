package com.xcjy.application.contact.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xcjy.application.base.impl.BaseApplicationImpl;
import com.xcjy.application.contact.IContactApplication;
import com.xcjy.dao.base.BaseDao;
import com.xcjy.dao.contact.ContactDao;
import com.xcjy.entity.contact.Contact;

/**
 * 联系我们application层实现类
 * 
 * @author 支亚州
 *
 */
@Service
@Transactional(value = "defaultTm")
public class ContactApplicationImpl extends BaseApplicationImpl<Contact>implements IContactApplication {

	@Autowired
	private ContactDao contactDao;

	@Override
	public BaseDao<Contact> getBaseDao() {
		return this.contactDao;
	}
	
}
