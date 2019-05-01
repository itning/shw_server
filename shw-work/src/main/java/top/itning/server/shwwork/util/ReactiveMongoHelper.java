package top.itning.server.shwwork.util;


import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Component Will Help You Pagination
 *
 * @author itning
 * @date 2019/4/30 15:47
 */
@Component
public class ReactiveMongoHelper {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public ReactiveMongoHelper(ReactiveMongoTemplate reactiveMongoTemplate, ModelMapper modelMapper) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.modelMapper = modelMapper;
    }

    @NonNull
    private <T> Mono<Page<T>> doFind(int page, int size,
                                     @Nullable Map<String, Object> criteriaMap,
                                     @NonNull Class<T> entityClass,
                                     @Nullable Sort.Direction direction, @Nullable String... properties) {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 1;
        }

        Query query = new Query();
        // need criteria?
        if (criteriaMap != null) {
            Criteria criteria = new Criteria();
            criteriaMap.forEach((k, v) -> criteria.and(k).is(v));
            query.addCriteria(criteria);
        }
        Pageable pageable;
        // need sort?
        if (direction != null) {
            Sort sort = new Sort(direction, properties);
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }

        Mono<Long> count = reactiveMongoTemplate.count(query, entityClass);
        Flux<T> flux = reactiveMongoTemplate.find(query.with(pageable), entityClass);

        return flux.collectList().zipWith(count).map(c -> getPage(pageable, c.getT1(), c.getT2()));
    }

    @NonNull
    public <T> Mono<Page<T>> getAllWithCriteriaByPagination(int page, int size, @NonNull Map<String, Object> criteriaMap, @NonNull Class<T> entityClass) {
        return doFind(page, size, criteriaMap, entityClass, null);
    }

    @NonNull
    public <T> Mono<Page<T>> getAllWithCriteriaAndDescSortByPagination(int page, int size,
                                                                       @NonNull String properties,
                                                                       @NonNull Map<String, Object> criteriaMap,
                                                                       @NonNull Class<T> entityClass) {
        return doFind(page, size, criteriaMap, entityClass, Sort.Direction.DESC, properties);
    }

    @NonNull
    public <T> Mono<Page<T>> getAllByPagination(int page, int size, @NonNull Class<T> entityClass) {
        return doFind(page, size, null, entityClass, null);
    }

    @NonNull
    public <T> Mono<Page<T>> getAllWithDescSortByPagination(int page, int size, @NonNull String properties, @NonNull Class<T> entityClass) {
        return doFind(page, size, null, entityClass, Sort.Direction.DESC, properties);
    }

    @NonNull
    public <T> Page<T> getPage(@NonNull Pageable pageable, @NonNull List<T> content, long total) {
        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public <T> Flux<T> find(@NonNull Query query, @NonNull Class<T> entityClass) {
        return reactiveMongoTemplate.find(query, entityClass);
    }

    @NonNull
    public <T> Flux<T> findFieldsByQuery(@NonNull final Map<String, Object> fieldsMap, @NonNull final Map<String, Object> queryMap, @NonNull Class<T> entityClass) {
        Document queryObject = new Document(queryMap);
        Document fieldsObject = new Document(fieldsMap);
        Query query = new BasicQuery(queryObject, fieldsObject);
        return find(query, entityClass);
    }

    @NonNull
    public <T> Flux<T> findFieldsByQuery(@NonNull final String queryKey, @NonNull final Object queryValue, @NonNull Class<T> entityClass, String... fields) {
        Document queryObject = new Document(queryKey, queryValue);
        Document fieldsObject = new Document();
        for (String f : fields) {
            fieldsObject.put(f, true);
        }
        Query query = new BasicQuery(queryObject, fieldsObject);
        return find(query, entityClass);
    }

    /**
     * 分页获取
     *
     * @param pageable    {@link Pageable}
     * @param allContents 所有元素集合
     * @return Page
     */
    public <T, V> Page<V> getPageWithAllContents(Pageable pageable, List<T> allContents, Type destinationType) {
        //页数*每页条数
        int to = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        int toIndex = to > allContents.size() ? allContents.size() : to;
        List<T> works;
        try {
            works = allContents.subList(Math.toIntExact(pageable.getOffset()), toIndex);
        } catch (Exception e) {
            works = new ArrayList<>(0);
        }
        return new PageImpl<>(modelMapper.map(works, destinationType), pageable, allContents.size());
    }
}
