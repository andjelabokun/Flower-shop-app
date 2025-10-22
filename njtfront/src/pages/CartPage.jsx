import React from "react";

import "../css/Crat.css"; 
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import  {  useState } from 'react'

const CartPage = ({ cart, setCart, userId }) => {

    // Stanje za napomenu
    const [note, setNote] = useState("");

    // Funkcija za proracun ukupnog iznosa korpe
    const calculateTotal = () => {
        return cart.reduce((sum, item) => sum + (item.price * (item.quantity || 1)), 0);
    };

    const updateQuantity = (id, newQty) => {
        if (newQty < 1) return;
        setCart(
            cart.map((c) => c.id === id ? { ...c, quantity: newQty } : c)
        );
    };

    const removeItem = (id) => {
        setCart(cart.filter((c) => c.id !== id));
    };

    const createOrder = async () => {
        if (cart.length === 0) return; 

        const orderNote = note.trim() === "" ? "poruceno iz reacta" : note.trim(); //logika za napomenu

        const dto = {
            userId,
            napomena: orderNote,
            items: cart.map((c) => ({
                flowerId: c.id, 
                quantity: c.quantity || 1,
                unitPrice: c.price,
            })),
        };

        try {
            await http.post("/orders", dto);
            alert("NARUDŽBINA KREIRANA!");
            setCart([]);
            setNote("");
        } catch (err) {
            console.error("GRESKA prilikom kreiranja narudžbine:", err);
            alert("Došlo je do greške. Proverite konzolu.");
        }
    };

    // Prikaz
    return (
        <div className="cart-page-container">
            <h2>Moja Korpa</h2>

            {cart.length === 0 ? (
                <p className="empty-cart-message">🛒 Vaša korpa je trenutno prazna. Dodajte cveće da biste naručili!</p>
            ) : (
                <div className="cart-content-wrapper">
                    
                    {/* LEVA KOLONA: Lista stavki u korpi */}
                    <div className="cart-items-list">
                        {cart.map((c) => (
                            <div key={c.id} className="cart-item-card">
                                
                                {/* Slika proizvoda  */}

                                <div className="item-details">
                                    <h4 className="item-name">{c.name}</h4>
                                    <span className="item-price-per-unit">{c.price} RSD / kom</span>
                                </div>
                                
                                <div className="qty-controls">
                                    <button onClick={() => updateQuantity(c.id, (c.quantity || 1) - 1)}>-</button>
                                    <input
                                        type="number"
                                        min="1"
                                        value={c.quantity || 1}
                                        onChange={(e) => updateQuantity(c.id, parseInt(e.target.value))}
                                        className="qty-input"
                                    />
                                    <button onClick={() => updateQuantity(c.id, (c.quantity || 1) + 1)}>+</button>
                                </div>
                                
                                <div className="item-subtotal">
                                    Ukupno: **{(c.price * (c.quantity || 1)).toFixed(2)} RSD**
                                </div>

                                <button onClick={() => removeItem(c.id)} className="btn-delete">
                                    &times;
                                </button>
                            </div>
                        ))}
                    </div>

                    {/* DESNA KOLONA: Rezime narudžbine */}
                    <div className="order-summary-box">
                        <h3>Rezime narudžbine</h3>

                        {/* Polje za napomenu */}
                        <div className="napomena-field">
                            <label htmlFor="order-note">Napomena za narudžbinu:</label>
                            <textarea
                                id="order-note"
                                rows="3"
                                value={note}
                                onChange={(e) => setNote(e.target.value)}
                                placeholder="Npr. Isporuka na adresu: Ulica cvetova 5, Vreme isporuke: posle 17h..."
                            ></textarea>
                        </div>

                        <div className="summary-row">
                            <span>Broj stavki:</span>
                            <span>{cart.length}</span>
                        </div>
                        <div className="summary-row total-row">
                            <span>Ukupna cena:</span>
                            <span>{calculateTotal().toFixed(2)} RSD</span>
                        </div>
                        
                        <button 
                            onClick={createOrder} 
                            className="btn-order-submit"
                        >
                            <i className="fas fa-shopping-bag"></i> Pošalji narudžbinu ({calculateTotal().toFixed(2)} RSD)
                        </button>
                    </div>

                </div>
            )}
        </div>
    );
};

export default CartPage;


