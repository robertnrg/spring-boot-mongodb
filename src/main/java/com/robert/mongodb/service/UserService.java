package com.robert.mongodb.service;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robert.mongodb.document.UserDoc;

public interface UserService {

   UserDoc save(final UserDoc pboUserDoc) throws JsonProcessingException;

   UserDoc update(final UserDoc pboUserDoc) throws JsonProcessingException;

   boolean delete(final String psId) throws JsonProcessingException;

   List<UserDoc> findWithExpReg(final String psExpRegName) throws JsonProcessingException;

   List<UserDoc> filterByDates(final Date init, final Date end) throws JsonProcessingException;

   UserDoc findById(final String psId) throws JsonProcessingException;

   List<UserDoc> findAll() throws JsonProcessingException;

   void deleteAll();

   boolean existUser(final String psId);

}