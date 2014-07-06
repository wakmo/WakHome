package com.atomikos.demo.publisher;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.atomikos.demo.Service;
import com.atomikos.demo.domain.Order;
import com.atomikos.demo.transformer.Counter;

import com.thoughtworks.xstream.XStream;

public class Publisher implements Service {

	private final static Logger log = LoggerFactory.getLogger(Publisher.class);

	private XStream xStream = new XStream();

	private JmsTemplate jmsTemplate;
	private String xmlFoldername;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public String getXmlFoldername() {
		return xmlFoldername;
	}
	public void setXmlFoldername(String xmlFoldername) {
		this.xmlFoldername = xmlFoldername;
	}

	@Transactional
	public boolean process() {
		try {
			File folder = new File(URLDecoder.decode(getClass().getClassLoader().getResource(xmlFoldername).getFile(),"UTF-8"));

			File[] files = folder.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".xml");
				}
			});

			if(files!=null){
				for (File file : files) {
					log.info("processing file " + file.getName());
					processOneFile(file.getAbsolutePath());
				}
			}


			return false;
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	private void processOneFile(String filename) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);

			Batch batch;
			Object o = xStream.fromXML(fis);
			if (o instanceof Batch) {
				batch = (Batch) o;
				log.info("batch of " + batch.getCount() + " object(s) to send");
			}
			else {
				batch = new Batch();
				batch.setCount(1);
				batch.setOrder((Order) o);
				log.info("1 object to send");
			}

			for (int i=0; i<batch.getCount() ;i++) {
				jmsTemplate.convertAndSend(batch.getOrder());
			}
			Counter.totalMessages=batch.getCount();
		}
		finally {
			if (fis != null) fis.close();
		}
	}

}
