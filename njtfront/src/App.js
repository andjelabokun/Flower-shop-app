import logo from './logo.svg';
import './App.css';
import { Beginning } from './pages/Beginning';
import { Footer } from './components/Footer';
import {Flower} from './pages/Flower';
import {Routes, Route, BrowserRouter} from "react-router-dom";
import { Category } from './pages/Category';
import Login from './pages/Login';
import Register from './pages/Register';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import { useEffect, useState } from 'react';
import CartPage from "./pages/CartPage";
import FlowersPage from "./pages/FlowersPage";
import CategoryPage from './pages/CategoryPage';
import Orders from './pages/Orders'; // Dodaj ovaj import
 
function App() {

  const [cart, setCart] = useState(() => {
    try{
      return JSON.parse(localStorage.getItem("cart")) || [];
    }catch{
      return [];
    }
});
useEffect(() => {
  localStorage.setItem("cart", JSON.stringify(cart));
}, [cart]);


  //const[cart, setCart] = useState([]);
  let userId = null;
    try{
      const me = JSON.parse(localStorage.getItem("me"));
      userId = me?.id || null;
    }catch(e){
      console.error("Ne mogu da pročitam localStorage.me", e);
    }

  
  const addToCart = (product) => {
    // 1. Proveri da li proizvod već postoji u korpi
    const existingItemIndex = cart.findIndex((item) => item.id === product.id);

    if (existingItemIndex > -1) {
      // 2. AKO POSTOJI: Kreiraj novu korpu sa ažuriranom količinom
      const newCart = [...cart];
      const existingItem = newCart[existingItemIndex];
      
      // Količina se povećava za 1 (ako nije definisana, kreni od 1+1=2)
      existingItem.quantity = (existingItem.quantity || 1) + 1; 

      setCart(newCart);
    } else {
      // 3. AKO NE POSTOJI: Dodaj novu stavku sa quantity: 1
      // Proizvodu dodajemo quantity: 1 pre nego što ga ubacimo u korpu
      const newItem = { ...product, quantity: 1 };
      setCart([...cart, newItem]);
    }
  };
  return (
    <BrowserRouter>
    <Navbar></Navbar>
    <Routes>
      <Route path = "/" element={<Beginning />}/>
      <Route path = "/proizvodi" element={<ProtectedRoute><Flower /></ProtectedRoute>}/>
      <Route path = "/kategorije" element={<ProtectedRoute><Category /></ProtectedRoute>}/>

      <Route path = "/login" element={<Login onSuccess={() => window.location.href='/'} />}/>
      <Route path = "/register" element={<Register onSuccess={() => window.location.href='/'} />}/>

      {/*za obirnog ulogovanog korisnika imamo rutu za korpu*/}
      <Route path  = "/korisnickekategorije" element={<ProtectedRoute><CategoryPage /></ProtectedRoute>}/>
        <Route path = "/korisnickekategorije/:id" element={<ProtectedRoute><FlowersPage addToCart={addToCart}/></ProtectedRoute>}/>
        <Route path = "/cart" element={<ProtectedRoute><CartPage cart={cart} setCart={setCart} userId={userId}/></ProtectedRoute>}/>
      <Route path = "/narudzbine" element={<ProtectedRoute><Orders /></ProtectedRoute>}/> 

    </Routes>
      <Footer></Footer>
    </BrowserRouter>
  );
}


export default App;
