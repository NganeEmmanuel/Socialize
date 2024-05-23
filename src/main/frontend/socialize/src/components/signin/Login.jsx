import React, {useEffect, useState} from 'react';
import {getHome} from "../utils/ApiFunctions.js";


const Login = (props) => {
    const  [newHome, setNewHome] = useState("")
    useEffect(() => {
        getHome().then((data) => {
            setNewHome(data)
        })
    }, []);
    return (
        <div>
            <h1>{newHome}</h1>
        </div>
    );
}

export default Login;