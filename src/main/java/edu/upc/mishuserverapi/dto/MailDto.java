package edu.upc.mishuserverapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MailDto {
    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;
    private String[] filenames;
}