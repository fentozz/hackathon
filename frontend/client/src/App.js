import {useRoutes} from "./routes";
import {BrowserRouter as Router} from 'react-router-dom';
import {useAuth} from "./hooks/auth.hook";
import {AuthContext} from "./context/AuthContext";
import React, {useContext} from "react";
import {Navbar} from "./components/Navbar";
import {Loaders} from "./components/Loader";


function App() {
    const {token, role, login, logout, ready} = useAuth();
    const isAuthenticated = !!token;
    const routes = useRoutes(isAuthenticated, role);

    //if (!ready)
    //{
    //    return <Loaders/>;
    //}

    return (
        <AuthContext.Provider value={{token, role, login, logout, isAuthenticated}}>
            <Router>
                {routes}
            </Router>
        </AuthContext.Provider>
    )
}

export default App;
