import {useState, useCallback, useEffect} from 'react';

const storageName = 'UserData';

export const useAuth = () => {
    const [token, setToken] = useState(null);
    const [ready, setReady] = useState(false);
    const [role, setRole] = useState(null);

    const login = useCallback((jwtToken, UserRole) => {
        setToken(jwtToken);
        setRole(UserRole);

        localStorage.setItem(storageName, JSON.stringify({
            token: jwtToken, role : UserRole
        }))
    }, []);
    const logout = useCallback(()=> {
        setToken(null);
        setRole(null);

        localStorage.removeItem(storageName);
    }, []);

    useEffect(()=> {
       const data = JSON.parse(localStorage.getItem(storageName));

       if ( data && data.token){
           login(data.token, data.role);
       }
       setReady(true);
    }, [login]);

    return {login, logout, token, role, ready};
};