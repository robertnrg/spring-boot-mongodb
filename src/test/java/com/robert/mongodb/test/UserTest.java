package com.robert.mongodb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.robert.mongodb.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.robert.mongodb.document.UserDoc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

   @Autowired
   private UserRepository userRepository;

   @Before
   public void setUp() throws Exception {
      final UserDoc user1 = new UserDoc("Juan", (byte) 27);
      final UserDoc user2 = new UserDoc("Miguel", (byte) 25);
      assertNotNull(user1.getId());
      assertNotNull(user2.getId());
      this.userRepository.save(user1);
      this.userRepository.save(user2);
      assertNotNull(user1.getId());
      assertNotNull(user2.getId());
   }

   @Test
   public void testFetchData() {
      final UserDoc userA = userRepository.findByName("Miguel");
      assertNotNull(userA);
      assertEquals(27, userA.getAge());

      final List<UserDoc> users = userRepository.findAll();
      int count = 0;
      for (final UserDoc user : users) {
         count++;
      }
      assertEquals(count, 2);
   }

   @Test
   public void testDataUpdate() {
      final UserDoc userB = this.userRepository.findByName("Juan");
      userB.setAge((byte) 40);
      this.userRepository.save(userB);
      final UserDoc userC = this.userRepository.findByName("Juan");
      assertNotNull(userC);
      assertEquals(40, userC.getAge());
   }

   @After
   public void tearDown() throws Exception {
      this.userRepository.deleteAll();
   }
}