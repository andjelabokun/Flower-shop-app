import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import http from "../api/http";
import "../css/FlowerPage.css";

export default function FlowersPage({ addToCart }) {
  const { id } = useParams(); // ID kategorije iz URL-a
  const [flowers, setFlowers] = useState([]);
  const [categoryName, setCategoryName] = useState("");
  const [catName, setCatName] = useState("");
  const [showPopup, setShowPopup] = useState(false); // 🌸 za prikaz notifikacije

  useEffect(() => {
    const fetchFlowers = async () => {
      try {
        const res = await http.get(`/flower/category/${id}`);
        setFlowers(res.data);
        if (res.data.length > 0) setCategoryName(res.data[0].categoryName);
      } catch (err) {
        console.error("Greška pri učitavanju cveća:", err);
      }
    };
    fetchFlowers();
  }, [id]);

  useEffect(() => {
    const fetchCategoryName = async () => {
      try {
        const response = await http.get(`/category/${id}`);
        setCatName(response.data.name);
      } catch (error) {
        console.error("Greška pri učitavanju naziva kategorije:", error);
      }
    };

    fetchCategoryName();
  }, [id]);

  const handleAddToCart = (flower) => {
  addToCart(flower); // koristi funkciju iz App.js

  setShowPopup(true);
  setTimeout(() => setShowPopup(false), 2000);
};


  if (!flowers.length) {
    return (
      <div className="flowers-page">
        <h2 className="flowers-title">Trenutno nema proizvoda u ovoj kategoriji 🌷</h2>
      </div>
    );
  }

  return (
    <div className="flowers-page">
      <h2 className="flowers-title">
        Cveće iz kategorije:{" "}
        <span className="category-name">{catName}</span>
      </h2>

      <div className="flowers-grid">
        {flowers.map((flower) => (
          <div key={flower.id} className="flower-card">
            <img
              src={
                flower.imageUrl ||
                "https://images.unsplash.com/photo-1608538465319-7e7f3c7adf79?auto=format&fit=crop&w=800&q=80"
              }
              alt={flower.name}
              className="flower-image"
            />
            <div className="flower-info">
              <h3>{flower.name}</h3>
              <p>{flower.description}</p>
              <div className="flower-bottom">
                <span className="flower-price">{flower.price} RSD</span>
                <button
                  className="add-to-cart-btn"
                  onClick={() => handleAddToCart(flower)}
                >
                  Dodaj u korpu
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* 🌸 Popup poruka */}
      {showPopup && (
        <div className="popup-message">🌷 Uspešno dodato u korpu!</div>
      )}
    </div>
  );
}



