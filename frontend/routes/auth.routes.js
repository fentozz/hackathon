const {Router} = require('express');
const {check, validationResult} = require('express-validator');
const router = Router();
const request = require('request');

config = require('config'),
    backPort = config.get('backPort'),
    backHostname = config.get('backHostName');

// api/auth

router.post('/login',
    [
        check('login', 'Минимальная длинна логина 3 символа').isLength({min: 3}),
        check('password', 'Минимальная длинна пароля 3 символа').isLength({min: 3})
    ],
    async (req, res) => {
        try {
            const errors = validationResult(req);

            if (!errors.isEmpty()) {
                return res.status(400).json({
                    errors: errors.array(),
                    message: 'Ошибка при авторизации'
                });
            }

            const {login, password} = req.body;

            request.post({
                url: `http://${backHostname}:${backPort}/api/v2/accounts/login`,
                json: true,
                body: {
                    login: login,
                    password: password
                }
            }, function (error, response, body) {
                if (body.error) {
                    return res.status(400).json({message: error.message});
                }

                const {token, role, message} = body;

                //console.log('body', body);

                if (!token && !role) {
                    return res.status(400).json({message: body.message});
                }

                return res.json({token, role, message});
            });


            //return res.status(400).json({message: 'Ошибка авторизации'});
        } catch (e) {
            res.status(500).json({message: 'На сервере что-то пошло не так'});
        }
    });

module.exports = router;