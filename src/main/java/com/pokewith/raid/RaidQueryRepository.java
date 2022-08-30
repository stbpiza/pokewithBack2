package com.pokewith.raid;

import com.pokewith.raid.dto.RqRaidListSearchDto;
import com.pokewith.user.QUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.pokewith.raid.QRaid.*;
import static com.pokewith.user.QUser.user;

@Repository
public class RaidQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public RaidQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Raid> raidList(RqRaidListSearchDto dto, Pageable pageable) {
        QueryResults<Raid> results = query
                .selectDistinct(raid)
                .from(raid)
                .leftJoin(raid.user, user)
                .where(typeEq(dto.getType()), stateEq(dto.getState()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(raid.raidId.desc())
                .fetchResults();

        List<Raid> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 분리된 메소드
     * **/

    private BooleanExpression typeEq(RaidType type) {
        if (type == null) return null;
        else return raid.raidType.eq(type);
    }

    private BooleanExpression stateEq(RaidState state) {
        if (state == null) return null;
        else return raid.raidState.eq(state);
    }
}