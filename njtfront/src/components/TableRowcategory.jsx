import React from 'react'
import { MdDelete } from "react-icons/md";
import { GrUpdate } from "react-icons/gr";

export const TableRowcategory = ({id, naziv, onDelete, onUpdate}) => {
  return (
     <tr key={id}>
              <td>{id}</td>
              <td>{naziv}</td>
              <td>
                <button className="btn tiny danger" onClick={() => onDelete(id)}><MdDelete /></button>
                <button className="btn tiny danger" onClick={() => onUpdate(id)}><GrUpdate /></button>
              </td>
            </tr>
  )
}
