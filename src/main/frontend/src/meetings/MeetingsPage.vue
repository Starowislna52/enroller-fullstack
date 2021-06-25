<template>
  <div>
    <new-meeting-form @added="addNewMeeting($event)"></new-meeting-form>
    <span v-if="meetings.length == 0">Brak zaplanowanych spotkań.</span>
    <h3 v-else>Zaplanowane zajęcia ({{ meetings.length }})</h3>

    <meetings-list
        :meetings="meetings"
        :username="username"
        @attend="addMeetingParticipant($event)"
        @unattend="removeMeetingParticipant($event)"
        @delete="deleteMeeting($event)"
    ></meetings-list>
  </div>
</template>

<script>
import NewMeetingForm from "./NewMeetingForm";
import MeetingsList from "./MeetingsList";

export default {
  components: {NewMeetingForm, MeetingsList},
  props: ["username"],
  data() {
    return {
      meetings: this.$http.get("meetings").then(response => {
        this.meetings = response.body;
      })
    };
  },
  methods: {
    addNewMeeting(meeting) {
      this.$http.post("meetings", meeting).then(() => {
        this.listMeetings();
      });
    },
    addMeetingParticipant(meeting) {
      var id = meeting.id;
      this.$http.post(`meetings/${id}/participants/${this.username}`, meeting).then(() => {
        this.listMeetings();
      });
    },
    removeMeetingParticipant(meeting) {
      var id = meeting.id;
      this.$http.delete(`meetings/${id}/participants/${this.username}`, meeting).then(() => {
        this.listMeetings();
      });
    },
    deleteMeeting(meeting) {
      var id = meeting.id;
      this.$http.delete(`meetings/${id}`, meeting).then(() => {
        this.listMeetings();
      });
    },
    listMeetings() {
      this.meetings = this.$http.get("meetings").then(response => {
        this.meetings = response.body;
      });
    }
  }
};
</script>