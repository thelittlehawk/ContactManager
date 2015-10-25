package controllers

import play.api._
import play.api.mvc._

class Contacts extends Controller {

  def index = Action {
    var contacts = models.Contact.all
    var messageOfDay = "There is no substitute for hard work."

    Ok(views.html.index(contacts, models.Contact.form))
  }

  def create = Action { implicit request =>
    models.Contact.form.bindFromRequest.fold(
      errors => BadRequest(views.html.index(models.Contact.all, errors)),
      contact => {
        models.Contact.create(contact)
        Redirect(routes.Contacts.index())
      }
    )
  }

  def edit(id: Long) = Action {
    Contact.get(id).map { contact =>
      Ok(views.html.edit(id, Contact.form.fill(contact)))
    } getOrElse {
      Redirect(routes.Contacts.index())
    }
  }

  def update(id: Long) = TODO

  def delete(id: Long) = TODO

}
