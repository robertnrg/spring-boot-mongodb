package com.robert.mongodb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robert.mongodb.document.UserDoc;
import com.robert.mongodb.service.UserService;
import com.robert.mongodb.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public final class UserController implements java.io.Serializable {

    /**
     * Generated serialVersionUID
     **/
    private static final long serialVersionUID = -7195991156755805709L;

    @Autowired
    private UserService srvUser;

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert(@RequestBody final UserDoc pboUserDoc, final HttpServletRequest poRequest) throws JsonProcessingException {
        if (null == pboUserDoc || !StringUtil.notNullOrEmpty(pboUserDoc.getName(), true) || pboUserDoc.getAge() <= 0) {
            LOGGER.error("Incomplete parameters: {}", new ObjectMapper().writeValueAsString(pboUserDoc));
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }
        this.getSrvUser().save(pboUserDoc);

        return new ResponseEntity<UserDoc>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody final UserDoc pboUserDoc, final HttpServletRequest poRequest) throws JsonProcessingException {
        if (null == pboUserDoc || !StringUtil.notNullOrEmpty(pboUserDoc.getId(), true) || !StringUtil.notNullOrEmpty(pboUserDoc.getName(), true)
                || pboUserDoc.getAge() <= 0) {
            LOGGER.error("Incomplete parameters: {}", new ObjectMapper().writeValueAsString(pboUserDoc));
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }
        final UserDoc boUserDoc = this.getSrvUser().update(pboUserDoc);
        if (null == boUserDoc) {
            return new ResponseEntity<>("Element not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boUserDoc, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") final String psId, final HttpServletRequest poRequest) throws JsonProcessingException {
        if (!StringUtil.notNullOrEmpty(psId, true)) {
            LOGGER.error("Incomplete parameters: {}", psId);
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }
        if (!this.getSrvUser().delete(psId)) {
            return new ResponseEntity<>("Element not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<UserDoc>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable("id") final String psId) throws JsonProcessingException {
        if (!StringUtil.notNullOrEmpty(psId, true)) {
            LOGGER.error("Incomplete parameters: {}", psId);
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }
        final UserDoc boUserDoc = this.getSrvUser().findById(psId);
        if (null == boUserDoc) {
            return new ResponseEntity<>("Element not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boUserDoc, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"exp-reg"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findWithName(@RequestParam("exp-reg") final String psExpRegName) throws JsonProcessingException {
        if (!StringUtil.notNullOrEmpty(psExpRegName, true)) {
            LOGGER.error("Incomplete parameters: {}", psExpRegName);
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.getSrvUser().findWithExpReg(psExpRegName), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"init", "end"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterByDates(@RequestParam("init") @DateTimeFormat(pattern = "dd-MM-yyyy") final Date init,
                                           @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") final Date end) throws JsonProcessingException {
        if (null == init) {
            LOGGER.error("Incomplete parameters");
            return new ResponseEntity<>("Incomplete parameters", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.getSrvUser().filterByDates(init, end), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDoc>> findAll() throws JsonProcessingException {
        return new ResponseEntity<>(this.getSrvUser().findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAll() {
        this.getSrvUser().deleteAll();
        return new ResponseEntity<>("Deleted elements", HttpStatus.OK);
    }

    public UserService getSrvUser() {
        return this.srvUser;
    }

    public void setSrvUser(final UserService srvUser) {
        this.srvUser = srvUser;
    }

}