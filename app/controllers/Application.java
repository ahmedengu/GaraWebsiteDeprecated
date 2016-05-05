package controllers;

import models.Member;
import models.Message;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.toJson;

public class Application extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok(views.html.index.render("AAST Pool It"));
    }

    public Result register() {
        return ok(views.html.register.render("Register"));
    }

    public Result about() {
        return ok(views.html.about.render("About"));
    }
    public Result login() {
        return ok(views.html.login.render("Login"));
    }
    public Result services() {
        return ok(views.html.services.render("Services"));
    }

    public Result contact() {
        return ok(views.html.contact.render("Contact"));
    }
    @Transactional
    public Result addMember() {
         Member member = formFactory.form(Member.class).bindFromRequest().get();
        JPA.em().persist(member);
        return ok("Success!");
    }
    @Transactional
    public Result addMessage() {
        Message message = formFactory.form(Message.class).bindFromRequest().get();
        JPA.em().persist(message);
        return ok("Success!");
    }
    @Transactional(readOnly = true)
    public Result getMembers() {
        List<Member> members = (List<Member>) JPA.em().createQuery("select m from Member m").getResultList();
        return ok(toJson(members));
    }

    @Transactional(readOnly = true)
    public Result getMessages() {
        List<Message> messages = (List<Message>) JPA.em().createQuery("select m from Message m").getResultList();
        return ok(toJson(messages));
    }

    @Transactional
    public Result doLogin() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String email="'"+dynamicForm.get("email")+"'";
        String password="'"+dynamicForm.get("password")+"'";
        List<Member> members = (List<Member>) JPA.em().createQuery("select m from Member m where m.email="+email+" AND m.password="+password).getResultList();
        if(members.size()>0)
            return ok("Welcome, "+members.get(0).name);
        else
            return ok(views.html.login.render("Login"));
    }
}
