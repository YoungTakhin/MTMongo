# MTMongo

**MTMongo**是一个基于Spring Data MongoDB封装的用于快速开发的工具包。
**MTMongo**包含了部分开箱即用的RESTful API CRUD方法，为开发者节省开发时间。

MTMongo is an enhanced toolkit base on Spring Data MongoDB for simplify development.
This toolkit provides some out-of-the-box CURD methods with RESTful API that can save your development time.

# Getting Started

## 安装

Apache Maven：

```xml
<dependency>
  <groupId>com.alpactech</groupId>
  <artifactId>mt-mongo</artifactId>
  <version>1.1.1</version>
</dependency>
```

Gradle Groovy DSL:

```groovy
implementation 'com.alpactech:mt-mongo:1.1.1'
```

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-mongodb)

# 更新日志

## v1.0.0

2022-05-22

- 实验性功能

## v1.1.0

2022-08-14

- 功能重构
- 支持查询分页
- 支持根据指定字段值进行查询

## v1.1.1

2022-08-29

- 修复：page方法query参数失效问题
- 新增：部分exists、count方法
- 更新：更新Spring Data MongoDB到2.7.2
- **不兼容更新**：部分page方法更名为pageAll


## v1.1.2

2022-09-21

- 修复：数组操作addToSet方法bug
- 新增：数组操作push、pushAll、pop方法
- 新增：部分方法注释
- 更新：更新Spring Data MongoDB到2.7.3

## v1.1.3

2022-10-03

- 新增：部分update方法

