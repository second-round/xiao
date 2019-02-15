package com.example.homework.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id(autoincrement = true)//自增的
    @Unique//唯一的
    private Long id;//主键
    private String name;
    private String uri;
    @Generated(hash = 1714901196)
    public UserBean(Long id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUri() {
        return this.uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }


}
