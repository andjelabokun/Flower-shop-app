import React, { useEffect, useState } from "react";
import http from "../api/http"; // Predpostavljam da je http.js u ../api/
import "../css/Orders.css"; // Kasnije ćemo definisati stilove

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Funkcija za dohvatanje narudžbina
  const fetchOrders = async () => {
    try {
      setLoading(true);
      const res = await http.get("/orders");
      // Sortiraj po datumu kreiranja opadajuće (najnovije prvo)
      const sortedOrders = res.data.sort(
        (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
      );
      setOrders(sortedOrders);
      setError(null);
    } catch (err) {
      console.error("Greška pri učitavanju narudžbina:", err);
      setError("Neuspelo učitavanje narudžbina.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  // Funkcija za promenu statusa
  const handleStatusChange = async (orderId, newStatus) => {
    if (!window.confirm(`Da li ste sigurni da želite da promenite status narudžbine #${orderId} u ${newStatus}?`)) {
      return;
    }

    try {
      // Koristi PATCH end-point: PATCH /orders/{id}/status?status={newStatus}
      await http.patch(`/orders/${orderId}/status?status=${newStatus}`);
      
      // Ažuriraj stanje na klijentu
      setOrders((prevOrders) =>
        prevOrders.map((order) =>
          order.id === orderId ? { ...order, status: newStatus } : order
        )
      );
    } catch (err) {
      console.error("Greška pri ažuriranju statusa:", err);
      alert("Neuspela promena statusa narudžbine.");
    }
  };
  
  // Pomoćna funkcija za formatiranje datuma
  const formatDateTime = (dateTime) => {
      const options = { 
          year: 'numeric', 
          month: '2-digit', 
          day: '2-digit', 
          hour: '2-digit', 
          minute: '2-digit', 
          second: '2-digit' 
      };
      return new Date(dateTime).toLocaleDateString('sr-RS', options);
  };
    
  if (loading) return <div className="loading">Učitavanje narudžbina...</div>;
  if (error) return <div className="error">{error}</div>;

  const statuses = ["CREATED", "CONFIRMED", "COMPLETED", "CANCELED"]; // Mogući statusi

  return (
    <div className="orders-wrap">
      <h2>Sve Narudžbine</h2>
      
      {orders.length === 0 ? (
        <p>Trenutno nema narudžbina.</p>
      ) : (
        <table className="orders-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Status</th>
              <th>Korisnik ID</th>
              <th>Datum kreiranja</th>
              <th>Ukupno stavki</th>
              <th>Napomena</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id} className={`status-${order.status.toLowerCase()}`}>
                <td>{order.id}</td>
                <td>
                  <span className={`status-badge status-${order.status.toLowerCase()}`}>
                    {order.status}
                  </span>
                </td>
                <td>{order.userId}</td>
                <td>{formatDateTime(order.createdAt)}</td>
                <td>{order.items.length}</td>
                <td>{order.napomena}</td>
                <td>
                  <select
                    value={order.status}
                    onChange={(e) => handleStatusChange(order.id, e.target.value)}
                    disabled={order.status === 'COMPLETED' || order.status === 'CANCELED'} // Ne dozvoli promenu ako je završena/otkazana
                  >
                    {statuses.map((s) => (
                      <option key={s} value={s}>
                        {s}
                      </option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}