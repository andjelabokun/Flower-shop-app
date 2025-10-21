import React from 'react'
import '../css/Beginning.css';
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
export const Beginning = () => {
  const navigate = useNavigate();
    const categories = [
  {name: "Lale", img: "https://images.unsplash.com/photo-1602992191575-59a2c7bd0ae1?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=689",},
  {name: "Kaktus", img: "https://images.unsplash.com/photo-1683688412173-148e0ba57c19?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",},
  {name: "Pampas", img: "https://images.unsplash.com/photo-1666425865764-862f7ea60cf1?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=735",},
  {name: "Hrizantema buket", img: "https://plus.unsplash.com/premium_photo-1713823800827-4c10d4d37585?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=687",},
  {name: "Pahira", img: "https://images.unsplash.com/photo-1597055181187-1f9b726e2d66?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1964",},
];



    const featured = [
    {
      id: 5,name: "Rezano cveće",rating: 4.9,povod: "Savršeno za poklon i dekoraciju doma",cena: "od 300 RSD po cvetu",tip: "poklon",img: "https://images.unsplash.com/photo-1578972497170-bfc780c65f65?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=686",},
    {
      id: 7,name: "Buketi",rating: 4.8,povod: "Rođendani, godišnjice, pokloni iz srca",cena: "od 1200 RSD",tip: "poklon",img: "https://images.unsplash.com/photo-1660549071389-962f436e1b5f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=765",},
    {
      id: 8,name: "Saksijsko cveće",rating: 4.7,povod: "Dugotrajan poklon za kuću ili kancelariju",cena: "od 800 RSD",tip: "dekoracija",img: "https://plus.unsplash.com/premium_photo-1672998159540-0a3f849fe3c6?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=687",},
    {
      id: 6,name: "Dekorativno i suvo cveće",rating: 4.6,povod: "Idealno za uređenje enterijera i proslave",cena: "od 1000 RSD",tip: "proslave",img: "https://plus.unsplash.com/premium_photo-1676383427728-5e6df7edcf02?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=713",},
  ];

  const [filter, setFilter] = useState("sve");

  const filtered =
    filter === "sve"
      ? featured
      : featured.filter((item) => item.tip === filter);

      const slike = [
  "https://images.unsplash.com/photo-1563241527-3004b7be0ffd?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=687", 
  "https://images.unsplash.com/photo-1608982216701-a9ab65b4a3a2?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=805", 
  "https://plus.unsplash.com/premium_photo-1667870034632-134c46ef9a47?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=764", 
  "https://plus.unsplash.com/premium_photo-1670426501265-3cd3d4a30f8f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=687", 
];

const [index, setIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((prev) => (prev + 1) % slike.length);
    }, 3000); // menja sliku svakih 3 sekunde
    return () => clearInterval(interval);
  }, []);

  return (
    <>
    <section className="hero">
      <div className="container hero-grid">
        <div className="hero-copy">
          
          <h1>Pošalji cveće onima koje voliš, brzo i sa stilom </h1>
          <p>Odaberi savršen poklon, dodaj poruku i obraduj nekog danas.</p>
          {/*
          <form className="hero-search" onSubmit={(e) => e.preventDefault()}>
            <input
              placeholder="Unesi povod/kategoriju"
              className="hero-input"
            />
            <button className="btn btn-primary">Pronađi kategoriju</button>
          </form>
          */}

          <div className="stats">
            <div>
              <strong>30+</strong>
              <span>Različitih cvetova</span>
            </div>
            <div>
              <strong>45 min</strong>
              <span>Prosečno vreme dostave</span>
            </div>
            <div>
              <strong>Besplatna dostava</strong>
              <span>Za porudžbine preko 2500 RSD</span>
            </div>
          </div>
        </div>

        <div className="hero-art">
          <div className="hero-slideshow">
            <img src={slike[index]} alt="Buket" className="hero-slideshow-img" />
            </div>
          
          </div>
      </div>
    </section>
 
    <section id="categories" className="section">
  <div className="container">
    <div className="section-head">
      <h2>Proizvodi</h2>
      <a className="link" href="#ponuda">Izdvojeni proizvodi</a>
    </div>

    <div className="grid cats">
      {categories.map((c) => (
        <a key={c.name} className="cat" href="#ponuda">
          <img src={c.img} alt={c.name} />
          <span>{c.name}</span>
        </a>
      ))}
    </div>
  </div>
</section>



      <section id="featured" className="section alt">
      <div className="container">
        <div className="section-head">
          <h2>Izdvojene kategorije</h2>
        </div>

        {/* Dugmići za filtere */}
        <div className="filters">
          <button
            className={`chip ${filter === "sve" ? "active" : ""}`}
            onClick={() => setFilter("sve")}
          >
            Sve
          </button>
          <button
            className={`chip ${filter === "poklon" ? "active" : ""}`}
            onClick={() => setFilter("poklon")}
          >
            Za poklon
          </button>
          <button
            className={`chip ${filter === "dekoracija" ? "active" : ""}`}
            onClick={() => setFilter("dekoracija")}
          >
            Za dekoraciju
          </button>
          <button
            className={`chip ${filter === "proslave" ? "active" : ""}`}
            onClick={() => setFilter("proslave")}
          >
             Za proslave
          </button>
        </div>

        {/* Kartice */}
        <div className="grid rest">
          {filtered.map((r) => (
            <article className="rest-card" key={r.id}>
              <div className="thumb">
                <img src={r.img} alt={r.name} />
                <div className="overlay">
                  <span>{r.cena}</span>
                </div>
              </div>
              <div className="content">
                <h3>{r.name}</h3>
                <p className="povod">{r.povod}</p>
                <div className="meta">
                  <span className="rating">⭐ {r.rating}</span>
                  <button className="btn tiny" onClick={() => navigate(`/korisnickekategorije/${r.id}`)}>Pogledaj ponudu</button>
                </div>
              </div>
            </article>
          ))}
        </div>
      </div>
    </section>


    </>
  )
}
