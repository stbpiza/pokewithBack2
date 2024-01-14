package com.pokewith.mypost.service;

import com.pokewith.chat.repository.ChatRoomRepository;
import com.pokewith.exception.BadRequestException;
import com.pokewith.exception.ForbiddenException;
import com.pokewith.exception.NotFoundException;
import com.pokewith.mypost.dto.LikeAndDislikeDto;
import com.pokewith.mypost.dto.request.RqPostLikeAndDislikeDto;
import com.pokewith.mypost.dto.request.RqStartRaidDto;
import com.pokewith.mypost.dto.response.RpGetMyPostDto;
import com.pokewith.raid.Raid;
import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
import com.pokewith.raid.RaidState;
import com.pokewith.raid.repository.RaidCommentQueryRepository;
import com.pokewith.raid.repository.RaidQueryRepository;
import com.pokewith.raid.repository.RaidRepository;
import com.pokewith.user.LikeOrDislike;
import com.pokewith.user.User;
import com.pokewith.user.UserState;
import com.pokewith.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MyPostServiceImpl implements MyPostService{

    private final RaidRepository raidRepository;
    private final RaidQueryRepository raidQueryRepository;
    private final RaidCommentQueryRepository raidCommentQueryRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EntityManager em;


    @Override
    public ResponseEntity<RpGetMyPostDto> getMyPost(Long userId) {

        User member = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        Raid raid = getMyPostRaid(member);

        boolean isVote = checkVote(member, raid);

        return new ResponseEntity<>(RpGetMyPostDto.builder()
                .raid(raid)
                .isVote(isVote)
                .build(), HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> startRaid(RqStartRaidDto dto, Long userId) {

        Raid raid = raidRepository.findById(dto.getRaidId()).orElseThrow(NotFoundException::new);

        // 작성자가 본인인지 확인
        checkWriter(raid, userId);
        // 초대중인 레이드인지 확인
        checkRaidStateInvite(raid);

        // 레이드 진행중으로 변경
        raid.startRaid();

        // 채팅방 이름생성
        String random = RandomStringUtils.randomAlphabetic(40);
        raid.makeChat(random);

        // 댓글 상태 변경
        List<RaidComment> raidCommentList = raidCommentQueryRepository.getRaidCommentListByRaidId(dto.getRaidId());
        setCommentStateAllJoinedOrRejected(raidCommentList, dto.getRaidCommentIdList());

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> endRaid(Long raidId, Long userId) {

        Raid raid = raidRepository.findById(raidId).orElseThrow(NotFoundException::new);

        // 작성자 확인
        checkWriter(raid, userId);
        // 이미 종료된 레이드인지 확인
        checkRaidStateDone(raid);

        // 레이드 종료 (1.초대중이면 완전 종료, 2.진행중이면 좋아요 싫어요 투표 상태로 변환)
        endRaidByRaidState(raid);

        // 댓글 상태 변경
        List<RaidComment> raidCommentList = raidCommentQueryRepository.getRaidCommentListByRaidId(raidId);
        setCommentStateAllEndByRaidState(raidCommentList, raid.getRaidState());

        // 채팅방 삭제
        deleteChatRoom(raid.getChat());

        return new ResponseEntity<>("", HttpStatus.OK);
    }



    @Transactional
    @Override
    public ResponseEntity<String> endRaidOneComment(Long userId) {

        RaidComment raidComment = raidCommentQueryRepository.getLastCommentAndRaidByUserId(userId)
                .orElseThrow(ForbiddenException::new);

        // 댓글상태 확인
        checkCommentState(raidComment.getRaidCommentState());

        // 1.초대중이면 혼자 종료, 2.진행중이면 좋아요 싫어요 투표 상태로 변환
        endCommentByRaidState(raidComment);

        return new ResponseEntity<>("", HttpStatus.OK);

    }


    @Transactional
    @Override
    public ResponseEntity<String> postLikeAndDislike(Long userId, RqPostLikeAndDislikeDto dto) {

        User member = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        // 작성한 글, 댓글 없으면 에러
        checkUserState(member.getUserState());

        // 좋아요 싫어요 반영
        insertLikeAndDislike(dto.getLikeAndDislikeDtoList());

        // 작성글, 댓글 상태 종료로 변경
        endMyRaidOrComment(member);

        // 본인 상태 변경
        member.setFreeState();

        return new ResponseEntity<>("", HttpStatus.OK);
    }




    /**
     *  분리한 메소드
     **/

    protected Raid getMyPostRaid(User member) {

        if (member.getUserState().equals(UserState.POST)) {
            return raidQueryRepository.getLastInviteRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
        } else if (member.getUserState().equals(UserState.COMMENT)) {
            RaidComment raidComment = raidCommentQueryRepository.getLastCommentAndRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
            return raidComment.getRaid();
        } else {
            return null;
        }
    }

    protected boolean checkVote(User member, Raid raid) {
        boolean isVote = false;
        if (member.getUserState().equals(UserState.POST)) {
            if (raid.getRaidState().equals(RaidState.VOTE)) {
                isVote = true;
            }
        } else if (member.getUserState().equals(UserState.COMMENT)) {
            for (RaidComment raidComment :raid.getRaidComments()) {
                if (raidComment.getUser().getUserId().equals(member.getUserId())
                        && raidComment.getRaidCommentState().equals(RaidCommentState.VOTE)) {
                    isVote = true;
                    break;
                }
            }
        }
        return isVote;
    }

    protected void checkWriter(Raid raid, Long userId) {
        // 작성자가 본인인지 확인
        if (!raid.getUser().getUserId().equals(userId)) {
            throw new ForbiddenException();
        }
    }

    protected void checkRaidStateInvite(Raid raid) {
        // 초대중인 레이드인지 확인
        if (!raid.getRaidState().equals(RaidState.INVITE)) {
            throw new BadRequestException();
        }
    }

    protected void checkRaidStateDone(Raid raid) {
        // 이미 종료된 레이드인지 확인
        if (raid.getRaidState().equals(RaidState.DONE)) {
            throw new BadRequestException();
        }
    }

    protected void setCommentStateAllJoinedOrRejected(List<RaidComment> raidCommentList, List<Long> raidCommentIdList) {
        for (RaidComment raidComment : raidCommentList) {
            for (Long commentId : raidCommentIdList) {
                if (commentId.equals(raidComment.getRaidCommentId())) {
                    raidComment.joinedComment();
                }
            }
            // 채택 리스트에 없는경우 거절로 변환
            if (raidComment.getRaidCommentState().equals(RaidCommentState.WAITING)) {
                raidComment.rejectedComment();
                raidComment.getUser().setFreeState();
            }
        }
    }

    protected void endRaidByRaidState(Raid raid) {
        if (raid.getRaidState().equals(RaidState.INVITE)) {
            raid.finalEndRaid();
            raid.getUser().setFreeState();
        } else if (raid.getRaidState().equals(RaidState.DOING)) {
            raid.endRaid();
        }
    }

    protected void setCommentStateAllEndByRaidState(List<RaidComment> raidCommentList, RaidState raidState) {
        for (RaidComment raidComment : raidCommentList) {
            if (raidState.equals(RaidState.INVITE)) {
                raidComment.rejectedComment();
                raidComment.getUser().setFreeState();
                continue;
            }
            if(raidComment.getRaidCommentState().equals(RaidCommentState.JOINED)) {
                raidComment.voteComment();
            }
        }
    }

    protected void deleteChatRoom(String chat) {
        if (chat != null) {
            chatRoomRepository.deleteChatRoom(chat);
        }
    }

    protected void checkCommentState(RaidCommentState raidCommentState) {
        if (raidCommentState.equals(RaidCommentState.VOTE) ||
                raidCommentState.equals(RaidCommentState.REJECTED) ||
                raidCommentState.equals(RaidCommentState.END)) {
            throw new BadRequestException();
        }
    }

    protected void endCommentByRaidState(RaidComment raidComment) {
        if (raidComment.getRaid().getRaidState().equals(RaidState.INVITE)) {
            raidComment.rejectedComment();
            raidComment.getUser().setFreeState();
        } else {
            raidComment.voteComment();
        }
    }

    protected void checkUserState(UserState userstate) {
        if (userstate.equals(UserState.FREE)) {
            throw new BadRequestException();
        }
    }

    protected void insertLikeAndDislike(List<LikeAndDislikeDto> likeAndDislikeDtoList) {
        for (LikeAndDislikeDto likeAndDislikeDto : likeAndDislikeDtoList) {
            User member = userRepository.findById(likeAndDislikeDto.getUserId())
                    .orElseThrow(ForbiddenException::new);
            if (likeAndDislikeDto.getLikeOrDislike().equals(LikeOrDislike.LIKE)) {
                member.upLikeCount();
            } else {
                member.upDislikeCount();
            }
        }
    }

    protected void endMyRaidOrComment(User member) {
        if (member.getUserState().equals(UserState.POST)) {
            Raid raid = raidQueryRepository.getLastInviteRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
            raid.finalEndRaid();
        } else if (member.getUserState().equals(UserState.COMMENT)) {
            RaidComment raidComment = raidCommentQueryRepository.getLastCommentAndRaidByUserId(member.getUserId())
                    .orElseThrow(NotFoundException::new);
            raidComment.endComment();
        }
    }

}
