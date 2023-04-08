# 初始化 SpringBoot 项目

## 1 下载代码

```shell
PS D:\Work> git clone https://github.com/hiwzc/cms.git
Cloning into 'cms'...
remote: Enumerating objects: 3, done.
remote: Counting objects: 100% (3/3), done.
remote: Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
Receiving objects: 100% (3/3), done.
PS D:\Work> cd cms
PS D:\Work\cms> git branch -a
* main
  remotes/origin/1.0.0
  remotes/origin/HEAD -> origin/main
  remotes/origin/main
PS D:\Work\cms> git checkout 1.0.0
Switched to a new branch '1.0.0'
branch '1.0.0' set up to track 'origin/1.0.0'.
```

## 2 使用 SpringBoot Initializr 初始化项目

https://start.spring.io/
   
使用 SpringBoot 2.7.10

## 3 创建一个Banner

http://patorjk.com/software/taag/#p=display&h=0&v=0&f=Big&t=CMS

```text
   _____   __  __    _____
  / ____| |  \/  |  / ____|
 | |      | \  / | | (___
 | |      | |\/| |  \___ \
 | |____  | |  | |  ____) |
  \_____| |_|  |_| |_____/     ${application.version}

 === SpringBoot ${spring-boot.version} ===
```

## 4 代码 

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.10</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.github.hiwzc</groupId>
    <artifactId>cms</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>cms</name>
    <description>cms</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### Application.java

```java
package com.github.hiwzc.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### application.properties

```properties
server.port=8080
```

### ApplicationTests.java

```java
package com.github.hiwzc.cms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
```

## 5 运行项目

```shell
# 执行单元测试
mvn test

# 方式一
mvn spring-boot:run

# 方式二
mvn package -Dmaven.test.skip=true
java -jar target/cms-0.0.1-SNAPSHOT.jar
```
