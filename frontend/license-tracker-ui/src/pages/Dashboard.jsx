import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axiosConfig";
import "../styles/dashboard.css";

export default function Dashboard() {
  const role = localStorage.getItem("role");

  const [summary, setSummary] = useState({
    totalDevices: 0,
    totalLicenses: 0,
    devicesAtRisk: 0,
    licensesExpiring: 0,
  });

  const [expiringLicenses, setExpiringLicenses] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {
    try {
      const expiringRes = await api.get("/api/licenses/expiring?days=30");
      const licensesRes = await api.get("/api/licenses");

      let devicesCount = 0;
      let riskCount = 0;

      if (role === "ADMIN") {
        const devicesRes = await api.get("/api/devices");
        const riskRes = await api.get("/api/devices/non-compliant");

        devicesCount = devicesRes.data.length;
        riskCount = riskRes.data.length;
      }

      setSummary({
        totalDevices: devicesCount,
        totalLicenses: licensesRes.data.length,
        devicesAtRisk: riskCount,
        licensesExpiring: expiringRes.data.length,
      });

      setExpiringLicenses(expiringRes.data);
    } catch (err) {
      console.error("Dashboard load failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Navbar />

      <div className="dashboard-page">
        <h2>Dashboard</h2>

        {loading ? (
          <p>Loading dashboard...</p>
        ) : (
          <>
            {/* SUMMARY CARDS */}
            <div className="card-grid">
              {role === "ADMIN" && (
                <div className="card">
                  <p>Total Devices</p>
                  <h3>{summary.totalDevices}</h3>
                </div>
              )}

              <div className="card">
                <p>Total Licenses</p>
                <h3>{summary.totalLicenses}</h3>
              </div>

              {role === "ADMIN" && (
                <div className="card warning">
                  <p>Devices at Risk</p>
                  <h3>{summary.devicesAtRisk}</h3>
                </div>
              )}

              <div className="card danger">
                <p>Licenses Expiring</p>
                <h3>{summary.licensesExpiring}</h3>
              </div>
            </div>

            {/* EXPIRING LICENSES */}
            <div className="table-section">
              <h3>Expiring Licenses (Next 30 Days)</h3>

              <table>
                <thead>
                  <tr>
                    <th>License Key</th>
                    <th>Software</th>
                    <th>Vendor</th>
                    <th>Devices Used</th>
                    <th>Expiry Date</th>
                  </tr>
                </thead>
                <tbody>
                  {expiringLicenses.length === 0 ? (
                    <tr>
                      <td colSpan="5" style={{ textAlign: "center" }}>
                        No expiring licenses
                      </td>
                    </tr>
                  ) : (
                    expiringLicenses.map((l) => (
                      <tr key={l.licenseKey}>
                        <td>{l.licenseKey}</td>
                        <td>{l.softwareName}</td>
                        <td>{l.vendorName || "N/A"}</td>
                        <td>{l.devicesUsed ?? 0}</td>
                        <td>{l.validTo}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </>
        )}
      </div>
    </>
  );
}
