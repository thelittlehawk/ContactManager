package models

import anorm._
import play.api.db._
import play.api.Play.current

case class Contact(id: Long, name: String, emailAddress: String)

object Contact {

  def all = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * FROM contacts")().map { row =>
        Contact(
          id = row[Long]("id"),
          name = row[String]("name"),
          emailAddress = row[String]("emailAddress")
        )
      }.toList
    }
  }

  def create(contact: Contact) {
    DB.withConnection { implicit connection =>
      SQL("INSERT INTO contacts(name emailAddress) VALUES({name}, {emailAddress})")
      .on(
        "name" -> contact.name,
        "emailAddress" -> contact.emailAddress
      ).execute()
    }
  }

  def get(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * FROM contacts WHERE id = {id}").on("id" -> id).map { row =>
        Contact(
          id = row[Long]("id"),
          name = row[String]("name"),
          emailAddress = row[String]("emailAddress")
        )
      }.toList
    }
  }

  import play.api.data._
  import play.api.data.Forms._

  val form = Form(
    mapping(
      "id" -> ignored(0L),
      "name" -> nonEmptyText,
      "emailAddress" -> email
    )(Contact.apply)(Contact.unapply)
  )
}
