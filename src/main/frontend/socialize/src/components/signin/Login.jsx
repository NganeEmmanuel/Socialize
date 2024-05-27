import React, {useEffect, useState} from 'react';
import {getHome} from "../utils/ApiFunctions.js";
import topLeftImage from "./top-left-image.jpg";
import topRightImage from "./top-right-image.jpg";
import "./styles.css";

const Login = (props) => {
    const  [newHome, setNewHome] = useState("")
    useEffect(() => {
        getHome().then((data) => {
            setNewHome(data)
        })
    }, []);


    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission here
      };

    return (
        <><div className="signup-page">
            <img src={topLeftImage} alt="Top Left Image" className="top-left-image" />
            <img src={topRightImage} alt="Top Right Image" className="top-right-image" />
            <div className="signup-form">
                <h2>Create a new account</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="name">Name:</label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required />
                    </div>
                    <div>
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required />
                    </div>
                    <div>
                        <label htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required />
                    </div>
                    <div>
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required />
                    </div>
                    <button type="submit">Sign Up</button>
                </form>
                <p>Already have an account? <a href="#">Log in</a></p>
            </div>
        </div>
        <div>
                <h1>{newHome}</h1>
            </div>
    </>
    );
}

export default Login;