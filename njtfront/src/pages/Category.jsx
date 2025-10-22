import React, { useEffect, useState } from 'react'
import http from '../api/http';
import '../css/Category.css';
import { TableRowcategory } from '../components/TableRowcategory';
import { useSearchParams } from 'react-router-dom';

export const Category = () => {
    const[data, setData] = useState([]);
    const[loading, setLoading] = useState(true);
    const[error, setError] = useState("");
    const[q, setQ] = useState("");
    const[sort, setSort] = useState({by: "id", dir:"asc"});

    // ZA EDITOVANJE (Forma iznad tabele)
    const[novoIme, setNovoIme] = useState("");
    const[editingId, setEditingId] = useState(null);

    // ZA KREIRANJE NOVE KATEGORIJE (Modal)
    const [showModal, setShowModal] = useState(false);
    const [newCategoryName, setNewCategoryName] = useState("");


    function toggleSort(col){
        setSort((s) => 
            s.by ===col ? {by:col, dir: s.dir ==="asc"?"desc":"asc"} : {by:col, dir:"asc"}
        );
    }

    useEffect(() => {
        setLoading(true);

        http
        .get("/category")
        .then((res) => setData(Array.isArray(res.data) ? res.data : []))
        .catch((e) => setError(e?.response?.data?.message || e.message))
        .finally(() => setLoading(false));
    }, []);


    let prikazani = data.filter(
        (r) =>
        r.name.toLowerCase().includes(q.toLowerCase())
    );
        
    prikazani.sort((a,b) =>{
        const va = a[sort.by];
        const vb = b[sort.by];
        if(va == null) return 1;
        if(vb == null) return -1;
        if(typeof va === "number" && typeof vb ==="number"){
            return sort.dir === "asc" ? va - vb : vb - va;
        }
        return sort.dir === "asc" ? String(va).localeCompare(String(vb)) : String(vb).localeCompare(String(va));
    });


    // CRUD
    
    async function handleDelete(id){
        if(!window.confirm("Da li ste sigurni da želite da obrišete kategoriju?")) return;
        try{
            await http.delete(`/category/${id}`);
            setData((prev) => prev.filter((r) => r.id !== id));
            alert("Kategorija je uspešno obrisana!");
        }catch(e){
            alert(e?.response?.data?.message || e.message);
        }
    }

    // FUNKCIJA ZA SUBMIT FORME ZA EDITOVANJE (Forma iznad tabele)
    async function handleEditSubmit(e){
        e.preventDefault();
        if(!novoIme.trim()){
            alert("Unesi naziv kategorije.");
            return;
        }
        
        // OVA FORMA SE KORISTI SAMO ZA EDITOVANJE (editingId != null)
        if(editingId == null) return; 

        try{
            // UPDATUJEMO HTTP PUT 
            const res = await http.put(`/category/${editingId}`,
                {
                    id: editingId,
                    name: novoIme.trim(),
                }
            );
            // AZURIRANJE U LOKALNOJ MEMORIJI
            setData((prev) => prev.map((c) => (c.id === editingId ? res.data : c)));
            alert("Kategorija je uspešno IZMENJENA!");
            
            // resetujemo formu
            setEditingId(null);
            setNovoIme("");
        }catch(e){
            alert(e?.response?.data?.message || e.message);
        }
    }
    
    // FUNKCIJA ZA SUBMIT FORME ZA KREIRANJE (Unutar Modala)
    async function handleCreateSubmit(e){
        e.preventDefault();
        if(!newCategoryName.trim()){
            alert("Unesi naziv kategorije.");
            return;
        }
        
        try{
            // KREIRANJE NOVOG HTTP POST
            const res = await http.post("/category", {
                name: newCategoryName.trim()
            });
            
            // Vracamo kreirani objekat iz baze i stavljamo u tabelu
            setData((prev) => [...prev, res.data]);
            alert("Kategorija je uspešno DODATA!");
            
            // Resetujemo i zatvaramo modal
            setNewCategoryName("");
            setShowModal(false);
        }catch(e){
            alert(e?.response?.data?.message || e.message);
        }
    }


    // FUNKCIJE ZA FORMU (Editovanje)
    function startEdit(row){
        setEditingId(row.id);
        setNovoIme(row.name ?? "");
        // Osiguravamo da se modal zatvori ako je otvoren
        setShowModal(false); 
        setNewCategoryName("");
    }

    function cancelEdit(){
        setEditingId(null);
        setNovoIme("");
    }

    
    function openCreateModal(){
       
        cancelEdit(); 
        
        setNewCategoryName("");
        setShowModal(true);
    }
    
    function closeCreateModal(){
        setShowModal(false);
        setNewCategoryName("");
    }


    console.log(data, loading, error);

    
    if(loading) return <div className="container admin-wrap">Učitavanje...</div>;
    if(error) return <div className="container admin-wrap" style={{color: 'red'}}>Greška: {error}</div>;

    return (
        <div className="container admin-wrap">
            <header className="admin-head">
                <div>
                    <h1>Kategorije</h1>
                    <p className="muted">Pregled svih kategorija iz baze (cvećar).</p>
                </div>

                <div className="row-gap">
                    <input
                        className="input"
                        placeholder="Pretraga (naziv kategorije)"
                        value={q}
                        onChange={(e) => { 
                            setQ(e.target.value)
                        }}
                    />
                    <button
                        className="btn" onClick={() => setQ("")}>Osveži</button>
                    <button
                        className="btn primary" onClick={openCreateModal}>+ Nova kategorija</button>
                </div>
            </header>

            {/* FORMA ZA EDITOVANJE (Prikazuje se samo ako se edituje) */}
            {editingId != null && (
                <form className="panel" onSubmit={handleEditSubmit} style={{marginBottom: 12, display: "flex", gap:8, alignItems:"center", flexWrap:"wrap"}}>
                    <h4 style={{margin: 0}}>Izmena kategorije ID: {editingId}</h4>
                    <input
                        className="input"
                        placeholder="Naziv kategorije"
                        value={novoIme}
                        onChange={(e) => setNovoIme(e.target.value)}
                        required
                    />
                    
                    <button className="btn primary" type="submit">Ažuriraj</button>
                    <button className="btn" type="button" onClick={cancelEdit}>Otkaži</button>
                </form>
            )}


            <table className="table">
                <thead>
                    <tr>
                        <th onClick={() => toggleSort("id")}>
                            ID {sort.by === "id" && (sort.dir === "asc" ? "▲" : "▼")}
                        </th>
                        <th onClick={() => toggleSort("name")}>
                            Naziv {sort.by === "name" && (sort.dir === "asc" ? "▲" : "▼")}
                        </th>
                        <th>Akcije</th>
                    </tr>
                </thead>
                <tbody>
                    {prikazani.map((c) => (
                        <TableRowcategory 
                            key = {c.id} 
                            id={c.id} 
                            naziv={c.name} 
                            onDelete={() => handleDelete(c.id)} 
                            onUpdate={() => startEdit(c)}
                        />
                    ))}
                </tbody>
            </table>

            
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h3>Kreiranje nove kategorije</h3>
                        <form onSubmit={handleCreateSubmit}>
                            <input
                                className="input"
                                placeholder="Naziv nove kategorije"
                                value={newCategoryName}
                                onChange={(e) => setNewCategoryName(e.target.value)}
                                style={{marginBottom: '10px'}}
                                required
                            />
                            <div className="modal-buttons">
                                <button className="btn primary" type="submit">
                                    Sačuvaj
                                </button>
                                <button className="btn" type="button" onClick={closeCreateModal}>
                                    Otkaži
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
            
        </div>
    )
}
