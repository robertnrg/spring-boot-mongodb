package com.robert.mongodb.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "user")
public class UserDoc {

   @Id
   @Indexed
   private String id;
   private String name;
   private byte   age;
   @JsonFormat(pattern = "dd-MM-yyyy")
   @DateTimeFormat(iso = ISO.DATE_TIME)
   private Date   created;
   @JsonFormat(pattern = "dd-MM-yyyy")
   @DateTimeFormat(iso = ISO.DATE_TIME)
   private Date   updated;

   public UserDoc() {
      super();
   }

   public UserDoc(final String name, final byte age) {
      super();
      this.setName(name);
      this.setAge(age);
   }

   public String getId() {
      return this.id;
   }

   public void setId(final String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public byte getAge() {
      return this.age;
   }

   public void setAge(final byte age) {
      this.age = age;
   }

   public Date getCreated() {
      return this.created;
   }

   public void setCreated(final Date created) {
      this.created = created;
   }

   public Date getUpdated() {
      return this.updated;
   }

   public void setUpdated(final Date updated) {
      this.updated = updated;
   }

}