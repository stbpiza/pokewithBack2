package com.pokewith.raid;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pokewith.raid.QRaid.raid;
import static com.pokewith.raid.QRaidComment.raidComment;

@Repository
public class RaidCommentQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public RaidCommentQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<RaidComment> getRaidCommentListByRaidId(Long raidId) {
        List<RaidComment> result = query
                .selectDistinct(raidComment)
                .from(raidComment)
                .leftJoin(raidComment.raid, raid)
                .where(raid.raidId.eq(raidId))
                .fetch();
        return result;
    }
}
