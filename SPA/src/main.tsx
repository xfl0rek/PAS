import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Users from "./pages/Users.tsx"
import RegisterForm from "./pages/RegisterForm.tsx"
import { BrowserRouter, Routes, Route } from 'react-router'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
        <Routes>
            <Route path="/users" element={<Users />}/>
            <Route path="/register" element={<RegisterForm />}/>
        </Routes>
    </BrowserRouter>
  </StrictMode>,
)
