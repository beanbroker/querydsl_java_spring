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



--------------------