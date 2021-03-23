import React, {useCallback, useContext, useState} from 'react';
import Dropzone, {useDropzone} from 'react-dropzone';
import {useHttp} from "../hooks/http.hook";
import {useMessage} from "../hooks/message.hook";
import {AuthContext} from "../context/AuthContext";
import {UserFileList} from "../components/UserFileList";

export const FilesPage = () => {
    const [files, setFiles] = useState();
    const {loading, error, request, clearError} = useHttp();
    const message = useMessage();
    const {token} = useContext(AuthContext);

    const onDrop = useCallback(async (acceptedFiles) => {

        console.log("acceptedFiles", acceptedFiles);

        var formdata = new FormData();
        formdata.append("multipartFile", acceptedFiles[0]);
        formdata.append("fileName", acceptedFiles[0].name );

        const response = await fetch('/upload', {
            multipart: true,
            method: 'POST',
            body: formdata
            , headers: {
                'token': token,
                'Content-Type': "multipart/form-data",
               // 'type': "formData",
               // 'Content-Length': acceptedFiles[0].size
            }
        });
        const data = await response.json();



        //const data = await request('/api/files/create', 'POST',
        //   {
        //     multipartFile : formdata,
        //   fileName : acceptedFiles[0].name
        //}, {
        //    token: token

        //});
        //const data = await response.json();


    }, []);
    const {getRootProps, getInputProps} = useDropzone({onDrop});


    const comp = (<div className="block">
        <h3>Добавить новый файл</h3>
        <div className="form">
            <div {...getRootProps()} className="dropFile">
                <input {...getInputProps()} />

                <p className="dropText">Перетащите сюда файлы, или нажмите чтобы выбрать</p>

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
            <UserFileList users={files}/>
        </div>
    );
};