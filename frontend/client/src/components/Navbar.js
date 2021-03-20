import React, {useContext} from "react";
import {NavLink, useHistory} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

export const Navbar = (role) => {

    const auth = useContext(AuthContext);
    const history = useHistory();

    const logoutHandler = (event) => {
        event.preventDefault();
        auth.logout();
        history.push('/');
    };

    return (
        <div className="header">
            <div className="headerContent">
                <div className="logo">
                    <a href="">furious <span className="pink">frontends</span></a>
                </div>
                <ul className="nav">
                    {
                        role == 'SPM_ADMIN' &&
                        <li><NavLink to="/usermanagement">Пользователи</NavLink></li>
                    }
                    <li><a href="/" onClick={logoutHandler}>Выйти</a></li>
                </ul>
            </div>
        </div>
    );

}