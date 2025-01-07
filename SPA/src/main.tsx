import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Users from "./pages/Users.tsx"
import { BrowserRouter, Routes, Route } from 'react-router'
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
        <Routes>
            <Route path="/users" element={<Users />}/>
        </Routes>
    </BrowserRouter>
  </StrictMode>,
)
