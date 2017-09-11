package com.robert.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.robert.mongodb.document.UserDoc;

@Repository
public interface UserRepository extends MongoRepository<UserDoc, String> {

   UserDoc findByName(final String name);

   UserDoc findByNameAndAge(final String name, final byte age);

   List<UserDoc> findByNameLikeOrNameLike(final String name, final String nameUpper);

   List<UserDoc> findAll();

   List<UserDoc> findByCreatedBetween(final Date init, final Date end);

   @Query("{'created': {$gt: ?0, $lt: ?1}}")
   List<UserDoc> findUsersWithDate(final Date init, final Date end);

}