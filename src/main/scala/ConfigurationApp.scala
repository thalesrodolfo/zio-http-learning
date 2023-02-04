import zio.ZLayer
import zio.config._
import zio.config.magnolia.descriptor

object ConfigurationApp {
  case class ApplicationConfig(host: String, path: String, port: Int, user: String, password: String, url: String)
  case class ServerConfig(host: String, path: String, port: Int)

  case class DbConfig(dataSourceClassName: String, dataSource: DataSource)
  case class DataSource(user: String, password: String, url:String)

  val live: ZLayer[Any, ReadError[String], ApplicationConfig] = {
    ZConfig.fromPropertiesFile("src/main/resources/application.conf", descriptor[ApplicationConfig])
  }

}
