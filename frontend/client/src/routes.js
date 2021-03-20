import React from 'react';
import {Switch, Route, Redirect} from "react-router-dom";
import {FilesPage} from "./pages/FilesPage";
import {SharedFilesPage} from "./pages/SharedFilesPage";
import {AuthPage} from "./pages/AuthPage";
import {UserManagementPage} from "./pages/UserManagementPage";

export const useRoutes = (isAuthenticated, role) => {
    if (isAuthenticated) {
        switch (role) {
            case 'SPM_ADMIN':
                return (
                    <Switch>
                        <Route path="/usermanagement" exact>
                            <UserManagementPage/>
                        </Route>
                        <Redirect to="/usermanagement"/>
                    </Switch>
                );
                break;
            case 2:
                return (
                    <Switch>
                        <Route path="/myfiles" exact>
                            <FilesPage/>
                        </Route>
                        <Route path="/sharedfiles" exact>
                            <SharedFilesPage/>
                        </Route>
                        <Redirect to="/myfiles"/>
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