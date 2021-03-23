import React, {useCallback, useContext, useEffect, useState} from 'react'
import {useHttp} from "../hooks/http.hook";
import {AuthContext} from "../context/AuthContext";
import {useMessage} from "../hooks/message.hook";
import {UsersList} from "../components/UsersList";


export const UserManagementPage = () => {
    const [users, setUsers] = useState();
    const {loading, error, request, clearError} = useHttp();
    const message = useMessage();
    const {token} = useContext(AuthContext);

    const fetchUsers = useCallback(async () => {
        try {

            const fetched = await request('/api/users/all', 'GET', null, {
                token: token
            });
            setUsers(fetched.body);
        } catch (e) {
        }
    }, [token, request]);


    const createHandler = async (event) => {
        try {
            const login = document.getElementById('login').value,
                password = document.getElementById('password').value,
                passwordConfirm = document.getElementById('passwordConfirm').value;

            const data = await request('/api/users/create', 'POST',
                {
                    login,
                    password,
                    passwordConfirm
                }, {token: token});

            message(data.message, 'success');
            fetchUsers();
        } catch (e) {
        }
    };


    useEffect(() => {

        message(error, 'error');
        clearError();
    }, [error, message, clearError]);

    useEffect(() => {
        fetchUsers();
    }, []);

    const comp = (<div className="block">
        <h3>Добавить нового пользователя</h3>
        <div className="form">
            <div className="row">
                <label>Логин:</label>
                <input id="login" type="text" placeholder="Логин"/>
            </div>
            <div className="row">
                <label>Пароль:</label>
                <input id="password" type="password" placeholder="Пароль"/>
            </div>
            <div className="row">
                <label>Подтвердите пароль:</label>
                <input id="passwordConfirm" type="password" placeholder="Подтвердите пароль"/>
            </div>

            <div className="row">
                <button className="submitButton" onClick={createHandler} disabled={loading}>Создать</button>
            </div>
        </div>
    </div>);

    if (loading) {
        <div className="content">
            {comp}
        </div>
    }

    return (
        <div className="content">
            {comp}
            <UsersList users={users}/>
        </div>
    );
};