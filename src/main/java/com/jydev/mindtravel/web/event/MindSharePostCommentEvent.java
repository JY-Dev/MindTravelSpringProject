package com.jydev.mindtravel.web.event;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.jydev.mindtravel.fcm.FcmPayload;
import com.jydev.mindtravel.fcm.FcmService;
import com.jydev.mindtravel.fcm.data.MindShareFcmData;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePost;
import com.jydev.mindtravel.service.mind.share.domain.MindSharePostComment;
import com.jydev.mindtravel.service.mind.share.event.MindSharePostCommentFcmEvent;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommandRepository;
import com.jydev.mindtravel.service.mind.share.repository.MindSharePostCommentCommandRepository;
import com.jydev.mindtravel.fcm.FcmMessageFactory;
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

    private final FcmMessageFactory messageFactory;

    @Async
    @TransactionalEventListener
    public void sendPostWriterPush(MindSharePostCommentFcmEvent event) throws FirebaseMessagingException {
        Optional<MindSharePost> postEntity = postCommandRepository.findById(event.getPostId());
        if (postEntity.isEmpty() || !event.hasCommentEvent(postEntity.get()))
            return;
        MindSharePost post = postEntity.get();
        String message = ms.getMessage("post.writer.comment.push", new Object[]{event.getCommentWriterMember().getNickname(), post.getTitle(), event.getContent()}, null);
        String title = ms.getMessage("comment.push.title",null,null);
        FcmPayload fcmPayload = new FcmPayload(FcmService.MIND_SHARE,message,title);
        MindShareFcmData mindShareFcmData = new MindShareFcmData(event.getPostId());
        Message commentMessage = messageFactory.getCommentMessage(fcmPayload,mindShareFcmData,post.getMember().getFcmToken());
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

    private void sendReplyCommentPush(MindSharePostCommentFcmEvent event, String fcmToken) throws FirebaseMessagingException {
        String replyCommentWriterNickname = event.getCommentWriterMember().getNickname();
        String message = ms.getMessage("reply.comment.push", new Object[]{replyCommentWriterNickname, event.getContent()}, null);
        String title = ms.getMessage("comment.push.title",null,null);
        FcmPayload fcmPayload = getFcmPayload(title,message);
        MindShareFcmData mindShareFcmData = new MindShareFcmData(event.getPostId());
        Message commentMessage = messageFactory.getCommentMessage(fcmPayload,mindShareFcmData,fcmToken);
        String response = FirebaseMessaging.getInstance().send(commentMessage);
        log.info("Fcm Response : {}",response);
    }

    private FcmPayload getFcmPayload(String title, String content){
        return new FcmPayload(FcmService.MIND_SHARE, title, content);
    }
}
