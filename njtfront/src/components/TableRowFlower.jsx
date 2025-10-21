// TableRowFlower.js
import React from "react";

export const TableRowFlower = ({ flower, categories, onUpdate, onDelete }) => {
  
  // Nema potrebe za ovim kodom jer je kolona uklonjena
  // const category = categories.find((c) => c.id === flower.category_id);
  // const categoryName = category ? category.name : "Nije definisana";

  return (
    <tr>
      <td>{flower.id}</td>
      <td>{flower.name}</td>
      <td>{flower.description}</td>
      <td>{flower.price} RSD</td>
      
      {/* UKLONJENA ĆELIJA: <td>{categoryName}</td> */}
      
      <td>
        <div style={{ display: 'flex', gap: '8px' }}>
            <button className="btn small" onClick={onUpdate}>
              Izmeni
            </button>
            <button className="btn small danger" onClick={onDelete}>
              Obriši
            </button>
        </div>
      </td>
    </tr>
  );
};




