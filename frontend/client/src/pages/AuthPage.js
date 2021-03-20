import React, {useContext, useEffect, useState} from 'react';
import '../index.css';
import {useHttp} from "../hooks/http.hook";
import {ToastsContainer, ToastsStore} from 'react-toasts';
import {useMessage} from "../hooks/message.hook";
import {AuthContext} from "../context/AuthContext";

export const AuthPage = () => {
    const auth = useContext(AuthContext);
    const message = useMessage();
    const {loading, error, request, clearError} = useHttp();

    useEffect(() => {

        message(error, 'error');
        clearError();
    }, [error, message, clearError]);

    const loginHandler = async (event) => {
        try {
            const login = document.getElementById('login').value,
                password = document.getElementById('password').value;


            const data = await request('/api/auth/login', 'POST',
                {
                    login,
                    password
                });

            //console.log('data', data);

            message(data.message, 'success');

            auth.login(data.token, data.role);

        } catch (e) {
        }
    };

    return (

        <div id="range5">

            <div className="outer">
                <div className="middle">
                    <div className="inner">

                        <div className="login-wr">
                            <h2>Авторизация</h2>
                            <div className="form">
                                <input id="login" type="text" placeholder="Логин"/>
                                <input id="password" type="password" placeholder="Пароль"/>
                                <button onClick={loginHandler} disabled={loading}> Вход</button>

                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>


    );
};