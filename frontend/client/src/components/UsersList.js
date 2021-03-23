import React from "react";
import Moment from 'react-moment';

export const UsersList = ({ users }) => {

    if(!users){
        return (<div></div>);
    }


  return (
      <div className="mobile">
        <table className="bordered">
          <thead>
          <tr>
            <th>id</th>
            <th>Логин</th>
            <th>Дата создания</th>
            <th>Последний вход</th>
          </tr>
          </thead>
          <tbody>
          {users.sort(function (a,b) {
                  return a.id - b.id;
          }).map( user => {
              return(
                  <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>{user.email}</td>
                      <td><Moment format="YYYY-M-D H:m:s" parse="YYYY-MM-DD HH:mm:ss">{user.created}</Moment></td>
                      <td><Moment format="YYYY-M-D H:m:s" parse="YYYY-MM-DD HH:mm:ss">{user.lastAccessed}</Moment></td>
                  </tr>
              );
          })}
          </tbody>
        </table>
      </div>
  );
};