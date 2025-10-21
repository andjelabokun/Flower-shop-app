import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";


export default function Register({onSuccess}) {
  const [form, setForm] = useState({ username: "", email: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [ok, setOk] = useState("");
  const [error, setError] = useState("");
 

  async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);
    setError("");
    setOk("");

    try {
      await http.post("/auth/register", form);
      const loginRes = await http.post("/auth/login", {
        username: form.username,
        password: form.password,
      });

      localStorage.setItem("token", loginRes.data.token);
      localStorage.setItem("me", JSON.stringify(loginRes.data.user));

      setOk("Account created successfully!");
      if(typeof onSuccess === "function") onSuccess(loginRes.data.user);
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Napravite profil</h2>
        <p className="muted">Pridružite nam se i nastavite sa razgledanjem!</p>

        {error && <div className="auth-alert">{error}</div>}
        {ok && <div className="auth-success">{ok}</div>}

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
            <label>Email</label>
            <input
              type="email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
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
            {loading ? "Creating..." : "Create account"}
          </button>
        </form>

        <div className="auth-footer">
          <span className="muted">Već imate profil?</span>
          <a href="/login">Log in</a>
        </div>
      </div>
    </div>
  );
}

