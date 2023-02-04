import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import repository.CoinService
import zio._
import zio.http._
import model.Method
import models.Coins
import models.Coins.decoder
import zio.json.EncoderOps

import java.io.IOException
import java.sql.SQLException

object Main extends ZIOAppDefault {

//  val app1: HttpApp[Any, Nothing] = Http.collect[Request] {
//    case Method.GET -> !! / "text" => Response.text("Hello World!")
//  }
//
//  val app2= Http.collectZIO[Request] {
//    case Method.GET -> !! / "coins" =>
//      DataService.getCoins.map { r => Response.json(r.toJson) }
//
//  }


  private val fooBar: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "foo" => Response.text("bar")
    case Method.GET -> !! / "bar" => Response.text("foo")
  }

  private val app: Http[Any, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "random" => Random.nextString(10).map(Response.text(_))
    case Method.GET -> !! / "utc" => Clock.currentDateTime.map(s => Response.text(s.toString))
  }

  private val app2: Http[CoinService, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "coins" => for {
      coins <- CoinService.all.orDie
    } yield Response.json(coins.toJson)

    case Method.GET -> !! / "coins" / id => for {
      coins <- CoinService.get(id.toInt).orDie
    } yield Response.json(coins.toJson)

  }

    override def run: ZIO[Any, Throwable, Nothing] = {

      (Server.install(app2).flatMap { port =>
        Console.printLine(s"Started server on port: $port")
      } *> ZIO.never)
      .provide(
        Server.default,
        ConfigurationApp.live,
        CoinService.live,
        Quill.Postgres.fromNamingStrategy(SnakeCase),
        Quill.DataSource.fromPrefix("DbConfig")
      )


  }

  //    val program: ZIO[ApplicationConfig with DataService, Exception, Unit] =
//      for {
//        config <- ZIO.service[ApplicationConfig]
////        _ <- ZIO.succeed(
////          Flyway
////            .configure()
////            .validateMigrationNaming(true)
////            .dataSource(config.url, config.user, config.password)
////            .load()
////            .migrate()
////        )
//
//        db <- ZIO.service[DataService]
//        _ <- db.getCoins.debug("Coins")
////        _ <- Console.printLine("List size: " + r1.length)
////        _ <- db.getPerson(r1.apply(r1.length - 1).id).debug("Result getPerson")
////        _ <- db.insertPerson(Person(0, "Teste", 10)).debug("Result insertPerson")
////        r2 <- db.getPeople.debug("Result getPeople")
////        _ <- db.deletePerson(r2.apply(r2.length - 1).id).debug("Result deletePerson")
////        _ <- db.getPeople.debug("Result getPeople")
//      } yield ()

//    Flyway
//      .configure()
//      .validateMigrationNaming(true)
//      .dataSource("jdbc:postgresql://localhost:5432/rewards_dev", "postgres", "postgrespw")
//      .load()
//      .migrate()

//    program.provide(
//      ConfigurationApp.live,
//      DataService.live,
//      Quill.Postgres.fromNamingStrategy(SnakeCase),
//      Quill.DataSource.fromPrefix("DbConfig")
//    ).debug("app")
//      .exitCode



  }