package br.com.rocketseat.hiokdev.planner_java.infrastructure.mail;

import br.com.rocketseat.hiokdev.planner_java.config.mail.MailProperties;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.EmailException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.gateway.MailGateway;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
@RequiredArgsConstructor
public class SmtpMailService implements MailGateway {

    private final Configuration templateConfig;
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public void send(MailMessage mailMessage) {
        String body = processTemplate(mailMessage);
        var mimeMessage = createMimeMessage(mailMessage, body);
        try {
            mailSender.send(mimeMessage);
        } catch (Exception exception) {
            throw new EmailException("Error when try send mail", exception);
        }
    }

    private String processTemplate(MailMessage mailMessage) {
        try {
            var template = templateConfig.getTemplate(mailMessage.body());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mailMessage.variables());
        } catch (Exception exception) {
            throw new EmailException("Error when try to process e-mail template", exception);
        }
    }

    private MimeMessage createMimeMessage(MailMessage mailMessage, String body) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(mailProperties.getSender());
            helper.setTo(mailMessage.destination());
            helper.setSubject(mailMessage.subject());
            helper.setText(body, true);
            return mimeMessage;
        } catch (Exception exception) {
            throw new EmailException("Error when try create mime message", exception);
        }
    }

}
