import { useState } from 'react'
import './App.css'
import Login from "./components/signin/Login.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";


function App() {

  return (
    <>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"} element={<Login />} />
                </Routes>
            </BrowserRouter>
    </>
  )
}

export default App
