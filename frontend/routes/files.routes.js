const {Router} = require('express');
const {check, validationResult} = require('express-validator');
const router = Router();
const request = require('request');

config = require('config'),
    backPort = config.get('backPort'),
    backHostname = config.get('backHostName');

// api/users

router.

router.post('/create',
    [],
    async (req, res) => {
        try {


            console.log('req.body', req.body);



            request.post({

                url: `http://${backHostname}:${backPort}/api/v2/accounts/file`,
                json: true,
                body: req.body,
                headers: req.headers

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