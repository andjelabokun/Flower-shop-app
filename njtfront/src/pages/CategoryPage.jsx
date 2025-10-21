import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import "../css/CategoryPage.css";

export default function CategoryPage() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    http.get("/category") 
      .then(res => setCategories(res.data))
      .catch(err => console.error("Greška pri učitavanju kategorija:", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <div className="loading">🌸 Učitavanje kategorija...</div>;
  }

  const categoryImages = {
  "4": "https://images.unsplash.com/photo-1589217289787-879b47f6edab?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",
  "5": "https://images.unsplash.com/photo-1471899236350-e3016bf1e69e?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1171",
  "6": "https://plus.unsplash.com/premium_photo-1726736627077-f4541d88622b?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
  "7": "https://plus.unsplash.com/premium_photo-1669997826684-785d9039f547?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=687",
  "8": "https://images.unsplash.com/photo-1584378619513-ecd2db623d0f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",
  "12": "https://images.unsplash.com/photo-1584378619513-ecd2db623d0f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",
  "13": "https://images.unsplash.com/photo-1584378619513-ecd2db623d0f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",
};

  return (
    <div className="categories-page">
      <h2 className="page-title">Naše kategorije</h2>
      <p className="page-subtitle">Odaberite povod i pronađite savršen buket!</p>

      <div className="categories-grid">
        {categories.length > 0 ? (
          categories.map((category) => (
            <div
              key={category.id}
              className="category-card"
              onClick={() => navigate(`/korisnickekategorije/${category.id}`)}
            >
              <div className="category-image-wrapper">
                <img
                    src={categoryImages[category.id.toString()] || "/flowers/default.jpg"}
                    alt={category.name}
                    className="category-image"
                />
              </div>
              <div className="category-info">
                <h3>{category.name}</h3>
                <p>{category.description || "Otkrijte više cvetnih aranžmana"}</p>
              </div>
            </div>
          ))
        ) : (
          <p className="no-categories">Nema dostupnih kategorija 🌸</p>
        )}
      </div>
    </div>
  );
}


