const http = require('http'),

    request = require('request'),

    express = require('express'),

    config = require('config'),
    port = config.get('port'),
    hostname = config.get('hostName'),

    app = express();

app.use(express.json({ extended: true }));

app.use('/api/auth', require('./routes/auth.routes'));

//const
//var fs = require('fs');
//var options = {
//    url: "http://localhost:5000/app",
//};


//const server = http.createServer((req, res) => {
//    res.statusCode = 200;
//    res.setHeader('Content-Type', 'text/plain');

//request.get(options, function(error, response, body){
//console.log(body);
//res.end(body);
//    res.end('Hello World');
//});

//    res.end('<H1> hello</H1>');
//});

app.listen(port, hostname, () => {
    console.log(`Сервер запущен на http://${hostname}:${port}/`);
});