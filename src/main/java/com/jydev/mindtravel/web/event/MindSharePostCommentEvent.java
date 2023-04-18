package com.jydev.mindtravel.web.event;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jydev.mindtravel.service.member.domain.Member;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.event.MindSharePostCommentFcmEvent;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommentCommandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class MindSharePostCommentEvent {
    private final MindSharePostCommandRepository postCommandRepository;
    private final MindSharePostCommentCommandRepository postCommentCommandRepository;
    private final MessageSource ms;

    @Async
    @TransactionalEventListener
    public void sendPostWriterPush(MindSharePostCommentFcmEvent event) throws FirebaseMessagingException {
        Optional<MindSharePost> postEntity = postCommandRepository.findById(event.getPostId());
        if (postEntity.isEmpty() || !event.hasCommentEvent(postEntity.get()))
            return;
        MindSharePost post = postEntity.get();
        Member postWriter = post.getMember();

        String message = ms.getMessage("post.writer.comment.push", new Object[]{postWriter.getNickname(), post.getTitle(), event.getContent()}, null);
        Message commentMessage = getCommentMessage(message,post.getMember().getFcmToken());
        String response = FirebaseMessaging.getInstance().send(commentMessage);
        log.info("Fcm Response : {}",response);
    }

    @Async
    @TransactionalEventListener
    public void sendReplyCommentPush(MindSharePostCommentFcmEvent event) throws FirebaseMessagingException {
        Optional<MindSharePost> postEntity = postCommandRepository.findById(event.getPostId());
        Optional<MindSharePostComment> comment = postCommentCommandRepository.findById(event.getParentCommentId());
        if (postEntity.isEmpty() || comment.isEmpty()
                || event.hasCommentEvent(postEntity.get()) || !event.hasChildCommentEvent(comment.get()))
            return;
        String commentWriterFcmToken = comment.get().getMember().getFcmToken();
        sendReplyCommentPush(event, commentWriterFcmToken);
    }

    @Async
    @TransactionalEventListener
    public void sendTagReplyCommentPush(MindSharePostCommentFcmEvent event) throws FirebaseMessagingException {
        Optional<MindSharePost> postEntity = postCommandRepository.findById(event.getPostId());
        Optional<MindSharePostComment> comment = postCommentCommandRepository.findById(event.getParentCommentId());
        if (postEntity.isEmpty() || comment.isEmpty()
                || event.hasCommentEvent(postEntity.get()) || event.hasChildCommentEvent(comment.get())
                || !event.hasTagMemberEvent())
            return;
        String tagFcmToken = event.getTagMember().getFcmToken();
        sendReplyCommentPush(event, tagFcmToken);
    }

    private void sendReplyCommentPush(MindSharePostCommentFcmEvent event, String tagFcmToken) throws FirebaseMessagingException {
        String replyCommentWriterNickname = event.getCommentWriterMember().getNickname();
        String message = ms.getMessage("reply.comment.push", new Object[]{replyCommentWriterNickname, event.getContent()}, null);
        Message commentMessage = getCommentMessage(message,tagFcmToken);
        String response = FirebaseMessaging.getInstance().send(commentMessage);
        log.info("Fcm Response : {}",response);
    }

    private Message getCommentMessage(String message, String fcmToken){
        String title = ms.getMessage("comment.push.title",null,null);
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(message)
                .build();
        return Message.builder()
                .setNotification(notification)
                .setToken(fcmToken)
                .build();
    }
}
