import {useRoutes} from "./routes";
import {BrowserRouter as Router} from 'react-router-dom';
import {useAuth} from "./hooks/auth.hook";
import {AuthContext} from "./context/AuthContext";
import React, {useContext} from "react";
import {Navbar} from "./components/Navbar";


function App() {
    const {token, role, login, logout} = useAuth();
    const isAuthenticated = !!token;
    const routes = useRoutes(isAuthenticated, role);

    return (
        <AuthContext.Provider value={{token, role, login, logout, isAuthenticated}}>
            {isAuthenticated && <div className="wrapper">
                <Navbar role = {role}/>
                <div className="content">
                
                </div>
            </div>}
            <Router>
                {routes}
            </Router>
        </AuthContext.Provider>
    )
}

export default App;
