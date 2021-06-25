package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {

    Session session;

    public MeetingService() {

        session = DatabaseConnector.getInstance().getSession();

    }

    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = session.createQuery(hql);
        return query.list();
    }


    public void createMeeting(Meeting meeting) {

        Transaction transaction = session.beginTransaction();
        session.save(meeting);
        transaction.commit();
    }

    public void deleteMeeting(Meeting meeting) {

        Transaction transaction = session.beginTransaction();
        session.delete(meeting);
        transaction.commit();
    }

    public Meeting findByMeetingId(long id) {

        return (Meeting) session.get(Meeting.class, id);

    }

    public void updateMeeting(Meeting meeting) {

        Transaction transaction = session.beginTransaction();
        session.merge(meeting);
    }
}
