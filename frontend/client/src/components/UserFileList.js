import React from "react";

export const UserFileList = ({ files }) => {

    if(!files){
        return (<div></div>);
    }


    return (
        <div className="mobile">
            <table className="bordered">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Название</th>
                    <th>Расширение</th>
                    <th>Размер</th>
                </tr>
                </thead>
                <tbody>
                {files.sort(function (a,b) {
                    return a.id - b.id;
                }).map( file => {
                    return(
                        <tr key={file.id}>
                            <td>{file.id}</td>
                            <td>{file.fileName}</td>
                            <td>{file.fileExtension}</td>
                            <td>{`${file.fileSize} Byte`}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
};