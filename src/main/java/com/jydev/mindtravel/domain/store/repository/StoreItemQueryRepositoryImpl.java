package com.jydev.mindtravel.domain.store.repository;

import com.jydev.mindtravel.domain.store.domain.StoreItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jydev.mindtravel.domain.store.domain.QStoreItem.storeItem;


@RequiredArgsConstructor
@Repository
public class StoreItemQueryRepositoryImpl implements StoreItemQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StoreItem> getStoreItems() {
        return queryFactory.selectFrom(storeItem)
                .orderBy(storeItem.price.asc())
                .fetch();
    }
}
