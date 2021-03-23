const {Router} = require('express');
const {check, validationResult} = require('express-validator');
const router = Router();
const request = require('request');

config = require('config'),
    backPort = config.get('backPort'),
    backHostname = config.get('backHostName');

// api/users

router.post('/create',
    [
        check('login', 'Минимальная длинна логина 3 символа').isLength({min: 3}),
        check('password', 'Минимальная длинна пароля 8 символа').isLength({min: 8}),
        check('passwordConfirm', 'Минимальная длинна повторения пароля 8 символа').isLength({min: 8})
    ],
    async (req, res) => {
        try {
            const errors = validationResult(req);

            if (!errors.isEmpty()) {
                return res.status(400).json({
                    errors: errors.array(),
                    message: 'Ошибка при валидации данных для создания нового пользователя'
                });
            }

            const {login, password, passwordConfirm} = req.body;

            if (password !== passwordConfirm) {
                return res.status(400).json({
                    errors: errors.array(),
                    message: 'Пароли не совпадают'
                });
            }

            request.post({
                url: `http://${backHostname}:${backPort}/api/v2/admin/register`,
                json: true,
                body: {
                    password: login,
                    passwordConfirm: password,
                    login: passwordConfirm,
                    userRole: "SPM_USER"
                },
                headers: {
                    token: req.headers.token
                }

            }, function (error, response, body) {

                if(!body.message){
                    return res.status(400).json({message: body.login});
                }

                const {message} = body;

                return res.json({message});
            });


            //return res.status(400).json({message: 'Ошибка авторизации'});
        } catch (e) {
            console.log(e);
            res.status(500).json({message: 'На сервере что-то пошло не так'});
        }
    });

router.get('/all',
    [],
    async (req, res) => {
        try {
            request.get({
                url: `http://${backHostname}:${backPort}/api/v2/admin/accounts`,
                json: true,
                headers: req.headers
            }, function (error, response, body) {
                if (body.error) {
                    return res.status(400).json({message: 'Ошибка'});
                }

                const {message} = body;

                return res.json({body, message});
            });


            //return res.status(400).json({message: 'Ошибка авторизации'});
        } catch (e) {
            res.status(500).json({message: 'На сервере что-то пошло не так'});
        }
    });

module.exports = router;