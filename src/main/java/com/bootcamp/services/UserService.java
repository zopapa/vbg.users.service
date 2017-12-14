package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.crud.UserCRUD;
import com.bootcamp.entities.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bignon on 11/27/17.
 */

@Component
public class UserService implements DatabaseConstants{

    UserCRUD userCRUD;

    @PostConstruct
    public void init(){
        userCRUD = new UserCRUD();
    }

    public void create(User user) throws SQLException {
         userCRUD.create(user);
    }

    public void update(User user) throws SQLException {
        userCRUD.update(user);
    }

    public User delete(int id) throws SQLException {
        User user = read(id);
        userCRUD.delete(user);

        return user;
    }

    public User read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<User> users = userCRUD.read(criterias);

        return users.get(0);
    }
    
    public User getByLoginAndPwd(String login, String pwd) throws SQLException {
        Criterias criterias = new Criterias();

        Criteria criteria = new Criteria();
        Rule rule = new Rule();
        rule.setEntityClass(User.class);
        rule.setColumn("login");
        rule.setOperator("=");
        rule.setValue(login);
        criteria.setRule(rule);
        criteria.setLinkOperator("AND");

        Criteria criteria1 = new Criteria();
        Rule rule1 = new Rule();
        rule1.setEntityClass(User.class);
        rule1.setColumn("password");
        rule1.setOperator("=");
        rule1.setValue(pwd);
        criteria1.setRule(rule);

        criterias.addCriteria(criteria);
        criterias.addCriteria(criteria1);
        List<User> users = userCRUD.read(criterias);

        return users.get(0);
    }



    public List<User> read() throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        List<User> users = UserCRUD.read();
        return users;
    }
    
    public boolean checkUser(String login, String password) throws SQLException {
        boolean isUser;
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("login", "=", login, "AND"));
        criterias.addCriteria(new Criteria("password", "=", password));
        
        if (UserCRUD.read(criterias).isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

}
