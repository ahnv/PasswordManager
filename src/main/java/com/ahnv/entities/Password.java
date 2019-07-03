package com.ahnv.entities;

/**
 * Created by Abhinav.
 */
public class Password {
    private int id;
    private String name;
    private String url;
    private String username;
    private String password;
    private String notes;
    public int getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getUrl(){ return this.url; }
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getNotes(){return this.notes; }
    public void setId(int id){ this.id = id; }
    public void setName(String name){ this.name = name;}
    public void setUrl(String url){ this.url = url;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setNotes(String notes){this.notes = notes;}
}
