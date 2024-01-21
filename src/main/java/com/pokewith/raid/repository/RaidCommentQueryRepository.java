package com.pokewith.raid.repository;

import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
import com.pokewith.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.pokewith.raid.QRaid.raid;
import static com.pokewith.raid.QRaidComment.raidComment;
import static com.pokewith.user.QUser.user;

@Repository
public class RaidCommentQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public RaidCommentQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<RaidComment> getRaidCommentListByRaidId(Long raidId) {

        QUser user1 = new QUser("user1");
        QUser user2 = new QUser("user2");

        return query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.raid, raid).fetchJoin()
                .leftJoin(raidComment.user, user1).fetchJoin()
                .leftJoin(raid.user, user2).fetchJoin()
                .where(raid.raidId.eq(raidId), raidComment.raidCommentState.ne(RaidCommentState.REJECTED))
                .fetch();
    }

    public Optional<RaidComment> getLastCommentAndRaidByUserId(Long userId) {

        QUser user1 = new QUser("user1");
        QUser user2 = new QUser("user2");

        return Optional.ofNullable(
                query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.user, user1).fetchJoin()
                .leftJoin(raidComment.raid, raid).fetchJoin()
                .leftJoin(raid.user, user2).fetchJoin()
                .where(raidComment.user.userId.eq(userId), raidComment.raidCommentState.eq(RaidCommentState.JOINED)
                        .or(raidComment.raidCommentState.eq(RaidCommentState.WAITING))
                        .or(raidComment.raidCommentState.eq(RaidCommentState.VOTE)))
                .fetchOne());
    }

    public List<RaidComment> getRaidCommentListForLikeAndDislike(Long raidId) {
        return query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.raid, raid).fetchJoin()
                .leftJoin(raidComment.user, user).fetchJoin()
                .leftJoin(raid.user, user).fetchJoin()
                .where(raid.raidId.eq(raidId), raidComment.raidCommentState.eq(RaidCommentState.JOINED)
                        .or(raidComment.raidCommentState.eq(RaidCommentState.END)))
                .fetch();
    }
}
