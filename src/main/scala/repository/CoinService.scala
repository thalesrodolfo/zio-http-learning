package repository

import io.getquill.{Quoted, SnakeCase}
import io.getquill.jdbczio.Quill
import models.{Coins, Person}
import zio._

import java.sql.SQLException

class CoinService(quill: Quill.Postgres[SnakeCase])  {
  import quill._
  def all: ZIO[Any, SQLException, List[Coins]] = run(query[Coins])
  def get(id: Int): ZIO[Any, SQLException, Option[Coins]] =
    for {
      queryResult <- run(query[Coins].filter(p => p.coinId == lift(id)))
      result = queryResult.headOption
    } yield result

  def delete(id: Int): ZIO[Any, SQLException, Long] =
    run(query[Coins].filter(p => p.coinId == lift(id)).delete)

//  def add(name: String, age: Int): ZIO[Any, SQLException, Option[Coins]] = {
//    for {
//      createdId <- run(query[Coins].insert(_.coinName -> lift(name), _.age -> lift(age)).returningGenerated(_.id))
//      newPerson <- get(createdId)
//    } yield newPerson
//
//  }
}

object CoinService {
  def all: ZIO[CoinService, SQLException, List[Coins]] =
    ZIO.serviceWithZIO[CoinService](_.all)

  def get(id: Int): ZIO[CoinService, SQLException, Option[Coins]] =
    ZIO.serviceWithZIO[CoinService](_.get(id))
//
//  def deletePerson(id: Int): ZIO[DataService, SQLException, Long] =
//    ZIO.serviceWithZIO[DataService](_.deletePerson(id))
//
//  def insertPerson(name: String, age: Int): ZIO[DataService, SQLException, Option[Person]] =
//    ZIO.serviceWithZIO[DataService](_.insertPerson(name, age))

  val live: ZLayer[Quill.Postgres[SnakeCase], Nothing, CoinService] = ZLayer.fromFunction(new CoinService(_))
}