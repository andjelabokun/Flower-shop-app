import React from 'react'

export default function ProtectedRoute({children}){
    const token = localStorage.getItem("token");
    
    if(!token){
        return <div style={{padding:40, textAlign:"center", color:'#d6336c'}}>
            <h2>Morate se prvo ulogovati/registrovati.</h2>
        </div>;
    }
    return children;
}

