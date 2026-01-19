// src/components/Navbar.jsx
import { NavLink } from "react-router-dom";
import { MENU_BY_ROLE } from "../config/menuConfig";
import "../styles/navbar.css";

export default function Navbar() {
  const role = localStorage.getItem("role");
  const menu = MENU_BY_ROLE[role] || [];

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    window.location.replace("/");
  };

  return (
    <header className="navbar">
      <div className="navbar-brand">License Tracker</div>

      <nav className="navbar-menu">
        {menu.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            {item.label}
          </NavLink>
        ))}
      </nav>
      <span className="navbar-role">{role}</span>

      <button className="logout-btn" onClick={handleLogout}>
        Logout
      </button>
    </header>
  );
}

