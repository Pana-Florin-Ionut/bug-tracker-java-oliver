package org.oliverr.bugtracker.repository;

import org.oliverr.bugtracker.DB;
import org.oliverr.bugtracker.entity.Role;
import org.oliverr.bugtracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Component
public class UserRepository {

    private DB db;

    @Autowired
    public void setDb(DB db) { this.db = db; }

    public User findByEmail(String s) {

        ResultSet rs = db.executeQuery("SELECT * FROM users WHERE email = '"+s+"';");
        User user = null;

        try {
            while(rs.next()) {
                user = new User();
                user.setId(rs.getLong(1));
                user.setFname(rs.getString(2));
                user.setLname(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setCreated(rs.getString(6));
                user.setLastLogin(rs.getString(7));
                user.setImage(rs.getString(8));
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (user != null) {
            ResultSet rs2 = db.executeQuery("SELECT roles.* FROM roles INNER JOIN users_roles ON users_roles.role_id = roles.role_id WHERE users_roles.user_id = "+user.getId()+";");
            try {
                while(rs2.next()) {
                    Role role = new Role();
                    role.setId(rs2.getLong(1));
                    role.setRole(rs2.getString(2));
                    user.addToRoles(role);
                }
                rs2.close();
            } catch(SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        return user;
    }

    public boolean isAdmin(User loggedUser) {
        for(Role r : loggedUser.getRoles()) {
            if(r.getRole().equalsIgnoreCase("admin")) return true;
        }
        return false;
    }

}
