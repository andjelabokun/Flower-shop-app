import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function Login({onSuccess}) {
  const [form, setForm] = useState({ username: "",  password: "" });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);
    setError("");
     try {
      const res = await http.post("/auth/login", form);
      //ocekujemo token
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("me", JSON.stringify(res.data.user));
      if(typeof onSuccess === "function") onSuccess(res.data.user);
    } catch (err) {
      setError(err.response?.data?.message || "Loging ing failed");
    } finally {
      setLoading(false);
    }}

    return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Ulogujte se</h2>
        <p className="muted">Pridružite nam se i nastavite sa razgledanjem!</p>

        {error && <div className="auth-alert">{error}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="field">
            <label>Username</label>
            <input
              type="text"
              value={form.username}
              onChange={(e) => setForm({ ...form, username: e.target.value })}
              required
            />
          </div>

          <div className="field">
            <label>Password</label>
            <input
              type="password"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              required
              minLength={6}
            />
          </div>

          <button className="btn-primary" disabled={loading}>
            {loading ? "Loging in..." : "Log in"}
          </button>
        </form>

        <div className="auth-footer">
          <span className="muted">Nemate profil?</span>
          <a href="/register">Registrujte se</a>
        </div>
      </div>
    </div>
  );
}