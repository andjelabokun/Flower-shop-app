import React from 'react'

export const Footer = () => {
  return (
    <footer id="contact" className="footer">
      <div className="container foot">
        <div className="brand">
          {/*<div className="dot"></div>*/}
          <span>BlossomGarden</span>
        </div>

        <p className="moto">
          “Svaki cvet je poruka, mi je samo nežno upakujemo.”
        </p>

        

        <small>© {new Date().getFullYear()} BlossomGarden</small>
      </div>
    </footer>
  )
}
