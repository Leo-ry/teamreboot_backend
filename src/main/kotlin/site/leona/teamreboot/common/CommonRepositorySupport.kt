package site.leona.teamreboot.common

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.querydsl.SimpleEntityPathResolver
import org.springframework.stereotype.Repository
import org.springframework.util.Assert

@Repository
abstract class CommonRepositorySupport(
    private val domainClass: Class<*>,
) {
    private lateinit var querydsl: Querydsl
    private lateinit var entityManager: EntityManager
    private lateinit var jpaQueryFactory: JPAQueryFactory

    init {
        Assert.notNull(domainClass, "도메인은 빈 값일 수 없습니다.")
    }

    @Autowired
    fun setEntityManager(entityManager: EntityManager) {
        Assert.notNull(domainClass, "EntityManager 는 빈 값일 수 없습니다.")

        val entityInformation: JpaEntityInformation<*, *> = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager)
        val resolver: SimpleEntityPathResolver = SimpleEntityPathResolver.INSTANCE
        val path: EntityPath<*> = resolver.createPath(entityInformation.javaType)
        this.entityManager = entityManager
        this.querydsl = Querydsl(entityManager, PathBuilder(path.type, path.metadata))
        this.jpaQueryFactory = JPAQueryFactory(entityManager)
    }

    @PostConstruct
    fun validate() {
        Assert.notNull(entityManager, "EntityManeger 는 빈 값일 수 없습니다.")
        Assert.notNull(querydsl, "Querydsl 는 빈 값일 수 없습니다.")
        Assert.notNull(jpaQueryFactory, "JpqQueryFactory는 빈값일 수 없습니다.")
    }

    fun getJpaQueryFactory(): JPAQueryFactory {
        return jpaQueryFactory
    }

    protected fun <T> select(expr: Expression<T>): JPAQuery<T> {
        return getJpaQueryFactory().select(expr)
    }

    protected fun <T> selectFrom(from: EntityPath<T>): JPAQuery<T> {
        return getJpaQueryFactory().selectFrom(from)
    }
}