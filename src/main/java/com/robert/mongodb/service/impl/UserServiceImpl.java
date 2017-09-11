package com.robert.mongodb.service.impl;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.robert.mongodb.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.mongodb.document.UserDoc;
import com.robert.mongodb.service.UserService;

@Service("srvUser")
public final class UserServiceImpl implements UserService {

   @Autowired
   private UserRepository userRepository;

   private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

   @Override
   public UserDoc save(UserDoc pboUserDoc) throws JsonProcessingException {
      LOGGER.info("Insert user with data: {}", new ObjectMapper().writeValueAsString(pboUserDoc));
      pboUserDoc.setId(null);
      pboUserDoc.setCreated(new Date());
      pboUserDoc.setUpdated(new Date());
      LOGGER.info("Insert user: {}", new ObjectMapper().writeValueAsString(pboUserDoc));
      pboUserDoc = this.getUserRepository().insert(pboUserDoc);
      LOGGER.info("User inserted: {}", new ObjectMapper().writeValueAsString(pboUserDoc));

      return pboUserDoc;
   }

   @Override
   public UserDoc update(final UserDoc pboUserDoc) throws JsonProcessingException {
      LOGGER.info("Update user with id: {}", pboUserDoc.getId());
      UserDoc boUserDoc = this.findById(pboUserDoc.getId());
      if (null == boUserDoc) {
         LOGGER.error("User not found: {}", pboUserDoc.getId());
         return null;
      }
      LOGGER.info("Old user: {}", new ObjectMapper().writeValueAsString(boUserDoc));
      boUserDoc.setName(pboUserDoc.getName());
      boUserDoc.setAge(pboUserDoc.getAge());
      boUserDoc.setUpdated(new Date());
      LOGGER.info("Update user: {}", new ObjectMapper().writeValueAsString(boUserDoc));
      boUserDoc = this.getUserRepository().save(boUserDoc);
      LOGGER.info("User updated: {}", new ObjectMapper().writeValueAsString(boUserDoc));

      return boUserDoc;
   }

   @Override
   public boolean delete(final String psId) throws JsonProcessingException {
      LOGGER.info("Delete user with id: {}", psId);
      final UserDoc boUserDoc = this.findById(psId);
      if (null == boUserDoc) {
         LOGGER.error("User not found: {}", psId);
         return false;
      }
      LOGGER.info("Delete user: {}", new ObjectMapper().writeValueAsString(boUserDoc));
      this.getUserRepository().delete(psId);
      LOGGER.info("User deleted: {}", new ObjectMapper().writeValueAsString(boUserDoc));

      return true;
   }

   @Override
   public UserDoc findById(final String psId) throws JsonProcessingException {
      LOGGER.info("Find by id: {}", psId);
      final UserDoc boUserDoc = this.getUserRepository().findOne(psId);
      if (null == boUserDoc) {
         LOGGER.error("User not found: {}", psId);
         return null;
      }
      LOGGER.info("User found: {}", new ObjectMapper().writeValueAsString(boUserDoc));

      return boUserDoc;
   }

   @Override
   public List<UserDoc> findWithExpReg(final String psExpRegName) throws JsonProcessingException {
      LOGGER.info("Find with exp-reg: {}", psExpRegName);
      final List<UserDoc> lstUsers = this.getUserRepository().findByNameLikeOrNameLike(psExpRegName.toLowerCase(), psExpRegName.toUpperCase());
      LOGGER.info("Users: {}", new ObjectMapper().writeValueAsString(lstUsers));

      return lstUsers;
   }

   @Override
   public List<UserDoc> filterByDates(final Date init, Date end) throws JsonProcessingException {
      if (null == end) {
         end = new Date();
      }
      LOGGER.info("Filter by dates: {} - {}", new SimpleDateFormat("dd-MM-yyyy").format(init), new SimpleDateFormat("dd-MM-yyyy").format(end));
      final List<UserDoc> lstUsers = this.getUserRepository().findByCreatedBetween(init, end);
      LOGGER.info("Users: {}", new ObjectMapper().writeValueAsString(lstUsers));

      return lstUsers;
   }

   @Override
   public List<UserDoc> findAll() throws JsonProcessingException {
      LOGGER.info("Find all");
      final List<UserDoc> lstUsers = this.getUserRepository().findAll();
      LOGGER.info("Users: {}", new ObjectMapper().writeValueAsString(lstUsers));

      return lstUsers;
   }

   @Override
   public void deleteAll() {
      LOGGER.info("Delete all");
      this.getUserRepository().deleteAll();
   }

   @Override
   public boolean existUser(final String psId) {
      return this.getUserRepository().exists(psId);
   }

   private UserRepository getUserRepository() {
      return this.userRepository;
   }

   public void setUserRepository(final UserRepository userRepository) {
      this.userRepository = userRepository;
   }

}