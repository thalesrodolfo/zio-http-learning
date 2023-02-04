package models

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

import java.sql.Timestamp

case class Coins(coinId: Int, coinName: String, createdAt: String)

object Coins {
  implicit val decoder: JsonDecoder[Coins] = DeriveJsonDecoder.gen[Coins]
  implicit val encoder: JsonEncoder[Coins] = DeriveJsonEncoder.gen[Coins]
}
