# Velosiped Challenge Leaderboard

This is an automated leaderboard for the Velosiped challenge. 

## Build and run

In order to build the application, use Apache Maven wrapper bundled with the sources: 

```shell
$ ./mvnw clean package
```

As a result, you'll get `jar` file in the `target` directory. 

It can be started using simple `java -jar`:

```shell
$ java -jar checker-1.0.jar --github.access-token=<my-secret-access-token>
```

The application is running on port `8080` by default, so open `http://localhost:8080` to see it. 