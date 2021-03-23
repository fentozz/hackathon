import React from 'react';
import {Switch, Route, Redirect} from "react-router-dom";
import {FilesPage} from "./pages/FilesPage";
import {SharedFilesPage} from "./pages/SharedFilesPage";
import {AuthPage} from "./pages/AuthPage";
import {UserManagementPage} from "./pages/UserManagementPage";
import {Navbar} from "./components/Navbar";
import {AdminsFilePage} from "./pages/AdminFilePage";

export const useRoutes = (isAuthenticated, role) => {
    if (isAuthenticated) {
        switch (role) {
            case 'SPM_ADMIN':
                return (
                    <Switch>
                        <div className="wrapper">
                            <Navbar role={role}/>
                            <Route path="/usermanagement" exact>
                                <UserManagementPage/>
                            </Route>
                            <Route path="/adminsfile" exact>
                                <AdminsFilePage/>
                            </Route>
                            <Redirect to="/usermanagement"/>
                        </div>
                    </Switch>
                );
                break;
            case 'SPM_USER':
                return (
                    <Switch>
                        <div className="wrapper">
                            <Navbar role={role}/>
                            <Route path="/myfiles" exact>
                                <FilesPage/>
                            </Route>
                            <Route path="/sharedfiles" exact>
                                <SharedFilesPage/>
                            </Route>
                            <Redirect to="/myfiles"/>
                        </div>
                    </Switch>
                );
                break;
        }
    }

    return (
        <Switch>
            <Route path="/" exact>
                <AuthPage/>
            </Route>
            <Redirect to="/"/>
        </Switch>
    );
};