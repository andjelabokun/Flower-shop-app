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
import Orders from './pages/Orders'; 
 
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
    // proveri da li proizvod vec postoji u korpi
    const existingItemIndex = cart.findIndex((item) => item.id === product.id);

    if (existingItemIndex > -1) {
      // AKO POSTOJI: kreiraj novu korpu sa azuriranom kolicinom
      const newCart = [...cart];
      const existingItem = newCart[existingItemIndex];
      
      // kolicina se povećava za 1 (ako nije definisana, kreni od 1)
      existingItem.quantity = (existingItem.quantity || 1) + 1; 

      setCart(newCart);
    } else {
      // AKO NE POSTOJI: dodaj novu stavku sa quantity da bude  1
      // dodajemo quantity pre nego sto ga ubacimo u korpu
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
      {/*neke rute su zasticene da ne bi moglo bez logovanja da se pristupi njima*/}
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
