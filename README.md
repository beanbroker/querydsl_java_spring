https://beanbroker.github.io/2019/07/13/Java/java_querydsl_gradle4-1/

# 1. 기본셋팅

5버젼과 4버젼의 차이를 추후 블로그에 공유할예정! 

회사에서 gradle 4버젼을 사용함을 인지!

build.gradle에서 가장 중요하게 봐야하는 부분은 __// querydsl 적용__ 이라 적혀있는 부분

> build.gradle (메이븐일 경우 다른데곳에서 찾아서....하시길..)


> build.gradle

```java
buildscript {
    ext {

        springBootVersion = '2.1.6.RELEASE'
        querydslPluginVersion = '1.0.10'
    }
    repositories {
        mavenCentral()
        maven { url "https://plugins.gradle.org/m2/" } // plugin 저장소
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("gradle.plugin.com.ewerk.gradle.plugins:querydsl-plugin:${querydslPluginVersion}")

    }
}

apply plugin: 'java'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'

group = 'com.beanbroker'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
    mavenCentral()
}

dependencies {
    compile("com.querydsl:querydsl-jpa") // querydsl
    compile("com.querydsl:querydsl-apt") // querydsl

    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('org.springframework.boot:spring-boot-starter-web')

//    runtimeOnly('com.h2database:h2')
    runtimeOnly 'mysql:mysql-connector-java'
    compile('org.projectlombok:lombok')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}

// querydsl 적용
apply plugin: "com.ewerk.gradle.plugins.querydsl"
def querydslSrcDir = 'src/main/generated'

querydsl {
    library = "com.querydsl:querydsl-apt"
    jpa = true
    querydslSourcesDir = querydslSrcDir
}

sourceSets {
    main {
        java {
            srcDirs = ['src/main/java', querydslSrcDir]
        }
    }
}

}


def querydslSrcDir = 'src/main/generated'

querydsl {
    library = "com.querydsl:querydsl-apt"
    jpa = true
    querydslSourcesDir = querydslSrcDir
}

compileQuerydsl{
    options.annotationProcessorPath = configurations.querydsl
}

configurations {
    querydsl.extendsFrom compileClasspath
}

sourceSets {
    main {
        java {
            srcDirs = ['src/main/java', querydslSrcDir]
        }
    }
}

```

> application.yml

```
spring: 
    datasource:
        url: jdbc:mysql://localhost:3306/study
        username: root
        password: password


```


> user.sql

```sql
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
```


> UserEntity.class

```java

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;
    private String name;
    private int age;

    @Builder
    public UserEntity(String userId, String name, int age){

        this.userId = userId;
        this.name = name;
        this.age = age;

    }

}

```

# 2. repository

> 반드시 읽고 왜 레포구성이 아래와 같이 진행되는지!

아래의 설명을 읽어야하는이유! ( you must )

__you must__ first define a fragment interface and an implementation for the custom functionality, as shown in the following example:

https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#repositories.custom-implementations


참고하면 좋을 사항
https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl


> UserRepository.java

```java
import com.beanbroker.sample.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends
        JpaRepository<UserEntity, Long>,
//        QuerydslPredicateExecutor<UserEntity>,
        UserRepositoryCustom {

}
```


> UserRepositoryCustom.java

```java

import com.beanbroker.sample.api.user.entity.UserEntity;

public interface UserRepositoryCustom {


    UserEntity getByUserId(String userId);
}

```



> UserRepositoryImpl.java

```java

import com.beanbroker.sample.api.user.entity.QUserEntity;
import com.beanbroker.sample.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport
        implements UserRepositoryCustom {

    private static final QUserEntity table = QUserEntity.userEntity;


    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public UserRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity getByUserId(String userId) {

        return  from(table)
                .where(table.userId.eq(userId))
                .fetchOne();

    }
}

```

# 3. Service and Domain and Controller

에러 처리밑 중복처리 안되어있음 그냥 쓰는 방법만 알고 알아서


> UserService.java

```java

import com.beanbroker.sample.api.user.domain.UserInfo;
import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createUser(){


        userRepository.save(
                new UserEntity("beanbroker", "pkj", 32)
        );

    }

    public UserEntity getUserId(String userId) throws NotFoundException {


        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.getByUserId(userId));

        if(!userEntity.isPresent()){
            throw new NotFoundException("Not Found");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserAge(userEntity.get().getAge());
        userInfo.setUserName(userEntity.get().getName());

        return userEntity.get();

    }
}

```


> UserInfo.java

entity와 client에게 갈 response가 왜따로따로 나가야할가라는 고민을 꼭하자...

```java
public class UserInfo {

    private String userName;
    private int userAge;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
```


> UserController.java

```java

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public UserEntity testUser() throws NotFoundException {



        userService.createUser();

        return userService.getUserId("beanbroker");
    }
}

```


약속이 있으므로 빠르게 튀튀 다음장에서 github과 다른 부분들 더자세하게

2019년 7월 17일 깃헙 추가

[git source](https://github.com/beanbroker/querydsl_java_spring) 링크



------------------------------





# PREDICATOR



[git source](https://github.com/beanbroker/querydsl_java_spring) 링크

[kotlin_version](https://beanbroker.github.io/2019/03/09/Kotlin/kotlin_queryDsl3/) 링크


PREDICATOR가 왜필요할지는  동적쿼리를 매번매번 function으로 추가하여 쓸데없는 메소드 추가를 방지


## 바로 소스 카즈아

__중요부분__ 매우 중요

괜히 이펙티브자바에서 빌더를 써라라고 하는것이 아님!

> UserPredicator.java

```java
package com.beanbroker.sample.api.user.service;

import com.beanbroker.sample.api.user.entity.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.StringUtils;

public class UserPredicator {

    private static final QUserEntity table = QUserEntity.userEntity;

    private BooleanBuilder builder = new BooleanBuilder();


    public UserPredicator userId(String userId){

        if(userId != null){
            builder.and(table.userId.eq(userId));
        }
        return this;
    }

    public UserPredicator name(String name){

        if(name != null){
            builder.and(table.name.eq(name));
        }
        return this;
    }

    public UserPredicator age(int age){

        if(age > 0){
            builder.and(table.age.eq(age));
        }
        return this;
    }

    public Predicate values(){

        return builder.getValue();
    }
}


```

참쉬죠잉?


> UserRepositoryCustom.java

```java
import com.beanbroker.sample.api.user.entity.UserEntity;
import com.querydsl.core.types.Predicate;
public interface UserRepositoryCustom {


    UserEntity getByUserId(String userId);
    UserEntity getUserInfoWithPredicator(Predicate predicate);
}



```


> UserRepositoryImpl.java

```sql

public class UserRepositoryImpl extends QuerydslRepositorySupport
        implements UserRepositoryCustom {

    private static final QUserEntity table = QUserEntity.userEntity;


    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public UserRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity getByUserId(String userId) {

        return  from(table)
                .where(table.userId.eq(userId))
                .fetchOne();

    }

    @Override
    public UserEntity getUserInfoWithPredicator(Predicate userPredicator) {
        return from(table)
                .where(userPredicator)
                .fetchOne();
    }
}

```

## 셋팅은 끝낫는데 how to use?

아래가 핵심코드

```java
 public UserEntity getUserInfoWithPredicator(
            String userId,
            String name,
            int age

    ){



        return userRepository.getUserInfoWithPredicator(
                setUserQuery(userId, name, age)
        );
    }


    private Predicate setUserQuery( String userId,
                                    String name,
                                    int age){

        return new UserPredicator()
                .userId(userId)
                .name(name)
                .age(age)
                .values();

    }
```

## 그럼 써보자

```java
   UserEntity test1 = userService.getUserInfoWithPredicator(
                "beanbroker",
                null,
                0

        );

        System.out.println(test1.toString());


        UserEntity test2 = userService.getUserInfoWithPredicator(
                "beanbroker",
                    "pkj",
                0

        );


        System.out.println(test2.toString());

        UserEntity test3 = userService.getUserInfoWithPredicator(
                null,
                null,
                32

        );

        System.out.println(test3.toString());
```

아 요즘은 왜 저렇게써야하는지 설명 적는게 귀찮다.... 활용방법은 다양하니! 잘사용합시당