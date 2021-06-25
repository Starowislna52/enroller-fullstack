package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {

        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {

        meetingService.createMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {

        Meeting meeting = meetingService.findByMeetingId(id);
        meetingService.deleteMeeting(meeting);
        return new ResponseEntity<Meeting>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.POST)
    public ResponseEntity<?> addMeetingParticipant(@PathVariable("id") long id, @PathVariable("login") String login) {

        Meeting meeting = meetingService.findByMeetingId(id);
        Participant participant = participantService.findByLogin(login);
        meeting.addParticipant(participant);
        meetingService.updateMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMeetingParticipant(@PathVariable("id") long id, @PathVariable("login") String login) {

        Meeting meeting = meetingService.findByMeetingId(id);
        Participant participant = participantService.findByLogin(login);
        meeting.removeParticipant(participant);
        meetingService.updateMeeting(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

    }
}
