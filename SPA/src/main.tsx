import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Users from "./pages/Users.tsx"
import Register from "./pages/Register.tsx"
import Login from "./pages/Login.tsx"
import Home from "./pages/Home.tsx"
import { BrowserRouter, Routes, Route } from 'react-router'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
        <Routes>
            <Route path="/users" element={<Users />}/>
            <Route path="/register" element={<Register />}/>
            <Route path="/login" element={<Login />}/>
            <Route path="/" element={<Home />}/>
        </Routes>
    </BrowserRouter>
  </StrictMode>,
)
