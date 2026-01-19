import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Devices from "./pages/Devices";
import Licenses from "./pages/Licenses";
import Assignments from "./pages/Assignments";
import Alerts from "./pages/Alerts";
import Reports from "./pages/Reports";
import Lifecycle from "./pages/Lifecycle";
import AiBot from "./pages/AiBot";


function App() {
  const [token, setToken] = useState(null);

  // ✅ Sync React state with localStorage
  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    setToken(storedToken);
  }, []);

  return (
    <BrowserRouter>
      <Routes>

        {/* Login */}
        <Route
          path="/"
          element={
            !token ? (
              <Login onLoginSuccess={() => setToken(localStorage.getItem("token"))} />
            ) : (
              <Navigate to="/dashboard" replace />
            )
          }
        />

        {/* Protected routes */}
        <Route
          path="/dashboard"
          element={token ? <Dashboard /> : <Navigate to="/" replace />}
        />

        <Route
          path="/devices"
          element={token ? <Devices /> : <Navigate to="/" replace />}
        />

        <Route
          path="/licenses"
          element={token ? <Licenses /> : <Navigate to="/" replace />}
        />

        <Route
          path="/assignments"
          element={token ? <Assignments /> : <Navigate to="/" replace />}
        />

        <Route
          path="/alerts"
          element={token ? <Alerts /> : <Navigate to="/" replace />}
        />

        <Route
          path="/reports"
          element={token ? <Reports /> : <Navigate to="/" replace />}
        />

        <Route
         path="/lifecycle"
         element={token ? <Lifecycle /> : <Navigate to="/" replace />}
        />

        <Route
        path="/ai-bot"
        element={token ? <AiBot /> : <Navigate to="/" replace />}
        />

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
