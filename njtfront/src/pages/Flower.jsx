import React, { useEffect, useState } from "react";
import http from "../api/http";
import "../css/Flower.css";
import { TableRowFlower } from "../components/TableRowFlower";

export const Flower = () => {
  const [data, setData] = useState([]);
  const [categories, setCategories] = useState([]); 
  const [loading, setLoading] = useState(true);
  const [q, setQ] = useState("");
  const [sort, setSort] = useState({ by: "id", dir: "asc" });
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(null);

  const [form, setForm] = useState({
    name: "",
    description: "",
    price: "",
    image_url: "",
    category_id: "",
  });

  useEffect(() => {
    
    const fetchData = async () => {
      try {
        const [flowerRes, categoryRes] = await Promise.all([
          http.get("/flower"),
          http.get("/category"), 
        ]);
        setData(flowerRes.data);
        setCategories(categoryRes.data);
      } catch (error) {
        console.error("Greška pri učitavanju podataka", error);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const toggleSort = (col) => {
    setSort((s) =>
      s.by === col ? { by: col, dir: s.dir === "asc" ? "desc" : "asc" } : { by: col, dir: "asc" }
    );
  };

  const filtered = [...data]
    .filter((f) => f.name?.toLowerCase().includes(q.toLowerCase()))
    .sort((a, b) => {
      const va = a[sort.by];
      const vb = b[sort.by];
      const numCols = ["id", "price"];
      if (numCols.includes(sort.by)) {
        const na = Number(va) || 0,
          nb = Number(vb) || 0;
        return sort.dir === "asc" ? na - nb : nb - na;
      }
      return sort.dir === "asc"
        ? String(va ?? "").localeCompare(String(vb ?? ""))
        : String(vb ?? "").localeCompare(String(va ?? ""));
    });

const handleOpenModal = (flower = null) => {
    if (flower) {
        setEditing(flower);
        
        
        const categoryId = flower.categoryId !== null ? String(flower.categoryId) : ""; 
        
        setForm({
            name: flower.name,
            description: flower.description,
            price: flower.price,
            
            imageUrl: flower.imageUrl || "", 
            categoryId: categoryId, 
        });
    } else {
        
    }
    setShowModal(true);
};

  const handleCloseModal = () => {
    setShowModal(false);
    setEditing(null);
  };



const handleSave = async (e) => {
    e.preventDefault();
    
   
    const payload = {
      ...form,
      price: Number(form.price), 
      imageUrl: form.imageUrl, 
     
      categoryId: form.categoryId === "" ? null : Number(form.categoryId),
    };
    
    try {
      if (editing) {
        // azuriranje
        const res = await http.put(`/flower/${editing.id}`, payload);
        setData((prev) =>
          prev.map((f) => (f.id === editing.id ? res.data : f))
        
        );
        alert("Cvet je uspešno IZMENJEN!");
      } else {
        // kreiranje novog cveta
        const res = await http.post("/flower", payload);
        setData((prev) => [...prev, res.data]);
        alert("Novi cvet je uspešno DODAT!");
      }
      handleCloseModal();
    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
};



  const handleDelete = async (id) => {
    if (!window.confirm("Da li sigurno želiš da obrišeš proizvod?")) return;
    try {
      await http.delete(`/flower/${id}`);
      setData((prev) => prev.filter((r) => r.id !== id));
    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
  };

  return (
    <div className="container admin-wrap">
      <header className="admin-head">
        <div>
          <h1>Proizvodi</h1>
          <p className="muted">Pregled, unos i izmena proizvoda u bazi.</p>
        </div>

        <div className="row-gap">
          <input
            className="input"
            placeholder="Pretraga (naziv proizvoda)"
            value={q}
            onChange={(e) => setQ(e.target.value)}
          />
          <button className="btn primary" onClick={() => handleOpenModal()}>
            + Novi proizvod
          </button>
        </div>
      </header>

      

      {loading ? (
        <p>Učitavanje...</p>
      ) : (
        <table className="table">
          <thead>
            <tr>
              <th onClick={() => toggleSort("id")}>
                ID {sort.by === "id" && (sort.dir === "asc" ? "▲" : "▼")}
              </th>
              <th>Naziv</th>
              <th>Opis</th>
              <th onClick={() => toggleSort("price")}>
                Cena {sort.by === "price" && (sort.dir === "asc" ? "▲" : "▼")}
              </th>
              
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            
            {filtered.map((f) => (
              <TableRowFlower
                key={f.id}
                flower={f}
                categories={categories}
                onUpdate={() => handleOpenModal(f)}
                onDelete={() => handleDelete(f.id)}
              />
            ))}
          </tbody>
        </table>
      )}



      {/* Modal */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>{editing ? "Izmeni proizvod" : "Dodaj novi proizvod"}</h2>
            <form onSubmit={handleSave} className="modal-form">
              <input
                className="input"
                placeholder="Naziv"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
                required
              />
              <input
                className="input"
                placeholder="Opis"
                value={form.description}
                onChange={(e) =>
                  setForm({ ...form, description: e.target.value })
                }
              />
              <input
                className="input"
                placeholder="Cena (RSD)"
                type="number"
                value={form.price}
                onChange={(e) => setForm({ ...form, price: e.target.value })}
              />
              <input
                className="input"
                placeholder="URL slike"
                value={form.imageUrl}
                onChange={(e) =>
                  setForm({ ...form, imageUrl: e.target.value })
                }
              />
              
              {/* padajuca lista za kategoriju */}
              <select
                className="input"
                value={form.categoryId}
                onChange={(e) =>
                  setForm({ ...form, categoryId: e.target.value })
                }
                required
              >
                <option value="">Izaberite kategoriju</option>
                {categories.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.name}
                  </option>
                ))}
              </select>
              
              <div className="modal-buttons">
                <button className="btn primary" type="submit">
                  Sačuvaj
                </button>
                <button className="btn" type="button" onClick={handleCloseModal}>
                  Otkaži
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};





