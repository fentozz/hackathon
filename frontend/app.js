const http = require('http'),

    request = require('request'),

    express = require('express'),

    config = require('config'),
    port = config.get('port'),
    hostname = config.get('hostName'),

    app = express();

const fileUpload = require('express-fileupload');

app.use(fileUpload({}));

app.use(express.json({ extended: true }));

app.use('/api/auth', require('./routes/auth.routes'));
app.use('/api/users', require('./routes/manageuser.routes'));
//app.use('/api/files', require('./routes/files.routes'));





const multer  = require('multer'),
    storage = multer.diskStorage({
        destination: function (req, file, next) {
            next(null, './uploads')
        },
        filename: function (req, file, next) {
            next(null, 'avatar-' + Date.now() + ".jpg")
        }
    }),
    upload = multer({ storage: storage }),
    fUpload = upload.fields([{name: 'photo', maxCount: 1}]);

/**
 * Upload routing.
 * Update form data. Upload file using multer.
 */
app.post('/upload', fUpload, function (req, res, next) {
    // Field data
    console.log(req.body);
    // File details
    console.log(req.files);

    // Error handling
    fUpload(req, res, function (err) {
        if (err) {
            console.log("An error occurred when uploading");
        }else{
            res.send("Form data saved!");
        }
    });
});





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