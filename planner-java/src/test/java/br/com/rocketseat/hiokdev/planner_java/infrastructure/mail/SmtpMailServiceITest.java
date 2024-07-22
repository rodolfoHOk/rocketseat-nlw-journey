package br.com.rocketseat.hiokdev.planner_java.infrastructure.mail;

import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.EmailException;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.ParticipantFactory;
import br.com.rocketseat.hiokdev.planner_java.factory.domain.TripFactory;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
public class SmtpMailServiceITest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Autowired
    private SmtpMailService smtpMailService;

    @BeforeEach
    void setup() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }

    @Test
    void shouldSendConfirmTripEmail() throws MessagingException, IOException {
        var trip = TripFactory.getTripWithId(UUID.randomUUID());
        var mailMessage = MailMessageFactory.getConfirmTripMailMessage(trip);
        smtpMailService.send(mailMessage);

        final MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages.length).isEqualTo(1);
        final MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getFrom()[0].toString()).isEqualTo("confirm@planner.com");
        assertThat(receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString()).isEqualTo(mailMessage.destination());
        assertThat(receivedMessage.getSubject()).isEqualTo(mailMessage.subject());
        assertThat(receivedMessage.getContent().toString()).contains("Você solicitou a criação de uma viagem");
        assertThat(receivedMessage.getContent().toString()).contains(trip.getOwnerName());
        assertThat(receivedMessage.getContent().toString()).contains(trip.getDestination());
        String startDate = trip.getStartsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM"));
        assertThat(receivedMessage.getContent().toString()).contains(startDate);
        String endDate = trip.getEndsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM' de 'yyyy"));
        assertThat(receivedMessage.getContent().toString()).contains(endDate);
        String confirmationLink = MailMessageFactory.BASE_URL + "/trips/" + trip.getId() + "/confirm";
        assertThat(receivedMessage.getContent().toString()).contains(confirmationLink);
    }

    @Test
    void shouldSendConfirmPresenceEmail() throws MessagingException, IOException {
        var participant = ParticipantFactory.getParticipantWithId(UUID.randomUUID());
        var mailMessage = MailMessageFactory.getConfirmPresenceMailMessage(participant);
        smtpMailService.send(mailMessage);

        final MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages.length).isEqualTo(1);
        final MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getFrom()[0].toString()).isEqualTo("confirm@planner.com");
        assertThat(receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString()).isEqualTo(mailMessage.destination());
        assertThat(receivedMessage.getSubject()).isEqualTo(mailMessage.subject());
        assertThat(receivedMessage.getContent().toString()).contains("Você foi convidado(a) pelo(a)");
        var trip = participant.getTrip();
        assertThat(receivedMessage.getContent().toString()).contains(trip.getOwnerName());
        assertThat(receivedMessage.getContent().toString()).contains(trip.getDestination());
        String startDate = trip.getStartsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM"));
        assertThat(receivedMessage.getContent().toString()).contains(startDate);
        String endDate = trip.getEndsAt().format(DateTimeFormatter.ofPattern("d' de 'MMM' de 'yyyy"));
        assertThat(receivedMessage.getContent().toString()).contains(endDate);
        String confirmationLink = MailMessageFactory.BASE_URL + "/trips/" + trip.getId() + "?participant=" + participant.getId();
        assertThat(receivedMessage.getContent().toString()).contains(confirmationLink);
        String confirmationApp = "planner://trip/" + trip.getId() + "?participant=" + participant.getId();
        assertThat(receivedMessage.getContent().toString()).contains(confirmationApp);
    }

    @Test
    void shouldThrowEmailExceptionWhenSendEmailWithInvalidTemplate() {
        var participant = ParticipantFactory.getParticipantWithId(UUID.randomUUID());
        var mailMessage = MailMessageFactory.getMailMessageWithInvalidTemplate(participant);
        assertThatThrownBy(() -> smtpMailService.send(mailMessage)).isInstanceOf(EmailException.class);
    }

    @Test
    void shouldThrowEmailExceptionWhenSendEmailWithInvalidMailData() {
        var participant = ParticipantFactory.getParticipantWithId(UUID.randomUUID());
        var mailMessage = MailMessageFactory.getMailMessageWithInvalidMailData(participant);
        assertThatThrownBy(() -> smtpMailService.send(mailMessage)).isInstanceOf(EmailException.class);
    }

    @Test
    void shouldThrowEmailExceptionWhenSendEmailWithSmtpServerIsDown() {
        greenMail.getSmtp().stopService();
        var participant = ParticipantFactory.getParticipantWithId(UUID.randomUUID());
        var mailMessage = MailMessageFactory.getConfirmPresenceMailMessage(participant);
        assertThatThrownBy(() -> smtpMailService.send(mailMessage)).isInstanceOf(EmailException.class);
    }

}
