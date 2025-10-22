import React from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import "../css/Navbar.css";
import { CgProfile } from "react-icons/cg";
import { IoIosLogIn } from "react-icons/io";

export default function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("me") || "null");
  const role = user?.role; // može biti "FLORIST" ili "USER"
  const isAuth = !!token;

  

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("me");
    localStorage.removeItem("cart");
    navigate("/");
  }

  

  return (
    <header className="navbar">
      <div className="nav-inner">
        {/* Logo sekcija */}
        <div className="nav-left">
          <Link to="/" className="logo">
            <img src="/flower.png" alt="logo" className="logo-img" />
            <span className="brand-name">BlossomGarden</span>
          </Link>
        </div>

        {/* Linkovi */}
          <nav className="nav-links">
          <NavLink to="/" className="nav-item">Početna</NavLink>

          {/* Ako je cvecar */}
          {isAuth && role === "FLORIST" && (
           <>
            <NavLink to="/kategorije" className="nav-item">Kategorije</NavLink>
            <NavLink to="/proizvodi" className="nav-item">Proizvodi</NavLink>
            <NavLink to="/narudzbine" className="nav-item">Narudzbine</NavLink>
            </>
           )}

           {/* Ako je  korisnik */}
           {isAuth && role === "USER" && (
           <>
            <NavLink to="/korisnickekategorije" className="nav-item">Cveće</NavLink>
             <NavLink to="/cart" className="nav-item">Korpa</NavLink>
          </>
            )}
          </nav>


        {/* Desna sekcija – login/register ili user info */}
        <div className="nav-actions">
          {!isAuth ? (
            <>
              <Link to="/login" className="btn-login">
                <i className="fa fa-user"></i><IoIosLogIn /> Log in
              </Link>
              <Link to="/register" className="btn-register">
                <i className="fa fa-user-plus"></i><CgProfile /> Register
              </Link>
            </>
          ) : (
            <>
              <span className="welcome">🌷 {user?.username || "Korisnik"}</span>
              <button className="btn-logout" onClick={handleLogout}>
                Odjava
              </button>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

