package Services;

import models.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class RoleService {
    public RoleService(){

    }


    public void insertRole(Role role){
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        context.save(role);
        context.close();
    }
    public List<Role> showRoles(){
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = context.createQuery("FROM Role");
        List<Role> roles = query.list();
        context.close();
        return roles;
    }
}
