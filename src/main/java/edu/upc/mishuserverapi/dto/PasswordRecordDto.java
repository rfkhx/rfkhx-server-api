package edu.upc.mishuserverapi.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 存储密码记录的表（加密过）
 */
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PasswordRecordDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // 记录的类型，当前阶段应为login
    private String type;
    //记录的名字。如“百度”
    private String name;
    //记录对应的网址，区分不同记录的主要识别依据
    private String url;
    //用户名
    private String username;
    //密码
    private String password;
    //文本记录，留给用户自己记点东西
    private String note;
}
