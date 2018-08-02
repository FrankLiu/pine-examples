package io.pine.examples.hello.domain;

import java.util.ArrayList;
import java.util.List;

public class Mailer {
	private String from;
	private String to;
	private String subject;
	private String text;
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
		public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(Attachment a){
		this.attachments.add(a);
	}
	
	public static class Attachment{
		private String name;
		private String path;
		
		public Attachment(String name, String path){
			this.setName(name);
			this.setPath(path);
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}
}
