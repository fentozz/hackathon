import React, {useContext} from "react";
import {NavLink} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

export const Navbar = (role) => {

    role = role.role;

    console.log('rolenavbar', role);

    const auth = useContext(AuthContext);

    const logoutHandler = (event) => {
        event.preventDefault();
        auth.logout();
    };

    return (
        <div className="header">
            <div className="headerContent">
                <div className="logo">
                    <a href="">furious <span className="pink">frontends</span></a>
                </div>
                <ul className="nav">


                    {role === 'SPM_ADMIN' && <li><NavLink to="/usermanagement">Пользователи</NavLink></li>}
                    {role === 'SPM_ADMIN' && <li>< NavLink to="/adminsfile">Файлы</NavLink></li>}


                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </div>
    );

};

