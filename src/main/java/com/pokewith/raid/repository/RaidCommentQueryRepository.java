package com.pokewith.raid.repository;

import com.pokewith.raid.RaidComment;
import com.pokewith.raid.RaidCommentState;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        return query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.raid, raid).fetchJoin()
                .leftJoin(raidComment.user, user).fetchJoin()
                .where(raid.raidId.eq(raidId))
                .fetch();
    }

    public Optional<RaidComment> getLastCommentAndRaidByUserId(Long userId) {
        return Optional.ofNullable(
                query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.user, user).fetchJoin()
                .leftJoin(raidComment.raid, raid).fetchJoin()
                .leftJoin(raid.user, user).fetchJoin()
                .where(raidComment.user.userId.eq(userId), raidComment.raidCommentState.eq(RaidCommentState.JOINED)
                        .or(raidComment.raidCommentState.eq(RaidCommentState.WAITING)))
                .fetchOne());
    }

    public List<RaidComment> getRaidCommentListForLikeAndHate(Long raidId) {
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
