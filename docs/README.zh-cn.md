# [simter-jwt](https://github.com/simter/simter-jwt) [[English]]

JSON Web Token (JWT) [[RFC7519]] 的纯 Java 实现。

## 安装

```xml
<dependency>
  <groupId>tech.simter</groupId>
  <artifactId>simter-jwt</artifactId>
  <version>0.3.0</version>
</dependency>
```

## 要求

- Java 8+ - 本组件使用了 java8 原生的 Base64 和 HMACSHA-256
- 至少一个 [JSR-353] 实现，如 [Genson]、[Jackson]、[Glassfish]

## 使用

### 创建 JWT
```
// 创建默认的 header: 
// e.g. {"typ":"JWT","alg":"HS256"}
Header header = Header.DEFAULT;

// 创建 payload
// e.g. {"iss":"RJ","aud":"you","exp":1483200000,"iat":1451577600,"name":"tester"}
Payload payload = new Payload();

// 设置 registed claims
payload.issuer = "RJ";
payload.audience = "you";
payload.issuedAt = ZonedDateTime.now().toEpochSecond();
payload.expires = ZonedDateTime.now().plusMonths(1).toEpochSecond();

// 添加 public/private claims
payload.add("name", "tester");
...

// 用 secret key 签名、编码
// eg. [header].[payload].[signature]
String jwt = new JWT(header, payload).encode("the-secret-key");
```

### 验证 JWT

```
// 验证和解码 JWT
JWT jwt = JWT.decode("[header].[payload].[signature]", "the-secret-key");

// 获取 public/private claims 值
String name = jwt.poyload.get("name");
```

## 构建

```bash
mvn clean package
```

## 发布

请先查看 [simter-parent] 的发布配置说明。

### 发布到局域网 Nexus 仓库

```bash
mvn clean deploy -Plan
```

### 发布到 Sonatype 仓库

```bash
mvn clean deploy -Psonatype
```

发布成功后登陆到 <https://oss.sonatype.org>，在 `Staging Repositories` 找到这个包，然后将其 close 和 release。
过几个小时后，就会自动同步到 [Maven 中心仓库](http://repo1.maven.org/maven2/tech/simter/simter-jwt) 了。

### 发布到 Bintray 仓库

```bash
mvn clean deploy -Pbintray
```

发布之前要先在 Bintray 创建 package `https://bintray.com/simter/maven/tech.simter:simter-jwt`。
发布到的地址为 `https://api.bintray.com/maven/simter/maven/tech.simter:simter-jwt/;publish=1`。
发布成功后可以到 <https://jcenter.bintray.com/tech/simter/simter-jwt> 检查一下结果。

## 参考

- JSON Web Token (JWT) 标准 [[RFC7519]]
- [jwt.io](https://jwt.io)

[JSON Web Token]: https://tools.ietf.org/html/rfc7519
[RFC7519]: https://tools.ietf.org/html/rfc7519
[JSR-353]: https://jcp.org/en/jsr/detail?id=353
[Genson]: http://owlike.github.io/genson
[Jackson]: https://github.com/FasterXML/jackson-datatype-jsr353
[Glassfish]: https://jsonp.java.net/download.html
[simter-parent]: https://github.com/simter/simter-parent/blob/master/docs/README.zh-cn.md
[English]: https://github.com/simter/simter-jwt/blob/master/README.md