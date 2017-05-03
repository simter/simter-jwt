# [simter-jwt](https://github.com/simter/simter-jwt) [[中文]]

Pure Java implementation for JSON Web Token (JWT) [[RFC7519]]

## Installation

```xml
<dependency>
  <groupId>tech.simter</groupId>
  <artifactId>simter-jwt</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Requirement

- Java 8+ - this component use java8 native Base64 and HMACSHA-256
- At lease one [JSR-353] provider (Java API for JSON Processing)  
  e.g. [Genson], [Jackson], [Glassfish]

## Usage

### Create a JWT
```
// create a default header: 
// e.g. {"typ":"JWT","alg":"HS256"}
Header header = Header.DEFAULT;

// create a payload
// e.g. {"iss":"RJ","aud":"you","exp":1483200000,"iat":1451577600,"name":"tester"}
Payload payload = new Payload();

// set some registed claims
payload.issuer = "RJ";
payload.audience = "you";
payload.issuedAt = ZonedDateTime.now().toEpochSecond();
payload.expires = ZonedDateTime.now().plusMonths(1).toEpochSecond();

// add your public/private claims
payload.add("name", "tester");
...

// sign and encode with the secret key
// eg. [header].[payload].[signature]
String jwt = new JWT(header, payload).generate("the-secret-key");
```

### Verify a JWT

```
// verify and decode to a JWT instance
JWT jwt = JWT.verify("[header].[payload].[signature]", "the-secret-key");

// get your public/private claims value
String name = jwt.payload.get("name");
```

## Build

```bash
mvn clean package
```

## Deploy

First take a look at [simter-parent] deploy config.

### Deploy to LAN Nexus Repository

```bash
mvn clean deploy -Plan
```

### Deploy to Sonatype Repository

```bash
mvn clean deploy -Psonatype
```

After deployed, login into <https://oss.sonatype.org>. Through `Staging Repositories`, search this package, 
then close and release it. After couple hours, it will be synced 
to [Maven Central Repository](http://repo1.maven.org/maven2/tech/simter/simter-jwt).

### Deploy to Bintray Repository

```bash
mvn clean deploy -Pbintray
```

Will deploy to `https://api.bintray.com/maven/simter/maven/tech.simter:simter-jwt/;publish=1`.
So first create a package `https://bintray.com/simter/maven/tech.simter:simter-jwt` on Bintray.
After deployed, check it from <https://jcenter.bintray.com/tech/simter/simter-jwt>.

## References

- JSON Web Token (JWT) Standard [[RFC7519]]
- [jwt.io](https://jwt.io)

[JSON Web Token]: https://tools.ietf.org/html/rfc7519
[RFC7519]: https://tools.ietf.org/html/rfc7519
[JSR-353]: https://jcp.org/en/jsr/detail?id=353
[Genson]: http://owlike.github.io/genson
[Jackson]: https://github.com/FasterXML/jackson-datatype-jsr353
[Glassfish]: https://jsonp.java.net/download.html
[simter-parent]: https://github.com/simter/simter-parent/blob/master/README.md
[中文]: https://github.com/simter/simter-jwt/blob/master/docs/README.zh-cn.md