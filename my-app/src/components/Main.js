import React, {useEffect, useState} from "react";
import axios from "axios";
import {redirect, Route, Router} from "react-router-dom";



const viewCertificate = async (user) => {
    console.log(user)



}
export default function Certificates() {
    const [users, setUsers] = useState([]);
    const [buttons, setButtons] = useState([])


    useEffect(() => {
        loadCertificate();
        getPages();

    }, []);

    const loadCertificate = async () => {
        const result = await axios.get("http://localhost:8888/certificate/getAllCertificates/1");
        setUsers(result.data);
    };
    const loadCertificateWithPage = async (page) => {
        const result = await axios.get("http://localhost:8888/certificate/getAllCertificates/" + page);
        console.log(result.data)
        setUsers(result.data);
    };
    const deleteCertificate = async (id) => {
        await axios.delete(`http://localhost:8888/certificate/${id}`);
        await loadCertificate();
        await getPages();
    };


    const getPages = async () => {
        let numberPage = [];

        let number = axios.get("http://localhost:8888/certificate/amount");
        for (let i = 0; i < (await number).data; i++) {
            numberPage.push(i);
        }

        setButtons(numberPage);


    };


    return (

        <div className="container">


            <div className="py-4">
                <table className="table border shadow">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">CreateDate</th>

                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user, index) => (
                        <tr key={index}>
                            <th scope="row">
                                {user.certificateId}
                            </th>
                            <td>{user.name}</td>
                            <td>{user.price}</td>
                            <td>{user.createDate}</td>
                            <td>
                                <button
                                    className="btn btn-outline-info mx-2"
                                    onClick={() => viewCertificate(user)}>
                                    View
                                </button>
                            </td>
                            <td>
                                <button
                                    className="btn btn-danger mx-2"
                                    onClick={() => deleteCertificate(user.certificateId)}>
                                    Delete
                                </button>
                            </td>

                        </tr>
                    ))}
                    </tbody>
                </table>

                {
                    buttons.map((index) => (

                        <button key={index}
                                className="btn btn-info mx-2"
                                onClick={() => loadCertificateWithPage(index + 1)}>
                            {index + 1}
                        </button>


                    ))}
            </div>


        </div>
    );
}